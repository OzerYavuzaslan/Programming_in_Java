package emlakcepte.client;

import emlakcepte.client.model.Payment;
import emlakcepte.repository.UserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentServiceClient {
    private final UserRepository userRepository;

    public PaymentServiceClient(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Payment processPayment(Integer userId, String cardNo,Integer extensionAmount) {
        var user = userRepository.getReferenceById(userId);

        Payment payment = new Payment(user.getId(), cardNo);

        String url = "http://localhost:9095/payment/" + cardNo+"/"+extensionAmount;
        RestTemplate template = new RestTemplate();
        HttpEntity<Payment> request = new HttpEntity<>(payment);

        return template.postForObject(url, request, Payment.class);

    }

}


