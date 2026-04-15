package com.ecoomerce.sportscenter.service;

import com.ecoomerce.sportscenter.repository.ProductRepository;
import com.ecoomerce.sportscenter.entity.Product;
import com.ecoomerce.sportscenter.entity.Type;
import com.ecoomerce.sportscenter.entity.Brand;
import com.ecoomerce.sportscenter.model.ProductResponse;
import com.ecoomerce.sportscenter.exceptions.ProductNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Product createMockProduct(int id, String name) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);

        Type type = new Type();
        type.setName("Sports");
        product.setType(type);

        Brand brand = new Brand();
        brand.setName("Nike");
        product.setBrand(brand);

        return product;
    }

    @Test
    void testGetProductById_Success() {

        Product product = createMockProduct(1, "Shoes");

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        ProductResponse result = productService.getProductById(1);

        assertNotNull(result);
        assertEquals(Integer.valueOf(1), result.getId());
        assertEquals("Shoes", result.getName());
    }

    @Test
    void testGetProductById_NotFound() {

        when(productRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(2);
        });
    }

    @Test
    void testProductMappingLogic() {

        Product product = createMockProduct(10, "Laptop");

        when(productRepository.findById(10)).thenReturn(Optional.of(product));

        ProductResponse result = productService.getProductById(10);

        assertNotNull(result);
        assertEquals(Integer.valueOf(10), result.getId());
        assertEquals("Laptop", result.getName());
    }

    @Test
    void testRepositoryInteraction() {

        Product product = createMockProduct(5, "Watch");

        when(productRepository.findById(5)).thenReturn(Optional.of(product));

        productService.getProductById(5);

        verify(productRepository, times(1)).findById(5);
    }
}