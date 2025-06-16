package com.tom.aws.awstest.product.mapper;

import com.tom.aws.awstest.product.dto.ProductRequest;
import com.tom.aws.awstest.product.dto.ProductResponse;
import com.tom.aws.awstest.product.model.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-16T00:31:33-0300",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.41.0.v20250213-1140, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toProduct(ProductRequest request) {
        if ( request == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.name( request.name() );
        product.quantity( request.quantity() );
        product.price( request.price() );
        product.manufacturer( request.manufacturer() );

        return product.build();
    }

    @Override
    public ProductResponse fromProduct(Product product) {
        if ( product == null ) {
            return null;
        }

        long id = 0L;
        String name = null;
        int quantity = 0;
        BigDecimal price = null;
        String manufacturer = null;
        boolean active = false;
        LocalDateTime created = null;
        LocalDateTime updated = null;

        if ( product.getId() != null ) {
            id = product.getId();
        }
        name = product.getName();
        quantity = product.getQuantity();
        price = product.getPrice();
        manufacturer = product.getManufacturer();
        active = product.isActive();
        created = product.getCreatedAt();
        updated = product.getUpdatedAt();

        ProductResponse productResponse = new ProductResponse( id, name, quantity, price, manufacturer, active, created, updated );

        return productResponse;
    }
}
