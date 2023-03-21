CREATE TABLE users (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(500) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
);

CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);

CREATE TABLE profiles (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    birthdate DATE NOT NULL,
    avatar_id INT NOT NULL DEFAULT 0,
    creation_time TIMESTAMP NOT NULL DEFAULT current_timestamp,
    balance DOUBLE NOT NULL DEFAULT 0.0,
    CONSTRAINT fk_profile_user FOREIGN KEY (username) REFERENCES users (username)
);

CREATE UNIQUE INDEX ix_profiles_username ON profiles (username, id);

CREATE TABLE categories (
    category_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(20) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE chats (
    chat_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    seller_name VARCHAR(50) NOT NULL,
    buyer_name VARCHAR(50) NOT NULL,
    listing_id BIGINT default NULL,
    FOREIGN KEY (seller_name) REFERENCES users (username),
    FOREIGN KEY (buyer_name) REFERENCES users (username)
);

CREATE TABLE items (
    item_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(255) NOT NULL,
    owner_name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    source_path VARCHAR(255) NOT NULL UNIQUE,
    FOREIGN KEY (owner_name) REFERENCES users (username)
);

CREATE TABLE items_categories (
    item_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    CONSTRAINT PK_items_category PRIMARY KEY (item_id, category_id),
    FOREIGN KEY (item_id) REFERENCES items (item_id),
    FOREIGN KEY (category_id) REFERENCES categories (category_id)
);

CREATE TABLE listings (
    listing_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    item_id BIGINT NOT NULL,
    min_price DOUBLE,
    max_price DOUBLE,
    publication_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    visits BIGINT NOT NULL DEFAULT 0,
    is_closed BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (item_id) REFERENCES items (item_id)
);

CREATE TABLE messages (
    message_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    senderName VARCHAR(50) NOT NULL,
    chat_id BIGINT NOT NULL,
    message VARCHAR(255) NOT NULL,
    message_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    seen BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (chat_id) REFERENCES chats (chat_id),
    FOREIGN KEY (senderName) REFERENCES users (username)
);

CREATE TABLE transactions (
    transaction_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    listing_id BIGINT NOT NULL,
    seller_name VARCHAR(50) NOT NULL,
    buyer_name VARCHAR(50) NOT NULL,
    transaction_price DOUBLE NOT NULL,
    transaction_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (seller_name) REFERENCES users (username),
    FOREIGN KEY (buyer_name) REFERENCES users (username),
    FOREIGN KEY (listing_id) REFERENCES listings(listing_id)
);

CREATE TABLE wish_list (
    item_id BIGINT NOT NULL,
    username VARCHAR(50) NOT NULL,
    CONSTRAINT PK_wish_list PRIMARY KEY (item_id, username),
    FOREIGN KEY (item_id) REFERENCES items (item_id),
    FOREIGN KEY (username) REFERENCES users (username)
);

CREATE TABLE bid(
  bid_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  listing_id BIGINT NOT NULL,
  buyer_name VARCHAR(50) NOT NULL,
  price DOUBLE NOT NULL,
  bid_time TIMESTAMP not null default CURRENT_TIMESTAMP,
  FOREIGN KEY (buyer_name) REFERENCES users(username),
  FOREIGN KEY (listing_id) REFERENCES listings(listing_id)
);