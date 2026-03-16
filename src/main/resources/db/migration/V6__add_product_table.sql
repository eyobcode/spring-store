CREATE TABLE products (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2),
    category_id TINYINT,

    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);