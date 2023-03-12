package emlakcepte.controller;

import com.emlakcepte.model.ConsoleLog;
import com.emlakcepte.model.DbLog;
import com.emlakcepte.model.FileLog;
import emlakcepte.client.model.Payment;
import emlakcepte.configuration.RabbitMQConfiguration;
import emlakcepte.dto.model.request.UserRequest;
import emlakcepte.dto.model.request.UserUpdateRequest;
import emlakcepte.dto.model.response.UserResponse;
import emlakcepte.model.User;
import emlakcepte.service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping(value = "/users")
public class UserController {
	private final UserService userService;

	private final RabbitTemplate rabbitTemplate;
	private final RabbitMQConfiguration rabbitMQConfiguration;
	private final Logger logger = Logger.getLogger(UserController.class.getName());
	public UserController(UserService userService, RabbitTemplate rabbitTemplate, RabbitMQConfiguration rabbitMQConfiguration)
	{
		this.userService = userService;
		this.rabbitTemplate = rabbitTemplate;
		this.rabbitMQConfiguration = rabbitMQConfiguration;
	}

	@GetMapping
	public ResponseEntity<List<UserResponse>> getAll()
	{
		rabbitTemplate.convertAndSend(rabbitMQConfiguration.getLogQueueName(), new FileLog("[Emlakcepte-Main-Service] -> [HTTP - GET Request] Get All Users : requested. Response code:"+HttpStatus.OK,LocalDateTime.now()));

		return ResponseEntity.ok(userService.getAll());
	}

	@PostMapping
	public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
		UserResponse userResponse = userService.createUser(userRequest);

		logger.log(Level.INFO, "user created: {0}", userResponse);
		rabbitTemplate.convertAndSend(rabbitMQConfiguration.getLogQueueName(), new DbLog("[Emlakcepte-Main-Service] -> [HTTP - POST Request] Create User: "+userRequest.getName()+" - "+userRequest.getEmail()+" requested. Response code:"+HttpStatus.OK,LocalDateTime.now()));
		rabbitTemplate.convertAndSend(rabbitMQConfiguration.getLogQueueName(), new ConsoleLog("[Emlakcepte-Main-Service] -> [HTTP - POST Request] Create User: "+userRequest.getName()+" - "+userRequest.getEmail()+" requested. Response code:"+HttpStatus.OK,LocalDateTime.now()));
		rabbitTemplate.convertAndSend(rabbitMQConfiguration.getLogQueueName(), new FileLog("[Emlakcepte-Main-Service] -> [HTTP - POST Request] Create User: "+userRequest.getName()+" - "+userRequest.getEmail()+" requested. Response code:"+HttpStatus.OK,LocalDateTime.now()));

		return ResponseEntity.ok(userResponse);
	}
	@GetMapping(value = "/{email}")
	public ResponseEntity<UserResponse> getByEmail(@PathVariable String email)
	{
		return ResponseEntity.ok(userService.getByEmail(email));
	}

	@PutMapping
	public ResponseEntity<UserResponse> update(@RequestBody UserUpdateRequest userUpdateRequest)
	{

		UserResponse user = userService.update(userUpdateRequest);

		rabbitTemplate.convertAndSend(rabbitMQConfiguration.getLogQueueName(), new DbLog("[Emlakcepte-Main-Service] -> [HTTP - PUT Request] Update User : requested. UserName: "+user.getName()+" Response code:"+HttpStatus.OK,LocalDateTime.now()));

		return ResponseEntity.ok(user);
	}

	@PostMapping("/process/{cardNo}/{extensionAmount}")
	public ResponseEntity<Payment> processPayment(@RequestBody User user, @PathVariable String cardNo,@PathVariable Integer extensionAmount) {

		if(Objects.isNull(user.getPackageExpireDate()))
		{
			user.setPackageExpireDate(LocalDateTime.now());
		}
		return new ResponseEntity<>(userService.processPayment(user,cardNo,extensionAmount),HttpStatus.OK);
	}

	// GET /users/{id} -> /users/1

}
