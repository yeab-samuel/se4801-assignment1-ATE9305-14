// Student number: ATE/9305/14
package com.shopwave.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String categoryName,
        LocalDateTime createdAt
) {}