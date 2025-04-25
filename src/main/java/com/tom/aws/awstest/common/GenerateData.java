package com.tom.aws.awstest.common;

import java.math.BigDecimal;

import org.springframework.context.annotation.Configuration;

import com.tom.aws.awstest.product.Product;

@Configuration
public class GenerateData implements DatagenUtil {

	protected Product datagen() {
		Product pro = new Product();

        String uniqueName = generateUniqueProductName();
        pro.setName(uniqueName);

		pro.setQuantity(getRandomInt(10, 1000));

		pro.setPrice(BigDecimal.valueOf(getRandomDouble(10, 120)));
		
		pro.setManufacturer(faker.company().name());

		// 90 % chance
		boolean isActive = getRandomNumber(100) < 90;
		pro.setActive(isActive);

		return pro;
	}

    private String generateUniqueProductName() {
        String name;
        do {
            name = faker.commerce().productName();
        } while (generatedNames.contains(name));

        generatedNames.add(name);
        return name;
    }
	
	protected double getRandomDouble(int min, int max) {
		return loc.nextDouble(min, max);
	}

	protected int getRandomInt(int min, int max) {
		return loc.nextInt(min, max);
	}

	protected int getRandomNumber(int value) {
		return loc.nextInt(value);
	}
}

