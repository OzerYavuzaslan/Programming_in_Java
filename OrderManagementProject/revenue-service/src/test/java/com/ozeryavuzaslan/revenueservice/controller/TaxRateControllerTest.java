package com.ozeryavuzaslan.revenueservice.controller;

import com.ozeryavuzaslan.basedomains.dto.revenues.TaxRateDTO;
import com.ozeryavuzaslan.basedomains.dto.revenues.enums.TaxRateType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * webEnvironment özelliği dört değer alabilir:
 * <p>
 * MOCK: Varsayılan seçenektir. Spring Web ortamını yükler ama gerçek bir web sunucusu başlatmaz.
 * Controller'lar ve diğer web katmanları mock servlet ortamında test edilir.
 * <p>
 * RANDOM_PORT: Gerçek bir servlet ortamını başlatır ve uygulamayı rastgele seçilen bir portta çalıştırır.
 * Bu, tam entegrasyon testleri için kullanılır ve uygulamanın gerçekte nasıl davranacağını daha iyi yansıtır.
 * TestRestTemplate veya WebTestClient gibi araçlarla end-to-end HTTP testleri yapmak için bu seçenek tercih edilir.
 * <p>
 * DEFINED_PORT: Uygulamayı varsayılan portta (application.properties veya application.yml dosyasında belirtilen server.port ya da 8080 gibi) başlatır.
 * Bu da benzer şekilde gerçek bir web sunucusu başlatır fakat port rastgele değil, önceden tanımlanmıştır.
 * <p>
 * NONE: Web ortamını yüklemek istemiyorsak bu seçeneği kullanabiliriz. Bu, uygulamanın web katmanını yüklemez ve non-web ortamda çalışacak şekilde yapılandırır.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaxRateControllerTest {
    private final TestRestTemplate testRestTemplate;
    int threadCount;
    int[] taxMonthRange;

    @Autowired
    public TaxRateControllerTest(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    @BeforeEach
    public void beforeEach() {
        threadCount = 5000;
        taxMonthRange = new int[]{12, 1};
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void should_handle_multiple_requests_concurrently_to_get_specific_tax_rate_for_each_request_with_correct_inputs_db_dependent() throws InterruptedException {
        int taxYear = 2023;
        runGetSpecificTaxRateConcurrently(taxYear, taxMonthRange, TaxRateType.KDV, threadCount, HttpStatus.OK);
    }

    @Test
    public void should_handle_multiple_requests_concurrently_to_get_not_found_exception_for_each_request_with_wrong_inputs_db_dependent() throws InterruptedException {
        int taxYear = 2024;
        runGetSpecificTaxRateConcurrently(taxYear, taxMonthRange, TaxRateType.KDV, threadCount, HttpStatus.NOT_FOUND);
    }

    private void runGetSpecificTaxRateConcurrently(int taxYear, int[] taxMonthRange, TaxRateType taxRateType, int threadCount, HttpStatus httpStatus) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        String getSpecificTaxRateEndpointUrl = "/api/v1/revenues/taxRate/getSpecificTaxRate";
        Random random = new Random();

        IntStream.range(0, threadCount).forEach(i -> executorService.submit(() -> {
            try {
                int taxMonth = random.nextInt(taxMonthRange[0]) + taxMonthRange[1];
                ResponseEntity<TaxRateDTO> response = testRestTemplate.getForEntity(
                        getSpecificTaxRateEndpointUrl
                                + "?taxYear=" + taxYear
                                + "&taxMonth=" + taxMonth
                                + "&taxRateType=" + taxRateType,
                        TaxRateDTO.class);

                assertEquals(httpStatus, response.getStatusCode());
            } finally {
                countDownLatch.countDown();
            }
        }));

        countDownLatch.await();
        executorService.shutdown();
    }
}
