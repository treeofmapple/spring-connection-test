package com.tom.aws.awstest.image;

import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-16T00:31:33-0300",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.41.0.v20250213-1140, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class ImageMapperImpl implements ImageMapper {

    @Override
    public ImageResponse fromImage(Image image) {
        if ( image == null ) {
            return null;
        }

        String name = null;
        String contentType = null;
        Long size = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        name = image.getName();
        contentType = image.getContentType();
        size = image.getSize();
        createdAt = image.getCreatedAt();
        updatedAt = image.getUpdatedAt();

        List<ImageResponse.TagDTO> tags = null;

        ImageResponse imageResponse = new ImageResponse( name, contentType, size, createdAt, updatedAt, tags );

        return imageResponse;
    }

    @Override
    public ImagePageResponse fromPage(List<ImageResponse> imagePage, int page, int size, int totalPages) {
        if ( imagePage == null ) {
            return null;
        }

        int page1 = 0;
        page1 = page;
        int size1 = 0;
        size1 = size;
        long totalPages1 = 0L;
        totalPages1 = totalPages;

        List<Image> content = null;

        ImagePageResponse imagePageResponse = new ImagePageResponse( content, page1, size1, totalPages1 );

        return imagePageResponse;
    }
}
