CREATE TABLE auditable (
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    version BIGINT NOT NULL
);

CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    quantity INTEGER,
    price DECIMAL(19, 2),
    manufacturer VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    
    -- Auditable columns defined directly
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    version BIGINT NOT NULL
) INHERITS (auditable);

CREATE TABLE image (
    id BIGSERIAL PRIMARY KEY,
    image_name VARCHAR(255) NOT NULL,
    object_key VARCHAR(255) NOT NULL UNIQUE,
    object_url VARCHAR(255) NOT NULL UNIQUE,
    content_type VARCHAR(255) NOT NULL,
    size BIGINT NOT NULL,

    -- Auditable columns defined directly
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    version BIGINT NOT NULL
) INHERITS (auditable);

-- INDEXES
CREATE INDEX IF NOT EXISTS idx_product_name ON product(name);
CREATE INDEX IF NOT EXISTS idx_image_name ON image(image_name);

-- COMMENTS 

COMMENT ON TABLE book IS 'Stores information about books, including ISBN and author details.';
COMMENT ON TABLE product IS 'Stores general product information.';
COMMENT ON TABLE image IS 'Stores metadata for uploaded images, like S3 object keys and URLs.';



