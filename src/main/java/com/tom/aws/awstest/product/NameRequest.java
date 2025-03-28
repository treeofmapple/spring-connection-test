package com.tom.aws.awstest.product;

import jakarta.validation.constraints.NotBlank;

public record NameRequest(
		
        @NotBlank(message = "O nome não pode estar em branco")
		String name
		) {
}
