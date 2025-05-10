package com.tom.aws.awstest.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductRequest(
		
        @NotBlank(message = "O nome não pode estar em branco")
        String name,

        @Size(min = 1, max = 10, message = "O minimo é 1 o máximo é 10")
        int quantity,

        @Positive(message = "O preço deve ser maior que zero")
        BigDecimal price,
        
        String manufacturer
){

}
