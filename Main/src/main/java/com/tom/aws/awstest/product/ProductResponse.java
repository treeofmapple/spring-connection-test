package com.tom.aws.awstest.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        
        long id,
        String name,
        int quantity,
        BigDecimal price,
        String manufacturer,
        boolean active,
        LocalDateTime created,
        LocalDateTime updated
) {
}
