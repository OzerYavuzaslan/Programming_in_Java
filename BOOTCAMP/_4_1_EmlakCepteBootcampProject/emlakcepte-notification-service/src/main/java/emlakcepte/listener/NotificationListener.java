package emlakcepte.listener;

import emlakcepte.model.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

	@RabbitListener(queues = "emlakcepte.notification")
	public void notificationListener(User user) {

		System.out.printf("%s ,%s , %s", user.getName(), user.getEmail(), user.getPassword() );
	}

}
