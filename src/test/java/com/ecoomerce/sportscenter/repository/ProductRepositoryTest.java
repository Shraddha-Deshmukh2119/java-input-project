package com.ecoomerce.sportscenter.repository;

import com.ecoomerce.sportscenter.entity.Product;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    @Test
    void testFindById() {

        ProductRepository repo = Mockito.mock(ProductRepository.class);

        Product product = new Product();
        product.setId(1);
        product.setName("Phone");

        Mockito.when(repo.findById(1)).thenReturn(Optional.of(product));

        Optional<Product> result = repo.findById(1);

        assertTrue(result.isPresent());
        assertEquals(Integer.valueOf(1), result.get().getId());
        assertEquals("Phone", result.get().getName());
    }

    @Test
    void testFindById_NotFound() {

        ProductRepository repo = Mockito.mock(ProductRepository.class);

        Mockito.when(repo.findById(2)).thenReturn(Optional.empty());

        Optional<Product> result = repo.findById(2);

        assertFalse(result.isPresent());
    }

    @Test
    void testSaveProduct() {

        ProductRepository repo = Mockito.mock(ProductRepository.class);

        Product product = new Product();
        product.setName("Shoes");

        Mockito.when(repo.save(product)).thenReturn(product);

        Product saved = repo.save(product);

        assertNotNull(saved);
        assertEquals("Shoes", saved.getName());
    }

    @Test
    void testDeleteById() {

        ProductRepository repo = Mockito.mock(ProductRepository.class);

        Mockito.doNothing().when(repo).deleteById(1);

        repo.deleteById(1);

        Mockito.verify(repo, Mockito.times(1)).deleteById(1);
    }
}