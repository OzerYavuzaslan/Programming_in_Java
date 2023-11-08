package com.ozeryavuzaslan.stockservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozeryavuzaslan.basedomains.dto.stocks.CategoryDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import com.ozeryavuzaslan.basedomains.util.CacheManagementService;
import com.ozeryavuzaslan.stockservice.model.Category;
import com.ozeryavuzaslan.stockservice.model.Stock;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.StockPropertySetter;
import com.ozeryavuzaslan.stockservice.repository.CategoryRepository;
import com.ozeryavuzaslan.stockservice.repository.ReservedStockRepository;
import com.ozeryavuzaslan.stockservice.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("Some of the StockService test cases")
public class StockServiceImplTest {
    @Mock
    ModelMapper modelMapperMock;
    @Mock
    ObjectMapper objectMapperMock;
    @Mock
    StockRepository stockRepositoryMock;
    @Mock
    CategoryRepository categoryRepositoryMock;
    @Mock
    StockPropertySetter stockPropertySetterMock;
    @Mock
    CacheManagementService cacheManagementService;
    @Mock
    ReservedStockRepository reservedStockRepositoryMock;
    @InjectMocks
    StockServiceImpl stockServiceImpl;

    long productID;
    long categoryID;
    Stock mockStock;
    Category mockCategory;
    StockDTO mockStockDTO;
    CategoryDTO mockCategoryDTO;
    UUID mockStockUUID;
    UUID mockCategoryUUID;
    LocalDateTime updateAndInsertDates;

    @BeforeEach
    public void beforeEach() {
        productID = 2404L;
        categoryID = 2102L;
        updateAndInsertDates = LocalDateTime.now();
        mockStockUUID = UUID.fromString("6e67d820-00ad-4687-a201-a6652ffb20ac");
        mockCategoryUUID = UUID.fromString("fd823f22-8d94-4cfc-b9d4-5621cb601743");
        String productName = "RTX 4090 Graphic Card";
        String brandName = "ASUS";
        String brandCompanyEmail = "night_of_elf@hotmail.com";
        int quantity = 1111;
        double price = 2500D;
        double discountAmount = 0D;
        double discountPercentage = 0D;

        mockCategory = new Category(categoryID, mockCategoryUUID, "Elektronik Eşya", updateAndInsertDates, updateAndInsertDates);
        mockStock = new Stock(productID, mockStockUUID, productName, brandName,
                brandCompanyEmail, quantity, price, discountAmount,discountPercentage,
                null, mockCategory, updateAndInsertDates, updateAndInsertDates);

        mockCategoryDTO = new CategoryDTO(categoryID, mockCategoryUUID, "Elektronik Eşya", updateAndInsertDates, updateAndInsertDates);
        mockStockDTO = new StockDTO(productID, mockStockUUID, productName, brandName, brandCompanyEmail, quantity, price,
                discountAmount, discountPercentage ,mockCategoryDTO, updateAndInsertDates, updateAndInsertDates,
                null);
    }

    @Test
    void contextLoads() {
    }

    /**
     * ***Verify Kullanım Nedenleri***
     * Beklenen Davranışın Gerçekleştiğini Doğrulamak:
     * verify ile bir metodun çağrıldığından emin olmak isteyebiliriz.
     * Örneğin, bir metodun çağrılması beklenen bir iş akışını tetikliyorsa, bu akışın gerçekten gerçekleştiğini doğrulamak isteriz.
     * <p>
     * Yan Etki Kontrolü:
     * Bazı metodlar yan etkilere neden olabilir (örneğin, veritabanına yazmak).
     * Bu methodların beklenen koşullar altında çağrıldığını doğrulamak, yan etkilerin kontrol altında olduğundan emin olmak için önemlidir.
     * <p>
     * Kod Kapsamını Sağlamak:
     * Testler sırasında belirli kod parçalarının çalıştırıldığından emin olmak için verify kullanılabilir.
     * Bu, testlerinizin beklenen kapsama sahip olduğundan emin olmanıza yardımcı olur.
     * <p>
     * Çağrı Sırasını Doğrulamak:
     * Bazı durumlarda, methodların belirli bir sırada çağrılması gerekebilir.
     * verify kullanarak, bu çağrı sırasını doğrulayabiliriz.
     * <p>
     * İhtiyaç Duyulmayan Çağrıları Tespit Etmek:
     * Bir metodun hiç çağrılmaması gerekiyorsa, verify(mock, never()).method(); şeklinde kullanabiliriz.
     * Yani method imzası verir gibi, test ettiğimiz spesifik methodun içinde hiçbir şekilde çağırılmamasını istediğimiz
     * methodları kontrol etmek için verify kullanabiliriz.
     */
    @Test
    public void should_get_stock_with_the_correct_product_id() {
        //productID'si (stockID) olan bir mockStock yaratmıştık. O yüzden burada stockID setlemiyoruz.
        when(stockRepositoryMock.findById(productID)).thenReturn(Optional.of(mockStock));
        when(modelMapperMock.map(any(Stock.class), eq(StockDTO.class))).thenReturn(mockStockDTO);
        StockDTO actualStockDTO = stockServiceImpl.getByProductID(productID);

        assertNotNull(actualStockDTO, "The retrieved StockDTO should not be null!");
        assertEquals(mockStockDTO, actualStockDTO);

        verify(modelMapperMock).map(any(), eq(StockDTO.class));
        verify(stockRepositoryMock).findById(productID);
    }
}
