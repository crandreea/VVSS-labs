package drinkshop.service;

import drinkshop.domain.CategorieBautura;
import drinkshop.domain.Product;
import drinkshop.domain.TipBautura;
import drinkshop.repository.AbstractRepository;
import drinkshop.repository.Repository;
import drinkshop.service.validator.ProductValidator;
import drinkshop.service.validator.ValidationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {

    private ProductService productService;
    private Repository<Integer, Product> mockRepo;
    private ProductValidator validator;
    private CategorieBautura categorieDummy;
    private TipBautura tipDummy;

    @BeforeEach
    void setUp() {
        mockRepo = new AbstractRepository<Integer, Product>() {
            @Override
            protected Integer getId(Product entity) {
                return entity.getId();
            }
        };
        productService = new ProductService(mockRepo);
        validator = new ProductValidator();

        categorieDummy = new CategorieBautura(1, "TEA");
        tipDummy = new TipBautura(1, "BASIC");
    }

    private void addProductWithValidation(Product product) {
        validator.validate(product);
        productService.addProduct(product);
    }

    // Teste ECP

    @Test
    @Tag("ECP")
    @Order(1)
    @DisplayName("TC1_ECP")
    void testAddProductValid_TC1_ECP() {
        // Arrange
        Product product = new Product(10, "Ice tea", 5.0, categorieDummy, tipDummy);

        // Act
        addProductWithValidation(product);

        // Assert
        assertEquals(1, productService.getAllProducts().size(), "Produsul ar trebui sa fie salvat.");
        assertEquals("Ice tea", productService.findById(10).getNume());
    }

    @Test
    @Tag("ECP")
    @Order(2)
    @DisplayName("TC2_ECP: ID invalid (< 0)")
    void testAddProductIdInvalid_TC2_ECP() {
        // Arrange
        Product product = new Product(-2, "Ice tea", 5.0, categorieDummy, tipDummy);

        // Act & Assert
        Exception exception = assertThrows(ValidationException.class, () -> {
            addProductWithValidation(product);
        });

        // Assert mesaj eroare
        assertTrue(exception.getMessage().contains("ID invalid!"));
    }

    @Test
    @Tag("ECP")
    @Order(3)
    @DisplayName("TC3_ECP: Nume invalid")
    void testAddProductNameEmpty_TC3_ECP() {
        // Arrange
        Product product = new Product(11, "", 5.0, categorieDummy, tipDummy);

        // Act & Assert
        Exception exception = assertThrows(ValidationException.class, () -> {
            addProductWithValidation(product);
        });

        assertTrue(exception.getMessage().contains("Numele nu poate fi gol"), "Așteptat mesaj de eroare empty name");
    }

    @Test
    @Tag("ECP")
    @Order(4)
    @DisplayName("TC4_ECP: Preț invalid (< 0)")
    void testAddProductPriceInvalid_TC4_ECP() {
        // Arrange
        Product product = new Product(12, "Ice tea", -5.0, categorieDummy, tipDummy);

        // Act & Assert
        Exception exception = assertThrows(ValidationException.class, () -> {
            addProductWithValidation(product);
        });

        assertTrue(exception.getMessage().contains("Pret invalid!"));
    }

    // Teste BVA

    @Test
    @Tag("BVA")
    @Order(5)
    @DisplayName("TC1_BVA: ID = 1")
    void testAddProductValidIdLimit_TC1_BVA() {
        // Arrange
        Product product = new Product(1, "Ice tea", 12.60, categorieDummy, tipDummy);

        // Act
        addProductWithValidation(product);

        // Assert
        assertEquals(1, productService.getAllProducts().size(), "Product saved successfully for ID = 1");
    }

    @Test
    @Tag("BVA")
    @Order(6)
    @DisplayName("TC2_BVA: ID = 0")
    void testAddProductInvalidIdLimit_TC2_BVA() {
        // Arrange
        Product product = new Product(0, "Ice tea", 12.40, categorieDummy, tipDummy);

        // Act & Assert
        Exception exception = assertThrows(ValidationException.class, () -> {
            addProductWithValidation(product);
        });

        assertTrue(exception.getMessage().contains("ID invalid!"));
    }

    @Test
    @Tag("BVA")
    @Order(7)
    @DisplayName("TC3_BVA: Pret = 0.01")
    void testAddProductValidPriceLimit_TC3_BVA() {
        // Arrange
        Product product = new Product(2, "Ice tea", 0.01, categorieDummy, tipDummy);

        // Act
        addProductWithValidation(product);

        // Assert
        assertEquals(1, productService.getAllProducts().size(), "Product saved for margin price = 0.01");
    }

    @Test
    @Tag("BVA")
    @Order(8)
    @DisplayName("TC4_BVA: Pret = 0.00")
    void testAddProductInvalidPriceLimit_TC4_BVA() {
        // Arrange
        Product product = new Product(3, "Ice tea", 0.00, categorieDummy, tipDummy);

        // Act & Assert
        Exception exception = assertThrows(ValidationException.class, () -> {
            addProductWithValidation(product);
        });

        assertTrue(exception.getMessage().contains("Pret invalid!"));
    }
}
