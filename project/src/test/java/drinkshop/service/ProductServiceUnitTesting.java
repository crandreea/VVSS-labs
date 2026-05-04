package drinkshop.service;

import drinkshop.domain.Product;
import drinkshop.repository.Repository;
import drinkshop.service.validator.ProductValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

// Arhitectura :
// E - Product
// V - ProductValidator
// R - Repository<Integer, Product>
// S - ProductService

// Scenariul (1): V <-- S --> R si ulterior integrare top-down si breadth-first.

public class ProductServiceUnitTesting {

    private ProductService productService;

    @Mock
    private Repository<Integer, Product> productRepo;

    @Mock
    private ProductValidator productValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepo, productValidator);
    }

    @Test
    void testAddProduct_Unit_Valid() {
        // Arrange
        Product p = new Product(1, "Test Product", 10.0, null, null);

        // Act
        assertDoesNotThrow(() -> productService.addProduct(p));

        // Assert & Verify
        verify(productValidator, times(1)).validate(p);
        verify(productRepo, times(1)).save(p);
    }

    @Test
    void testDeleteProduct_Unit_Valid() {
        // Arrange
        int id = 1;

        // Act
        productService.deleteProduct(id);

        // Assert & Verify
        verify(productRepo, times(1)).delete(id);
    }
}
