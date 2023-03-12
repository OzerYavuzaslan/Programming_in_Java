package emlakcepte.tasks;

import emlakcepte.model.User;
import emlakcepte.model.enums.RealtyType;
import emlakcepte.repository.RealtyRepository;
import emlakcepte.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.TimerTask;


public class RealtyTask extends TimerTask {

    private final UserRepository userRepository;

    private final RealtyRepository realtyRepository;

    public RealtyTask(UserRepository userRepository, RealtyRepository realtyRepository) {
        this.userRepository = userRepository;
        this.realtyRepository = realtyRepository;
    }


    @Override
    public void run() {
        var list = userRepository.findAll().stream()
                .filter(u -> u.getPackageExpireDate() != null)
                .filter(u -> LocalDateTime.now().isAfter(u.getPackageExpireDate()))
                .toList()
                .stream()
                .map(User::getRealtyList)
                .flatMap(Collection::stream)
                .toList();

        list.forEach(r -> r.setStatus(RealtyType.PASSIVE));

        realtyRepository.saveAll(list);
    }
}

