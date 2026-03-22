// Student number: ATE/9305/14
package com.shopwave;

import com.shopwave.model.Product;
import com.shopwave.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findByNameContainingIgnoreCase_returnsCorrectResults() {
        productRepository.save(Product.builder()
                .name("Apple iPhone").price(BigDecimal.valueOf(999)).stock(5).build());
        productRepository.save(Product.builder()
                .name("Samsung Galaxy").price(BigDecimal.valueOf(799)).stock(3).build());

        List<Product> results = productRepository.findByNameContainingIgnoreCase("apple");

        assertEquals(1, results.size());
        assertEquals("Apple iPhone", results.get(0).getName());
    }
}