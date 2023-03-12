package emlakcepte.service;

import com.emlakcepte.model.ConsoleLog;
import com.emlakcepte.model.DbLog;
import com.emlakcepte.model.FileLog;
import emlakcepte.client.PaymentServiceClient;
import emlakcepte.client.model.Payment;
import emlakcepte.configuration.RabbitMQConfiguration;
import emlakcepte.controller.UserController;
import emlakcepte.dto.converter.UserConverter;
import emlakcepte.dto.model.request.UserRequest;
import emlakcepte.dto.model.request.UserUpdateRequest;
import emlakcepte.dto.model.response.UserResponse;
import emlakcepte.model.User;
import emlakcepte.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserService {

	private final RabbitTemplate rabbitTemplate;
	private final RabbitMQConfiguration rabbitMQConfiguration;
	private final UserRepository userRepository;
	private final UserConverter userConverter;
	private final Logger logger = Logger.getLogger(UserController.class.getName());
	private PaymentServiceClient paymentServiceClient;

	public UserService(RabbitTemplate rabbitTemplate, RabbitMQConfiguration rabbitMQConfiguration, UserRepository userRepository, UserConverter userConverter, PaymentServiceClient paymentServiceClient)
	{

		this.rabbitTemplate = rabbitTemplate;
		this.rabbitMQConfiguration = rabbitMQConfiguration;
		this.userRepository = userRepository;
		this.userConverter = userConverter;
		this.paymentServiceClient=paymentServiceClient;
	}
	public Payment processPayment(User user, String cardNo,Integer extensionAmount) {
		return paymentServiceClient.processPayment(user.getId(),cardNo,extensionAmount);
	}

	public void extendPackageExpiration(Payment payment)
	{
		var user = userRepository.findById(payment.getUserId());
		if(user.isPresent()) {
			if (user.get().getPackageExpireDate() == null || user.get().getPackageExpireDate().isBefore(LocalDateTime.now())) {
				user.get().setPackageExpireDate(LocalDateTime.now().plusMonths(1));
			}
			else {
				user.get().setPackageExpireDate(user.get().getPackageExpireDate().plusMonths(1));
			}
			userRepository.save(user.get());
		}

	}

	public UserResponse createUser(UserRequest userRequest) {
		User savedUser = userRepository.save(userConverter.convert(userRequest));
		rabbitTemplate.convertAndSend(rabbitMQConfiguration.getLogQueueName(), new DbLog("[Emlakcepte-Main-Service] -> Create User: "+savedUser.getName()+" - "+savedUser.getEmail()+" created.",LocalDateTime.now()));
		rabbitTemplate.convertAndSend(rabbitMQConfiguration.getLogQueueName(), new ConsoleLog("[Emlakcepte-Main-Service] -> Create User: "+savedUser.getName()+" - "+savedUser.getEmail()+" created.",LocalDateTime.now()));
		rabbitTemplate.convertAndSend(rabbitMQConfiguration.getLogQueueName(), new FileLog("[Emlakcepte-Main-Service] -> Create User: "+savedUser.getName()+" - "+savedUser.getEmail()+" created.",LocalDateTime.now()));


		logger.log(Level.INFO, "[createUser] - user created: {0}", savedUser.getId());
		rabbitTemplate.convertAndSend(rabbitMQConfiguration.getQueueName(), savedUser);

		logger.log(Level.WARNING, "[createUser] - userRequest: {0}, sent to : {1}",
				new Object[] { userRequest.getEmail(), rabbitMQConfiguration.getQueueName() });
		return userConverter.convert(savedUser);
	}


	public List<UserResponse> getAll() {
		return userConverter.convert(userRepository.findAll());
	}

	public void updatePassword(User user, String newPassword) {
		// önce hangi user bul ve passwordü update et.
	}

	public User getByEmailAntiPattern(String email) {

		//// @formatter:off
		return userRepository.findAll()
				.stream()
				.filter(user -> user.getEmail().equals(email))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
		// @formatter:on

	}

	public UserResponse getByEmail(String email) {
		return userConverter.convert(userRepository.findByEmail(email));
	}

	public Optional<User> getById(Integer userId) {
		return userRepository.findById(userId);
	}

	public UserResponse update(UserUpdateRequest userUpdateRequest) {
		// TODO Auto-generated method stub
		return null;
	}
	public UserRepository getUserRepository() {
		return userRepository;
	}


}
