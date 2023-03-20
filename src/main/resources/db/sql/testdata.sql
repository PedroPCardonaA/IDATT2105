INSERT INTO users (username, password, enabled)
VALUES 
	('john', '{noop}password123', true),
	('jane', '{noop}password456', true),
	('bob', '{noop}password789', true),
	('alice', '{noop}password111', true),
	('tom', '{noop}password222', true);

INSERT INTO authorities (username, authority)
VALUES 
	('john', 'USER'),
	('jane', 'ADMIN'),
	('bob', 'USER'),
	('alice', 'USER'),
	('tom', 'USER');

INSERT INTO profiles (username, first_name, last_name,email, birthdate)
VALUES
    ('john','John','Smith','johnSmith@gmail.com','1998-05-07'),
    ('jane','jane','Lee','HappyJane@gmail.com','1973-02-14'),
    ('bob','Bob','Garcia','BigGarcia@gmail.com','1958-12-23'),
    ('alice','Alice','Lee','johnSmit2h@gmail.com','2002-01-04'),
    ('tom','Tom','Johnson','johnSmith3@gmail.com','1993-11-21');
INSERT INTO categories (category_name, description)
VALUES 
	('Electronics', 'Items related to electronics'),
	('Clothing', 'Items related to clothing'),
	('Furniture', 'Items related to furniture'),
	('Books', 'Items related to books'),
	('Sports', 'Items related to sports');

INSERT INTO chats (seller_name, buyer_name)
VALUES 
	('john', 'jane'),
	('bob', 'alice'),
	('jane', 'tom'),
	('alice', 'john'),
	('tom', 'bob');

INSERT INTO items (owner_name, description, source_path)
VALUES 
	('john', 'iPhone X', '/images/iphone.jpg'),
	('jane', 'Leather Jacket', '/images/jacket.jpg'),
	('bob', 'Dining Table', '/images/table.jpg'),
	('alice', 'The Great Gatsby', '/images/book.jpg'),
	('tom', 'Basketball', '/images/ball.jpg');

INSERT INTO items_category (item_id, category_id)
VALUES 
	(1, 1),
	(2, 2),
	(3, 3),
	(4, 4),
	(5, 5);

INSERT INTO listings (item_id, min_price, max_price, visits)
VALUES 
	(1, 500, 1000, 50),
	(2, 100, 200, 20),
	(3, 800, 1200, 10),
	(4, 50, 100, 5),
	(5, 20, 50, 100);

INSERT INTO messages (chat_id, message, is_deleted)
VALUES 
	(1, 'Hi John, is the iPhone still available?', false),
	(2, 'Hi Alice, I am interested in the dining table.', false),
	(3, 'Hi Tom, can you lower the price for the basketball?', false),
	(4, 'Hi Jane, I loved The Great Gatsby, thank you for selling it!', false),
	(5, 'Hi Bob, I am interested in the basketball, can you tell me more about it?', false);

INSERT INTO transactions (listing_id, seller_name, buyer_name, transaction_price)
VALUES
(1, 'john', 'jane', 900),
(3, 'bob', 'alice', 1000),
(5, 'tom', 'bob', 30),
(2, 'alice', 'john', 150),
(1, 'jane', 'bob', 700);

INSERT INTO wish_list (item_id, username)
VALUES
(1, 'jane'),
(4, 'bob'),
(2, 'alice'),
(5, 'tom'),
(3, 'john');