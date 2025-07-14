package com.tom.aws.awstest.image;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-13T23:13:15-0300",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.42.0.v20250526-2018, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ImageMapperImpl implements ImageMapper {

    @Override
    public ImageResponse toResponse(Image image) {
        if ( image == null ) {
            return null;
        }

        long id = 0L;
        String name = null;
        String objectUrl = null;
        String contentType = null;
        long size = 0L;
        ZonedDateTime createdAt = null;
        ZonedDateTime updatedAt = null;

        if ( image.getId() != null ) {
            id = image.getId();
        }
        name = image.getName();
        objectUrl = image.getObjectUrl();
        contentType = image.getContentType();
        if ( image.getSize() != null ) {
            size = image.getSize();
        }
        createdAt = image.getCreatedAt();
        updatedAt = image.getUpdatedAt();

        ImageResponse imageResponse = new ImageResponse( id, name, objectUrl, contentType, size, createdAt, updatedAt );

        return imageResponse;
    }

    @Override
    public Image mergeData(Image Image, MultipartFile file, String objectKey, String objectUrl) {
        if ( file == null && objectKey == null && objectUrl == null ) {
            return Image;
        }

        if ( file != null ) {
            if ( file.getOriginalFilename() != null ) {
                Image.setName( file.getOriginalFilename() );
            }
            if ( file.getContentType() != null ) {
                Image.setContentType( file.getContentType() );
            }
            Image.setSize( file.getSize() );
        }
        if ( objectKey != null ) {
            Image.setObjectKey( objectKey );
        }
        if ( objectUrl != null ) {
            Image.setObjectUrl( objectUrl );
        }

        return Image;
    }

    @Override
    public List<ImageResponse> toResponseList(List<Image> images) {
        if ( images == null ) {
            return null;
        }

        List<ImageResponse> list = new ArrayList<ImageResponse>( images.size() );
        for ( Image image : images ) {
            list.add( toResponse( image ) );
        }

        return list;
    }
}
