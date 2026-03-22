// Student number: ATE/9305/14
package com.shopwave;

import com.shopwave.model.Category;
import com.shopwave.model.Product;
import com.shopwave.repository.CategoryRepository;
import com.shopwave.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(ProductRepository productRepository,
                               CategoryRepository categoryRepository) {
        return args -> {
            Category electronics = categoryRepository.save(
                    Category.builder()
                            .name("Electronics")
                            .description("Electronic devices and gadgets")
                            .build()
            );

            productRepository.save(Product.builder()
                    .name("Laptop Pro")
                    .description("High performance laptop")
                    .price(BigDecimal.valueOf(1299.99))
                    .stock(15)
                    .category(electronics)
                    .build());

            productRepository.save(Product.builder()
                    .name("Wireless Mouse")
                    .description("Ergonomic wireless mouse")
                    .price(BigDecimal.valueOf(29.99))
                    .stock(50)
                    .build());

            productRepository.save(Product.builder()
                    .name("USB-C Hub")
                    .description("7-in-1 USB-C hub")
                    .price(BigDecimal.valueOf(49.99))
                    .stock(30)
                    .build());
        };
    }
}