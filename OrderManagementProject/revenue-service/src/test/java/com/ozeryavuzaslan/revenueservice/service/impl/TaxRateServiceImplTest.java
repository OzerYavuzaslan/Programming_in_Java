package com.ozeryavuzaslan.revenueservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.revenues.TaxRateDTO;
import com.ozeryavuzaslan.basedomains.dto.revenues.enums.TaxRateType;
import com.ozeryavuzaslan.basedomains.util.CacheManagementService;
import com.ozeryavuzaslan.revenueservice.exception.TaxRateNotFoundException;
import com.ozeryavuzaslan.revenueservice.model.TaxRate;
import com.ozeryavuzaslan.revenueservice.repository.TaxRateRepository;
import com.ozeryavuzaslan.revenueservice.service.TaxRateService;
import com.ozeryavuzaslan.revenueservice.util.CacheManagementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Some of the TaxRateService test cases")
public class TaxRateServiceImplTest {
    private final TaxRateService taxRateService;
    TaxRateRepository taxRateRepositoryMock;
    ModelMapper modelMapperMock;
    CacheManagementService cacheManagementServiceMock;
    TaxRateService taxRateServiceMock;
    List<TaxRate> taxRateEntityList = new ArrayList<>();
    int threadCount;
    ExecutorService executorService;
    CountDownLatch countDownLatch;
    Random random;
    int[] taxMonthRange;

    @Value("${tax.rate.not.found}")
    private String taxRateNotFoundMsg;

    @Autowired
    public TaxRateServiceImplTest(TaxRateService taxRateService) {
        this.taxRateService = taxRateService;
    }

    @BeforeEach
    public void beforeEach() {
        taxRateRepositoryMock = mock(TaxRateRepository.class);
        modelMapperMock = mock(ModelMapper.class);
        cacheManagementServiceMock = mock(CacheManagementServiceImpl.class);
        taxRateServiceMock = new TaxRateServiceImpl(modelMapperMock, taxRateRepositoryMock, cacheManagementServiceMock);

        taxRateEntityList.clear();
        threadCount = 32;
        executorService = Executors.newFixedThreadPool(threadCount);
        countDownLatch = new CountDownLatch(threadCount);
        random = new Random();
        taxMonthRange = new int[]{12, 1};

        //for yerine böyle yapmayı uygun gördüm (rangeClosed ile 1 ve 12 dâhil oluyor)
        taxRateEntityList = IntStream.rangeClosed(1, 12)
                .mapToObj(idAndMonth -> new TaxRate(
                        (long) idAndMonth,
                        2023,
                        idAndMonth,
                        idAndMonth <= 6 ? 18D : 20D,
                        TaxRateType.KDV,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                )).toList(); // collect(Collectors.toList());
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void should_get_correct_tax_rate_with_valid_inputs_db_dependent() {
        TaxRateDTO actualTaxRateDTO = taxRateService.getTaxRate(2023, 10, TaxRateType.KDV);
        TaxRateDTO expectedTaxRateDTO = new TaxRateDTO(261, 2023, 10, 20D, TaxRateType.KDV);
        assertEquals(expectedTaxRateDTO, actualTaxRateDTO);
    }

    @Test
    public void should_throw_tax_rate_not_found_exception_when_inputs_are_invalid_db_dependent() {
        Class<TaxRateNotFoundException> expectedExceptionType = TaxRateNotFoundException.class;

        TaxRateNotFoundException thrownException = assertThrows(expectedExceptionType,
                () -> taxRateService.getTaxRate(2024, 1, TaxRateType.KDV));

        assertEquals(taxRateNotFoundMsg, thrownException.getMessage());
    }

    @Test
    public void should_get_correct_tax_rate_with_valid_inputs() {
        TaxRate taxRate = new TaxRate(1L, 2023, 9, 20.0, TaxRateType.KDV, LocalDateTime.now(), LocalDateTime.now());
        Optional<TaxRate> taxRateOptional = Optional.of(taxRate);
        when(taxRateRepositoryMock.findByYearAndMonthAndTaxRateType(2023, 9, TaxRateType.KDV)).thenReturn(taxRateOptional);

        TaxRateDTO expectedTaxRateDTO = new TaxRateDTO(1L, 2023, 9, 20.0, TaxRateType.KDV);
        when(modelMapperMock.map(taxRate, TaxRateDTO.class)).thenReturn(expectedTaxRateDTO);
        TaxRateDTO actualTaxRateDTO = taxRateServiceMock.getTaxRate(2023, 9, TaxRateType.KDV);

        assertNotNull(actualTaxRateDTO);
        assertEquals(expectedTaxRateDTO, actualTaxRateDTO);
    }

    @Test
    public void should_throw_tax_rate_not_found_exception_when_inputs_are_invalid() {
        Class<TaxRateNotFoundException> expectedExceptionType = TaxRateNotFoundException.class;
        assertThrows(expectedExceptionType,
                () -> taxRateServiceMock.getTaxRate(2023, 1, TaxRateType.KDV));
    }

    /**
     * Mock Nesnelerin Oluşturulması:
     * TaxRateRepository, ModelMapper, ve CacheManagementService mock (taklit) nesneleri oluşturulur.
     * Bu mocklar, gerçek nesnelerin yerine test sırasında kullanılmak üzere hazırlanır ve beklenen davranışları taklit ederler.
     * <p>
     * Servis İmplementasyonunun Oluşturulması:
     * Gerçek TaxRateService implementasyonu olan TaxRateServiceImpl nesnesi yaratılırken yukarıda oluşturulan mock nesneler constructor'a parametre olarak verilir.
     * <p>
     * Pageable Nesnesinin Oluşturulması:
     * Test edilecek veri setinin büyüklüğü ve sıralamasını belirleyecek olan Pageable objesi oluşturulur.
     * <p>
     * Test Veri Setinin Oluşturulması:
     * IntStream kullanarak 1'den 12'ye kadar olan sayılar (ayları temsil eder) için TaxRate nesneleri oluşturulur ve bir List'e dönüştürülür.
     * <p>
     * Sayfa Nesnesinin Oluşturulması ve Dönüş Değerinin Belirlenmesi:
     * Oluşturulan TaxRate nesneleri listesi, Page nesnesine dönüştürülür ve bu sayfa nesnesi taxRateRepositoryMock.findAll() metodunun çağrıldığında döneceği değer olarak ayarlanır.
     * <p>
     * ModelMapper Mock Davranışının Ayarlanması:
     * Her TaxRate nesnesi için ModelMapper'ın bu nesneyi TaxRateDTO'ya çevireceği davranış tanımlanır.
     * <p>
     * Servis Metodunun Çağrılması:
     * taxRateService.getAllTaxRates(pageable) metodu çağrılır ve dönen sonuç taxRateDTOList değişkenine atanır.
     * <p>
     * Sonuçların Doğrulanması (Assert):
     * Elde edilen taxRateDTOList'in boş olmadığı (assertNotNull) ve beklenen büyüklükte olduğu (assertEquals) kontrol edilir.
     * <p>
     * Elemanların Karşılaştırılması:
     * taxRateEntityList ve taxRateDTOList listelerindeki her bir elemanın karşılıklı olarak karşılaştırılması yapılır.
     * Her bir eleman için id, yıl, ay, oran ve vergi tipi (TaxRateType) gibi özelliklerin doğru bir şekilde eşleşip eşleşmediği test edilir (assertEquals).
     * Eğer herhangi bir karşılaştırmada bir farklılık varsa test başarısız olur.
     * <p>
     * Bu method, bahsettiğim adımları kullanarak TaxRateServiceImpl'ın getAllTaxRates methodunun beklenen işlevi doğru bir şekilde yerine getirip getirmediğini test eder.
     */
    @Test
    public void should_get_all_tax_rates() {
        Pageable pageable = PageRequest.of(0, 25);
        Page<TaxRate> taxRatePage = new PageImpl<>(taxRateEntityList, pageable, taxRateEntityList.size());
        when(taxRateRepositoryMock.findAll(pageable)).thenReturn(taxRatePage);

        taxRateEntityList.forEach(taxRateEntity ->
                when(modelMapperMock.map(taxRateEntity, TaxRateDTO.class))
                        .thenReturn(new TaxRateDTO(
                                taxRateEntity.getId(),
                                taxRateEntity.getYear(),
                                taxRateEntity.getMonth(),
                                taxRateEntity.getRate(),
                                TaxRateType.valueOf(taxRateEntity.getTaxRateType().toString())
                        )));

        List<TaxRateDTO> taxRateDTOList = taxRateServiceMock.getAllTaxRates(pageable);
        assertNotNull(taxRateDTOList);
        assertEquals(taxRateEntityList.size(), taxRateDTOList.size());

        for (int i = 0; i < taxRateEntityList.size(); i++) {
            final TaxRate taxRateEntity = taxRateEntityList.get(i);
            final TaxRateDTO taxRateDTO = taxRateDTOList.get(i);

            assertAll("TaxRate and TaxRateDTO should be equal",
                    () -> assertEquals(taxRateEntity.getId(), taxRateDTO.getId(), "IDs should match"),
                    () -> assertEquals(taxRateEntity.getYear(), taxRateDTO.getYear(), "Years should match"),
                    () -> assertEquals(taxRateEntity.getMonth(), taxRateDTO.getMonth(), "Months should match"),
                    () -> assertEquals(taxRateEntity.getRate(), taxRateDTO.getRate(), "Rates should match"),
                    () -> assertEquals(taxRateEntity.getTaxRateType(), taxRateDTO.getTaxRateType(), "Tax rate types should match"));
        }
    }

    /**
     * should_get_all_tax_rates() test methodunun kısmi multithreaded versiyonu.
     * Bu senaryoda aynı anda servis katmanına 10 isteğin geldiğini varsaydım.
     * <p>
     * Belirttiğim sayıda iş parçacığını yönetmek için ExecutorService kullandım. newFixedThreadPool static methodu
     * yalnızca sabit sayıda iş parçacığı çalıştırmak için bir thread havuzu oluşturur.
     * <p>
     * Senkronizasyonu sağlamak için CountDownLatch classından faydalandım. Belirttiğim thread sayısı kadar counter tutar.
     * Her iş parçacığı bittikçe "countDownLatch" bir kez azaltılacaktır.
     * <p>
     * IntStream.range ile her bir eleman için bir flow/akış oluşturdum. Her bir iterasyon için, executorService.submit çağrısıyla,
     * ExecutorService tarafından yönetilen iş parçacığı/thread havuzuna bir iş/task gönderdim/ekledim.
     * Bu işler/tasklar, havuzdaki iş parçacıkları/threadler
     * tarafından paralel olarak işlenecek. submit metodu ile gönderilen her bir iş (Runnable veya Callable)****, iş parçacığı
     * havuzunda uygun bir iş parçacığına/threade atanacak ve çalıştırılacak.
     * <p>
     * try ... finally bloğu arasında bulunan kodlar, threadler/iş parçacıkları tarafından paralel bir şekilde çalıştırılacak.
     * <p>
     * Her bir iş parçacığı tamamlandığında finally bloğu çalışır. countDownLatch.countDown() çağrısı, CountDownLatch sayacını bir azaltır,
     * böylece main iş parçacığı/threadi tüm iş parçacıklarının/threadlerin tamamlandığını takip edebilir.
     * <p>
     * countDownLatch.await(); ile main threadin bütün threadlerin işlerini bitirmesini sağlıyoruz.
     * <p>
     * service.shutdown(); ile ExecutorService'i kapatmak için shutdown çağrısı yapılır.
     * Bu, havuzdaki tüm iş parçacıklarının tamamlandığından emin olmak ve kaynakları serbest bırakmak içindir.
     * ***
     * Runnable ve Callable Java'da belirli bir görevi temsil eden iki farklı interfacedir.
     * Bunlar metodlar veya işler olarak düşünülebilir ve bir iş parçacığının (thread) çalıştırabileceği işin ne olacağını tanımlarlar.
     * Bu interfacelerin kendileri iş parçacığı/thread değildir, ancak iş parçacığı/thread tarafından çalıştırılan kodu tanımlarlar.
     * Runnable --> void run() methodu içinde threadin çalıştıracağı işi tanımlar. Exception fırlatmaz; return yapmaz.
     * Callable --> hangi tipte dönüş yapacaksak Callable'mızı ona göre oluştururuz. Ör: Callable<String> run() gibi...
     * Exception fırlatabilir, return yapar.
     * ***
     *
     * @throws InterruptedException
     */
    @Test
    public void should_handle_multiple_requests_concurrently_to_get_all_tax_rates_for_each_request() throws InterruptedException {
        Pageable pageable = PageRequest.of(0, 25);
        Page<TaxRate> taxRatePage = new PageImpl<>(taxRateEntityList, pageable, taxRateEntityList.size());
        when(taxRateRepositoryMock.findAll(pageable)).thenReturn(taxRatePage);

        taxRateEntityList.forEach(taxRateEntity ->
                when(modelMapperMock.map(taxRateEntity, TaxRateDTO.class))
                        .thenReturn(new TaxRateDTO(
                                taxRateEntity.getId(),
                                taxRateEntity.getYear(),
                                taxRateEntity.getMonth(),
                                taxRateEntity.getRate(),
                                TaxRateType.valueOf(taxRateEntity.getTaxRateType().toString())
                        )));

        IntStream.range(0, threadCount).forEach(i -> executorService.submit(() -> {
            try {
                List<TaxRateDTO> taxRateDTOList = taxRateServiceMock.getAllTaxRates(pageable);
                assertNotNull(taxRateDTOList);
                assertEquals(taxRateEntityList.size(), taxRateDTOList.size());

                for (int j = 0; j < taxRateEntityList.size(); j++) {
                    final TaxRate taxRateEntity = taxRateEntityList.get(j);
                    final TaxRateDTO taxRateDTO = taxRateDTOList.get(j);

                    assertAll("TaxRate and TaxRateDTO should be equal",
                            () -> assertEquals(taxRateEntity.getId(), taxRateDTO.getId(), "IDs should match"),
                            () -> assertEquals(taxRateEntity.getYear(), taxRateDTO.getYear(), "Years should match"),
                            () -> assertEquals(taxRateEntity.getMonth(), taxRateDTO.getMonth(), "Months should match"),
                            () -> assertEquals(taxRateEntity.getRate(), taxRateDTO.getRate(), "Rates should match"),
                            () -> assertEquals(taxRateEntity.getTaxRateType(), taxRateDTO.getTaxRateType(), "Tax rate types should match"));
                }
            } finally {
                countDownLatch.countDown();
            }
        }));

        countDownLatch.await();
        executorService.shutdown();
    }

    @Test
    public void should_handle_multiple_requests_concurrently_to_correct_tax_rate_with_valid_inputs() throws InterruptedException {
        IntStream.range(0, threadCount).forEach(i -> executorService.submit(() -> {
            try {
                int month = random.nextInt(taxMonthRange[0]) + taxMonthRange[1];
                TaxRate taxRate = new TaxRate(1L, 2023, month, 20.0, TaxRateType.KDV, LocalDateTime.now(), LocalDateTime.now());
                Optional<TaxRate> taxRateOptional = Optional.of(taxRate);
                when(taxRateRepositoryMock.findByYearAndMonthAndTaxRateType(2023, month, TaxRateType.KDV)).thenReturn(taxRateOptional);

                TaxRateDTO expectedTaxRateDTO = new TaxRateDTO(1L, 2023, month, 20.0, TaxRateType.KDV);
                when(modelMapperMock.map(taxRate, TaxRateDTO.class)).thenReturn(expectedTaxRateDTO);
                TaxRateDTO actualTaxRateDTO = taxRateServiceMock.getTaxRate(2023, month, TaxRateType.KDV);

                assertNotNull(actualTaxRateDTO);
                assertEquals(expectedTaxRateDTO, actualTaxRateDTO);
            } finally {
                countDownLatch.countDown();
            }
        }));

        countDownLatch.await();
        executorService.shutdown();
    }
}