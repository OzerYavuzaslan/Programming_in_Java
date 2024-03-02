package com.ozeryavuzaslan.stockservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozeryavuzaslan.basedomains.dto.stocks.CategoryDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.CategoryWithoutUUIDDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockWithIgnoredUUID;
import com.ozeryavuzaslan.basedomains.util.CacheManagementService;
import com.ozeryavuzaslan.basedomains.util.TypeFactoryHelper;
import com.ozeryavuzaslan.stockservice.model.Category;
import com.ozeryavuzaslan.stockservice.model.Stock;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.CategoryPropertySetter;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.StockPropertySetter;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.impl.StockPropertySetterImpl;
import com.ozeryavuzaslan.stockservice.repository.CategoryRepository;
import com.ozeryavuzaslan.stockservice.repository.ReservedStockRepository;
import com.ozeryavuzaslan.stockservice.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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
    CacheManagementService cacheManagementServiceMock;
    @Mock
    ReservedStockRepository reservedStockRepositoryMock;
    @Mock
    CategoryPropertySetter categoryPropertySetterMock;
    StockPropertySetter stockPropertySetter;
    StockServiceImpl stockServiceImpl;

    long productID;
    long categoryID;
    Stock mockStock1;
    Stock mockStock2;
    Category mockCategory;
    StockDTO mockStockDTO1;
    StockDTO mockStockDTO2;
    CategoryDTO mockCategoryDTO;
    UUID mockStockUUID;
    UUID mockCategoryUUID;
    LocalDateTime updateAndInsertDates;
    StockWithIgnoredUUID mockStockWithIgnoredUUID;
    CategoryWithoutUUIDDTO mockCategoryWithoutUUIDDTO;
    String stockDTOCannotBeNullMsg;
    List<StockDTO> mockStockDTOList;
    List<Stock> mockStockList;

    @BeforeEach
    public void beforeEach() {
        stockPropertySetter = new StockPropertySetterImpl(categoryPropertySetterMock);
        stockServiceImpl = new StockServiceImpl(
                modelMapperMock,
                objectMapperMock,
                stockRepositoryMock,
                categoryRepositoryMock,
                stockPropertySetter,
                cacheManagementServiceMock,
                reservedStockRepositoryMock);

        productID = 2404L;
        categoryID = 2102L;
        updateAndInsertDates = LocalDateTime.now();
        mockStockUUID = UUID.fromString("6e67d820-00ad-4687-a201-a6652ffb20ac");
        mockCategoryUUID = UUID.fromString("fd823f22-8d94-4cfc-b9d4-5621cb601743");
        String productName = "RTX 4090 Graphic Card";
        String brandName = "ASUS";
        String brandCompanyEmail = "night_of_elf@hotmail.com";
        String categoryName = "Elektronik Eşya";
        int quantity = 1111;
        double price = 2500D;
        double discountAmount = 0D;
        double discountPercentage = 0D;
        stockDTOCannotBeNullMsg = "The retrieved StockDTO should not be null!";

        mockCategory = new Category(categoryID, mockCategoryUUID, categoryName, updateAndInsertDates, updateAndInsertDates);
        mockStock1 = new Stock(productID, mockStockUUID, productName, brandName,
                brandCompanyEmail, quantity, price, discountAmount, discountPercentage,
                null, mockCategory, updateAndInsertDates, updateAndInsertDates);

        mockCategoryDTO = new CategoryDTO(categoryID, mockCategoryUUID, categoryName, updateAndInsertDates, updateAndInsertDates);
        mockStockDTO1 = new StockDTO(productID, mockStockUUID, productName, brandName, brandCompanyEmail, quantity, price,
                discountAmount, discountPercentage, mockCategoryDTO, updateAndInsertDates, updateAndInsertDates,
                null);

        mockCategoryWithoutUUIDDTO = new CategoryWithoutUUIDDTO(null, null, categoryName, updateAndInsertDates, updateAndInsertDates);
        mockStockWithIgnoredUUID = new StockWithIgnoredUUID(null, null, productName, brandName, brandCompanyEmail, quantity, price,
                discountAmount, discountPercentage, mockCategoryWithoutUUIDDTO, updateAndInsertDates, updateAndInsertDates,
                null);

        mockStockDTO2 = mockStockDTO1;
        mockStock2 = mockStock1;
        mockStockList = new ArrayList<>();
        mockStockDTOList = new ArrayList<>();

        mockStockDTOList.add(mockStockDTO1);
        mockStockDTOList.add(mockStockDTO2);
        mockStockList.add(mockStock1);
        mockStockList.add(mockStock2);
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
     * Bu, testlerimizin beklenen kapsama sahip olduğundan emin olmamıza yardımcı olur.
     * <p>
     * Çağrı Sırasını Doğrulamak:
     * Bazı durumlarda, methodların belirli bir sırada çağrılması gerekebilir.
     * verify kullanarak, bu çağrı sırasını doğrulayabiliriz.
     * <p>
     * İhtiyaç Duyulmayan Çağrıları Tespit Etmek:
     * Bir metodun hiç çağrılmaması gerekiyorsa, verify(mock, never()).method(); şeklinde kullanabiliriz.
     * Yani method imzası verir gibi, test ettiğimiz spesifik methodun içinde hiçbir şekilde çağırılmamasını istediğimiz
     * methodları kontrol etmek için verify kullanabiliriz.
     * <p>
     * any() --> Aşağıdaki kodtan örneklersek Stock.class'ından türemiş herhangi bir nesneyi alabileceğini ifade ederiz.
     * Yani any() fonksiyonu, herhangi bir argümanı kabul etmek için kullanılır.
     * <p>
     * eq() fonksiyonu, belirli bir argümanın eşleşmesi gerektiğini belirtir.
     * Aşağıdaki örneğe göre StockDTO.class ile tam olarak eşleşen nesneleri kabul eder.
     */
    @Test
    public void should_get_stock_with_the_correct_product_id() {
        //productID'si (stockID) olan bir mockStock yaratmıştık. O yüzden burada stockID setlemiyoruz.
        when(stockRepositoryMock.findById(productID)).thenReturn(Optional.of(mockStock1));
        when(modelMapperMock.map(any(Stock.class), eq(StockDTO.class))).thenReturn(mockStockDTO1);
        StockDTO actualStockDTO = stockServiceImpl.getByProductID(productID);

        assertNotNull(actualStockDTO, stockDTOCannotBeNullMsg);
        assertEquals(mockStockDTO1, actualStockDTO);

        verify(modelMapperMock).map(any(Stock.class), eq(StockDTO.class));
        verify(stockRepositoryMock).findById(productID);
    }

    @Test
    @DisplayName("Test saveOrUpdateStock with new stock entry and new category")
    void should_insert_new_stock_with_new_category() {
        when(stockRepositoryMock.findByProductCode(mockStockWithIgnoredUUID.getProductCode())).thenReturn(Optional.empty());
        when(categoryRepositoryMock.findByName(mockStockWithIgnoredUUID.getCategory().getName())).thenReturn(Optional.empty());
        when(categoryRepositoryMock.save(any(Category.class))).thenReturn(mockCategory);
        when(modelMapperMock.map(any(CategoryWithoutUUIDDTO.class), eq(Category.class))).thenReturn(mockCategory);
        when(modelMapperMock.map(eq(mockCategory), eq(CategoryWithoutUUIDDTO.class))).thenReturn(mockCategoryWithoutUUIDDTO);
        when(modelMapperMock.map(eq(mockStockWithIgnoredUUID), eq(Stock.class))).thenReturn(mockStock1);
        when(stockRepositoryMock.save(any(Stock.class))).thenReturn(mockStock1);
        when(modelMapperMock.map(any(Stock.class), eq(StockDTO.class))).thenReturn(mockStockDTO1);

        StockDTO actualStockDTO = stockServiceImpl.saveOrUpdateStock(mockStockWithIgnoredUUID);

        assertNotNull(actualStockDTO, stockDTOCannotBeNullMsg);
        assertEquals(mockStockDTO1, actualStockDTO);
        verify(stockRepositoryMock).save(any(Stock.class));
        verify(categoryRepositoryMock).findByName(mockStockWithIgnoredUUID.getCategory().getName());
        verify(categoryRepositoryMock).save(any(Category.class));
        verify(categoryPropertySetterMock).setSomeProperties(any(CategoryWithoutUUIDDTO.class), anyBoolean(), anyBoolean());
    }

    @Test
    @DisplayName("Test updateStocks")
    void should_update_stocks_with_correct_input() {
        Type stockListType = TypeFactoryHelper.constructCollectionType(Stock.class);
        when(modelMapperMock.map(eq(mockStockDTOList), eq(stockListType))).thenReturn(mockStockList);

        List<Stock> actualStockList = modelMapperMock.map(mockStockDTOList, stockListType);

        assertEquals(mockStockList, actualStockList);
        verify(modelMapperMock).map(eq(mockStockDTOList), eq(stockListType));
    }

















}
