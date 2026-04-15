package com.ecoomerce.sportscenter.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testSetAndGetFields() {
        Product product = new Product();

        product.setName("Laptop");

        assertEquals("Laptop", product.getName());
    }

    @Test
    void testDefaultValues() {
        Product product = new Product();

        assertNull(product.getName());
    }

    @Test
    void testSetId() {
        Product product = new Product();

        product.setId(10);

        assertEquals(Integer.valueOf(10), product.getId());
    }

    @Test
    void testMultipleFieldUpdate() {
        Product product = new Product();

        product.setName("Phone");
        product.setName("Tablet");

        assertEquals("Tablet", product.getName());
    }

    @Test
    void testNullNameAssignment() {
        Product product = new Product();

        product.setName(null);

        assertNull(product.getName());
    }
}