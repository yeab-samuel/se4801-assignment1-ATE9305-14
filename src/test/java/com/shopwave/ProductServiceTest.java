// Student number: ATE/9305/14
package com.shopwave;

import com.shopwave.dto.CreateProductRequest;
import com.shopwave.dto.ProductDTO;
import com.shopwave.exception.ProductNotFoundException;
import com.shopwave.mapper.ProductMapper;
import com.shopwave.model.Product;
import com.shopwave.repository.ProductRepository;
import com.shopwave.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_happyPath() {
        Product saved = Product.builder()
                .id(1L).name("Laptop").price(BigDecimal.valueOf(999)).stock(10).build();
        ProductDTO dto = new ProductDTO(1L, "Laptop", null, BigDecimal.valueOf(999), 10, null, null);

        when(productRepository.save(any())).thenReturn(saved);
        when(productMapper.toDTO(saved)).thenReturn(dto);

        CreateProductRequest req = new CreateProductRequest(
                "Laptop", "A laptop", BigDecimal.valueOf(999), 10, null);
        ProductDTO result = productService.createProduct(req);

        assertNotNull(result);
        assertEquals("Laptop", result.name());
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void getProductById_notFound_throwsException() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(99L));
    }
}