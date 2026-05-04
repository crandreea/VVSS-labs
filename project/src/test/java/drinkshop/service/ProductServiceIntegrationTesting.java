package drinkshop.service;

import drinkshop.domain.Product;
import drinkshop.repository.AbstractRepository;
import drinkshop.repository.Repository;
import drinkshop.service.validator.ProductValidator;
import drinkshop.service.validator.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceIntegrationTesting {

    private ProductService productService;
    private ProductValidator realValidator;
    private Repository<Integer, Product> realRepo;

    @Mock
    private Repository<Integer, Product> mockRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        realValidator = new ProductValidator();
        realRepo = new AbstractRepository<Integer, Product>() {
            @Override
            protected Integer getId(Product entity) {
                return entity.getId();
            }
        };
    }

    // --- STEP 2: Integrare V (S + V, R mocked) ---

    @Test
    void testAddProduct_Step2_V_Valid() {
        // Arrange
        productService = new ProductService(mockRepo, realValidator);
        Product p = new Product(1, "Valid Name", 10.0, null, null);

        // Act
        assertDoesNotThrow(() -> productService.addProduct(p));

        // Assert & Verify
        verify(mockRepo, times(1)).save(p);
    }

    @Test
    void testAddProduct_Step2_V_Invalid() {
        // Arrange
        productService = new ProductService(mockRepo, realValidator);
        Product p = new Product(-1, "", -5.0, null, null);

        // Act & Assert
        assertThrows(ValidationException.class, () -> productService.addProduct(p));

        // Verify R was NOT called because validation failed
        verify(mockRepo, times(0)).save(p);
    }

    // --- STEP 3: Integrare R (S + V + R) ---

    @Test
    void testAddProduct_Step3_R_Valid() {
        // Arrange
        productService = new ProductService(realRepo, realValidator);
        Product p = new Product(2, "Another Valid", 15.0, null, null);

        // Act
        productService.addProduct(p);

        // Assert
        assertEquals(p, realRepo.findOne(2));
        assertEquals(1, realRepo.findAll().size());
    }

    @Test
    void testAddProduct_Step3_R_Duplicate() {
        // Arrange
        productService = new ProductService(realRepo, realValidator);
        Product p1 = new Product(3, "Unique", 20.0, null, null);
        productService.addProduct(p1);

        Product p2 = new Product(3, "Duplicate ID", 25.0, null, null);

        // Act & Assert
        // AbstractRepository throws IllegalArgumentException for duplicate ID
        assertThrows(IllegalArgumentException.class, () -> productService.addProduct(p2));
    }
}
