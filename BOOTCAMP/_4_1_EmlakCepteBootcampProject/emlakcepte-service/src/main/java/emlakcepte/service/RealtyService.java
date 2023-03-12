package emlakcepte.service;

import com.emlakcepte.model.DbLog;
import emlakcepte.client.BannerServiceClient;
import emlakcepte.client.model.Banner;
import emlakcepte.configuration.RabbitMQConfiguration;
import emlakcepte.controller.UserController;
import emlakcepte.dto.converter.RealtyConverter;
import emlakcepte.dto.model.request.RealtyRequest;
import emlakcepte.dto.model.response.RealtyResponse;
import emlakcepte.model.Realty;
import emlakcepte.model.User;
import emlakcepte.model.enums.RealtyType;
import emlakcepte.model.enums.UserType;
import emlakcepte.repository.RealtyRepository;
import emlakcepte.tasks.RealtyTask;
import org.apache.commons.lang.NullArgumentException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class RealtyService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfiguration rabbitMQConfiguration;

    private static final int MAX_INDIVIDUAL_REALTY_SIZE = 10;
    private final UserService userService;
    private final RealtyRepository realtyRepository;
    private final RealtyConverter realtyConverter;
    private final BannerServiceClient bannerServiceClient;
    private final Logger logger = Logger.getLogger(UserController.class.getName());

    public RealtyService(RabbitTemplate rabbitTemplate, RabbitMQConfiguration rabbitMQConfiguration, UserService userService, RealtyRepository realtyRepository, RealtyConverter realtyConverter, BannerServiceClient bannerServiceClient) {
        new Timer().schedule(new RealtyTask(userService.getUserRepository(), realtyRepository), 0, 10000000);
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfiguration = rabbitMQConfiguration;
        this.userService = userService;
        this.realtyRepository = realtyRepository;
        this.realtyConverter = realtyConverter;
        this.bannerServiceClient = bannerServiceClient;
    }

    public RealtyResponse create(RealtyRequest realtyRequest) throws Exception {
        Optional<User> foundUser = userService.getById(realtyRequest.getUserId());

        // Kullanıcı bilgileri kontrol ediliyor.
        if (foundUser.isPresent()) {
            if (UserType.INDIVIDUAL.equals(foundUser.get().getType())) {
                List<Realty> realtyList = realtyRepository.findAllByUserId(foundUser.get().getId());
                rabbitTemplate.convertAndSend(rabbitMQConfiguration.getLogQueueName(), new DbLog("[Emlakcepte-Main-Service] -> [HTTP - PUT Request] Update User : requested. UserName: "+foundUser.get().getName()+" Response code:"+ HttpStatus.OK,LocalDateTime.now()));

                if (MAX_INDIVIDUAL_REALTY_SIZE <= realtyList.size()) {
                    rabbitTemplate.convertAndSend(rabbitMQConfiguration.getLogQueueName(), new DbLog("[Emlakcepte-Main-Service] -> [HTTP - PUT Request] Update User : requested. UserName: "+foundUser.get().getName()+"No more room for new realty! Need to remove at least one of the realties in order to add new realty. Response code:"+ HttpStatus.INSUFFICIENT_STORAGE,LocalDateTime.now()));

                    logger.log(Level.WARNING, "Bireysel kullanıcı en fazla 5 ilan girebilir. userID : {0}",
                            foundUser.get().getId());
                    throw new Exception("Bireysel kullanıcı en fazla 10 ilan girebilir");
                }
                if (foundUser.get().getPackageExpireDate() == null || foundUser.get().getPackageExpireDate().isBefore(LocalDateTime.now())) {
                    logger.log(Level.WARNING, "Aktif paketi bulunmayan kullanıcılar ilan yayınlayamazlar! {0}", foundUser.get().getName());
                    rabbitTemplate.convertAndSend(rabbitMQConfiguration.getLogQueueName(), new DbLog("[Emlakcepte-Main-Service] -> [HTTP - PUT Request] Update User : requested. UserName: "+foundUser.get().getName()+" Response code:"+ HttpStatus.PAYMENT_REQUIRED,LocalDateTime.now()));

                    throw new Exception("Aktif paketi bulunmayan kullanıcılar ilan yayınlayamazlar! Lütfen bir abonelik satın alın!");
                }
            }
        }
        else {
            rabbitTemplate.convertAndSend(rabbitMQConfiguration.getLogQueueName(), new DbLog("[Emlakcepte-Main-Service] -> [HTTP - PUT Request] Update User : requested but [USER NOT FOUND] Response code:"+ HttpStatus.OK,LocalDateTime.now()));

            throw new IllegalArgumentException("\"user bulunamadı\"");
        }


        // İlan kaydediliyor.
        Realty realty = realtyConverter.convert(realtyRequest);
        if (foundUser.isPresent()) {
            realty.setUser(foundUser.get());
        }

        Realty savedRealty = realtyRepository.save(realty);

        rabbitTemplate.convertAndSend(rabbitMQConfiguration.getrealtyQueueName(), savedRealty);
        // İlan kaydedene afiş veriyoruz.

        Banner bannerRequest = new Banner(String.valueOf(realty.getNo()), 1, "123123", "");

        Banner bannerResponse = bannerServiceClient.create(bannerRequest);

        return realtyConverter.convert(savedRealty);
    }

    public void save(Realty realty) {
        realtyRepository.save(realty);
    }

    public void delete(Integer id) {
        var realty = realtyRepository.findById(id);
        realty.ifPresent(realtyRepository::delete);
    }

    public RealtyResponse update(Integer id, RealtyRequest realtyRequest) {
        var realty = realtyRepository.findById(id);
        if (realty.isPresent()) {

            Realty realty1 = realtyConverter.convert(realtyRequest);
            realtyRepository.save(realty1);
            return realtyConverter.convert(realty1);

        }
        throw new NullArgumentException("Realty is not found!!!");

    }

    public List<RealtyResponse> getAll() {
        var realtyList = realtyRepository.findAll();
        return realtyList.stream().map(realtyConverter::convert).collect(Collectors.toList());
    }

    public RealtyResponse getRealtyByUserId(Integer userId, Integer realtyId) {
        var user = userService.getById(userId);
        if (user.isPresent()) {
            var realty = user.get().getRealtyList().stream().filter(realty1 -> realty1.getId().equals(realtyId)).findFirst();
            return realty.map(realtyConverter::convert).orElse(null);
        }
        return null;
    }

    public void getAllByProvince(String province) {
        getAll().stream()
                .filter(realty -> realty.getProvince().equals(province))
                .forEach(realty -> System.out.println(realty));

    }

    public List<RealtyResponse> getActiveRealtyByUserId(Integer id) {
        return getAll().stream()
                .filter(realty -> realty.getUserId().equals(id))
                .filter(realty -> RealtyType.ACTIVE.equals(realty.getRealtyType())).toList();

    }

    public List<RealtyResponse> getAllById(int id) {
        return realtyRepository.findAllByUserId(id).stream().map(realtyConverter::convert).collect(Collectors.toList());
    }

    public List<RealtyResponse> getAllActiveRealtyes() {
        return realtyRepository.findAllByStatus(RealtyType.ACTIVE).stream().map(realtyConverter::convert).collect(Collectors.toList());
    }

    public RealtyResponse changeStatusOfRealty(Integer realtyId) {
        var realty = realtyRepository.findById(realtyId).stream().findFirst();
        if (realty.isPresent()) {
            Realty realty1 = realty.get();
            if (RealtyType.ACTIVE.equals(realty1.getStatus())) {
                realty1.setStatus(RealtyType.PASSIVE);
            } else {
                realty1.setStatus(RealtyType.ACTIVE);
            }
            realtyRepository.save(realty1);
            return realtyConverter.convert(realty1);
        }
        throw new EntityNotFoundException("Realty is not found!");
    }

}
