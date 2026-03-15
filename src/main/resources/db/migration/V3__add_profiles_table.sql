CREATE TABLE profiles (
    id BIGINT PRIMARY KEY,
    bio TEXT,
    phone_number VARCHAR(255),
    loyalty_point INT UNSIGNED DEFAULT 0,
    FOREIGN KEY (id) REFERENCES users(id)
);