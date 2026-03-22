// Student number: ATE/9305/14
package com.shopwave.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank String name,
        String description,
        @NotNull @Positive BigDecimal price,
        @NotNull @Min(0) Integer stock,
        Long categoryId
) {}