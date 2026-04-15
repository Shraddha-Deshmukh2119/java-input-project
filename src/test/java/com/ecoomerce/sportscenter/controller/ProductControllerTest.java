package com.ecoomerce.sportscenter.controller;

import com.ecoomerce.sportscenter.model.ProductResponse;
import com.ecoomerce.sportscenter.service.ProductService;
import com.ecoomerce.sportscenter.service.TypeService;
import com.ecoomerce.sportscenter.service.BrandService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    @Test
    void testGetProduct_Success() {

        ProductService productService = Mockito.mock(ProductService.class);
        TypeService typeService = Mockito.mock(TypeService.class);
        BrandService brandService = Mockito.mock(BrandService.class);

        ProductController controller =
                new ProductController(productService, typeService, brandService);

        ProductResponse response = new ProductResponse();
        response.setId(1);
        response.setName("Bag");

        Mockito.when(productService.getProductById(1)).thenReturn(response);

        ResponseEntity<ProductResponse> result = controller.getProductById(1);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Bag", result.getBody().getName());
    }

    @Test
    void testGetProduct_NotFound() {

        ProductService productService = Mockito.mock(ProductService.class);
        TypeService typeService = Mockito.mock(TypeService.class);
        BrandService brandService = Mockito.mock(BrandService.class);

        ProductController controller =
                new ProductController(productService, typeService, brandService);

        Mockito.when(productService.getProductById(2)).thenReturn(null);

        ResponseEntity<ProductResponse> result = controller.getProductById(2);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertNull(result.getBody());
    }

    @Test
    void testGetProduct_MultipleCalls() {

        ProductService productService = Mockito.mock(ProductService.class);
        TypeService typeService = Mockito.mock(TypeService.class);
        BrandService brandService = Mockito.mock(BrandService.class);

        ProductController controller =
                new ProductController(productService, typeService, brandService);

        ProductResponse response1 = new ProductResponse();
        response1.setId(1);
        response1.setName("Shoes");

        ProductResponse response2 = new ProductResponse();
        response2.setId(2);
        response2.setName("Watch");

        Mockito.when(productService.getProductById(1)).thenReturn(response1);
        Mockito.when(productService.getProductById(2)).thenReturn(response2);

        ResponseEntity<ProductResponse> result1 = controller.getProductById(1);
        ResponseEntity<ProductResponse> result2 = controller.getProductById(2);

        assertEquals("Shoes", result1.getBody().getName());
        assertEquals("Watch", result2.getBody().getName());
    }
}