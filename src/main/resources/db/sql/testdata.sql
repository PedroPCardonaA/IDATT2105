INSERT INTO users (username, password, enabled)
VALUES 
	('john', '{noop}password123', true),
	('jane', '{noop}password456', true),
	('bob', '{noop}password789', true),
	('alice', '{noop}password111', true),
	('tom', '{noop}password222', true),
    ('sarah', '{noop}password333', true),
    ('david', '{noop}password444', true),
    ('emily', '{noop}password555', true),
    ('mike', '{noop}password666', true),
    ('olivia', '{noop}password777', true);

INSERT INTO authorities (username, authority)
VALUES 
	('john', 'USER'),
	('jane', 'ADMIN'),
	('bob', 'USER'),
	('alice', 'USER'),
	('tom', 'USER'),
    ('sarah', 'USER'),
    ('david', 'ADMIN'),
    ('emily', 'USER'),
    ('mike', 'USER'),
    ('olivia', 'ADMIN');

INSERT INTO profiles (username, first_name, last_name,email, birthdate)
VALUES
    ('john','John','Smith','johnSmith@gmail.com','1998-05-07'),
    ('jane','jane','Lee','HappyJane@gmail.com','1973-02-14'),
    ('bob','Bob','Garcia','BigGarcia@gmail.com','1958-12-23'),
    ('alice','Alice','Lee','johnSmit2h@gmail.com','2002-01-04'),
    ('tom','Tom','Johnson','johnSmith3@gmail.com','1993-11-21'),
    ('sarah', 'Sarah', 'Taylor', 'sarahTaylor@gmail.com', '1987-09-18'),
    ('david', 'David', 'Miller', 'davidMiller@gmail.com', '1979-07-25'),
    ('emily', 'Emily', 'Chen', 'emilyChen@gmail.com', '1990-03-12'),
    ('mike', 'Mike', 'Johnson', 'mikeJohnson@gmail.com', '1985-06-30'),
    ('olivia', 'Olivia', 'Davis', 'oliviaDavis@gmail.com', '2000-10-15');
INSERT INTO categories (category_name)
VALUES 
	('Photography'),
	('Animation'),
	('Wallpaper'),
	('Animal'),
	('People'),
	('Cartoon'),
	('Art'),
	('Collectibles');

INSERT INTO chats (seller_name, buyer_name)
VALUES 
	('john', 'jane'),
	('bob', 'alice'),
	('jane', 'tom'),
	('alice', 'john'),
	('tom', 'bob'),
    ('sarah', 'david'),
    ('emily', 'mike'),
    ('olivia', 'john'),
    ('bob', 'sarah'),
    ('alice', 'david'),
    ('tom', 'emily'),
    ('jane', 'olivia'),
    ('john', 'sarah'),
    ('mike', 'alice'),
    ('david', 'tom');

INSERT INTO items (owner_name,item_name, description, source_path)
VALUES
    ('john', 'Canon EOS R','Professional camera for photographers', '77ae0f3e-7412-43e0-86fc-10a1d31714131679588267719c82f8350e4549e0d66b5612e7690e6b4.webp'),
    ('jane', 'Pikachu Plushie','Cute and soft plushie', 'f4f81958-066a-464f-8395-c484596ee74f1679732986894cai-fang-EKLaAXax6d8-unsplash.jpg'),
    ('bob', 'Smartwatch', 'Latest smartwatch with heart rate monitor and GPS','69d302ba-d6ff-48eb-a765-2950941512de1679733039055greg-rosenke-ksRz3p93YS0-unsplash.jpg'),
    ('alice', 'Oil Painting','A beautiful and unique oil painting', '1fd27321-4c17-4bf9-84b3-cf1a305cfb591679733093246ivan-rohovchenko-r76sQCumIRI-unsplash.jpg'),
    ('tom', 'Vintage Vinyl Record','Rare vinyl record from the 70s', 'd0b8b406-46d5-499b-b537-665f01e1cf731679733659640zhou-xian-uaSSx-dJ428-unsplash.jpg'),
    ('john', 'Nature Photography Print','Print of a stunning nature photo', 'e8ddebb2-5426-43a3-9ce2-899fc76ae6f91679733743190snehal-krishna-XDjoIymUWiU-unsplash.jpg'),
    ('jane', 'Anime Figurine','Collectible figurine from popular anime', '5e782e86-563e-4bff-864e-28724b1270721679734081355shri-BA_i8d6uVqk-unsplash.jpg'),
    ('bob', 'Sculpture', 'Handcrafted sculpture made from wood','22ea5be9-eddc-4cd0-a943-7e89dd253deb1679735275507kevin-mueller-aeNg4YA41P8-unsplash.jpg'),
    ('alice', 'Vintage Book Set','Collection of classic novels from the 19th century', '71ebc05e-4fad-49ee-831c-d55d2305cda11679734084911knxrt-9s5_r0t6wVY-unsplash.jpg'),
    ('tom', 'NFT Crypto Artwork','Unique digital artwork on the blockchain', '31989c1e-cd50-48c6-9042-d42338fd0d911679734087931giphy.gif'),
    ('john', 'Crystal Chandelier','Luxury chandelier for elegant home decor', '6ba7319f-146d-4ec1-b4f3-e8e14c9462ba1679734574542a.gif'),
    ('jane', 'Disney Snow Globe','Beautiful snow globe featuring Disney characters', '2dd590c1-91d0-485c-8740-b2f8558bdb4d1679734690928b.gif'),
    ('bob', 'Antique Pocket Watch', 'Rare pocket watch from the 1800s','2aaa08f6-1541-4333-9970-85a27413a3681679734731937c.gif'),
    ('alice', 'Abstract Sculpture','Contemporary sculpture by a renowned artist', 'a0abdb9b-c070-4d04-9376-c07a212380a81679734757482d.gif'),
    ('tom', 'Handmade Pottery','Unique and functional pottery for everyday use', '973fee02-e814-4c91-8fcb-de3a2e992f571679734783004e.gif'),
    ('john', 'Movie Poster','Rare vintage movie poster from the 1950s', 'c1fac9f4-398e-41d3-a233-c9393522d2711679734976563ryoji-iwata-dlBXwGlzfcs-unsplash.jpg'),
    ('jane', 'Jewelry Set','Elegant jewelry set for special occasions', 'a89ac135-5d64-432e-a11b-81f9f54b10121679734960736matt-bennett-78hTqvjYMS4-unsplash.jpg'),
    ('bob', 'Vintage Camera', 'Antique camera from the early 1900s','3bef4603-044d-4607-a38a-b4f55bbfaca71679735331825wexor-tmg-L-2p8fapOA8-unsplash.jpg'),
    ('alice', 'Autographed Memorabilia','Signed sports memorabilia by famous athlete', '44b25e6c-d680-4e42-ba6a-92967034d26b1679735372941ricky-kharawala-adK3Vu70DEQ-unsplash.jpg'),
    ('tom', 'Modern Art Painting','Bold and colorful contemporary painting', 'fcc401c2-d31a-47b5-acf8-78bcae158dd51679735395361dominik-lange-BFsm5vldl2I-unsplash.jpg');


INSERT INTO items_categories (item_id, category_id)
VALUES
    (1, 1),
    (1, 7),
    (2, 6),
    (2, 8),
    (3, 8),
    (4, 4),
    (4, 7),
    (6, 1),
    (6, 7),
    (7, 2),
    (7, 7),
    (8, 7),
    (8, 8),
    (9, 8),
    (10, 7),
    (10, 5),
    (11, 2),
    (12, 8),
    (13, 1),
    (14, 3),
    (15, 6),
    (16, 5),
    (17, 4),
    (18, 1),
    (18, 6),
    (19, 7),
    (20, 2),
    (20, 5);

INSERT INTO listings (item_id, min_price, max_price, visits)
VALUES
    (1, 5.4, 9.4, 50),
    (2, 7, 7, 20),
    (3, 0.2, 1.0323, 10),
    (4, 5, 6.5, 5),
    (5, 2, 5, 100),
    (6, 1.5, 3.1240, 30),
    (7, 4.53, 7.231, 15),
    (8, 3.12, 2.212, 25),
    (9, 0.9, 1.50, 5),
    (10, 21.23, 30, 50),
    (11, 3.12, 6.23, 20),
    (12, 7.321, 9.99, 10),
    (13, 2.23, 3.313, 3),
    (14, 2.5, 5.0, 75),
    (15, 2.32, 7.50, 25),
    (16, 3, 6, 50),
    (17, 10, 20, 10),
    (18, 1.32, 2.654, 5),
    (19, 4, 8, 15),
    (20, 7.450, 9.4520, 30);

INSERT INTO messages (chat_id,senderName, message, is_deleted)
VALUES
    (1, 'jane', 'Hi John, is the iPhone still available?', false),
    (1, 'john', 'Yes, it is still available. Would you like to make an offer?', false),
    (1, 'jane', 'What is the lowest price you would accept?', false),
    (1, 'john', 'I can go as low as $450 for it.', false),
    (1, 'jane', 'OK, that works for me. Can we meet tomorrow to complete the transaction?', false),
    (2, 'bob', 'Hi Alice, I am interested in the dining table.', false),
    (2, 'alice', 'Sure, it is still available. What questions do you have?', false),
    (2, 'bob', 'Can you send me some photos of it?', false),
    (2, 'alice', 'Absolutely, I will send them over shortly.', false),
    (2, 'bob', 'Thanks. Also, what is the condition of the table?', false),
    (3, 'bob', 'Hi Tom, can you lower the price for the basketball?', false),
    (3, 'tom', 'Sorry, the price is already quite low. I cannot go any lower.', false),
    (4, 'john', 'Hi Jane, I loved The Great Gatsby, thank you for selling it!', false),
    (5, 'alice', 'Hi Bob, I am interested in the basketball, can you tell me more about it?', false),
    (5, 'bob', 'Sure, it is a Wilson brand basketball in great condition.', false),
    (5, 'alice', 'What is the asking price for it?', false),
    (5, 'bob', 'I am asking for $30.', false),
    (6, 'sarah', 'Hi David, is the couch still available?', false),
    (6, 'david', 'Yes, it is still available. Are you interested in seeing it?', false),
    (6, 'sarah', 'Yes, when can we meet?', false),
    (7, 'emily', 'Hi Mike, can you tell me more about the bike you are selling?', false),
    (7, 'mike', 'Sure, it is a Trek road bike in excellent condition. Would you like me to send you some photos?', false),
    (7, 'emily', 'Yes, please.', false),
    (7, 'mike', 'OK, I will send them over shortly.', false),
    (8, 'olivia', 'Hi John, is the treadmill still available?', false);

INSERT INTO transactions (listing_id, seller_name, buyer_name, transaction_price)
VALUES
    (1, 'john', 'jane', 900),
    (3, 'bob', 'alice', 1000),
    (5, 'tom', 'bob', 30),
    (2, 'alice', 'john', 150),
    (4, 'john', 'jane', 200);

INSERT INTO wish_list (item_id, username)
VALUES
    (2, 'alice'),
    (7, 'tom'),
    (3, 'john'),
    (10, 'sarah'),
    (1, 'jane'),
    (6, 'bob'),
    (11, 'david'),
    (5, 'tom'),
    (8, 'alice'),
    (9, 'emily'),
    (12, 'mike'),
    (15, 'olivia'),
    (4, 'bob'),
    (14, 'emily'),
    (16, 'john'),
    (13, 'david'),
    (17, 'jane'),
    (18, 'sarah'),
    (19, 'tom'),
    (20, 'olivia');

INSERT INTO bid (listing_id, buyer_name, price)
VALUES
    (6, 'john', 200.00),
    (6, 'bob', 180.00),
    (6, 'alice', 250.00),
    (7, 'jane', 300.00),
    (7, 'tom', 250.00),
    (8, 'sarah', 100.00),
    (8, 'david', 125.00),
    (8, 'emily', 150.00),
    (9, 'mike', 95.00),
    (10, 'olivia', 80.00),
    (10, 'john', 100.00),
    (11, 'jane', 450.00),
    (12, 'bob', 800.00),
    (12, 'alice', 700.00),
    (13, 'tom', 250.00),
    (13, 'sarah', 225.00),
    (14, 'david', 35.00),
    (15, 'emily', 550.00),
    (15, 'mike', 500.00),
    (16, 'olivia', 40.00),
    (17, 'john', 180.00),
    (18, 'jane', 200.00),
    (18, 'tom', 150.00),
    (19, 'sarah', 700.00),
    (20, 'david', 100.00),
    (20, 'emily', 120.00),
    (1, 'mike', 80.00),
    (2, 'olivia', 60.00),
    (3, 'john', 250.00),
    (3, 'jane', 300.00),
    (3, 'tom', 200.00),
    (4, 'sarah', 550.00),
    (4, 'david', 600.00),
    (5, 'emily', 400.00),
    (6, 'mike', 150.00),
    (7, 'olivia', 100.00),
    (7, 'john', 150.00),
    (8, 'jane', 175.00),
    (8, 'tom', 125.00),
    (9, 'sarah', 300.00),
    (10, 'david', 80.00),
    (10, 'emily', 100.00),
    (11, 'mike', 50.00),
    (12, 'olivia', 75.00),
    (13, 'john', 125.00),
    (13, 'jane', 150.00),
    (14, 'tom', 90.00),
    (15, 'sarah', 400.00),
    (16, 'david', 700.00),
    (16, 'emily', 600.00),
    (17, 'mike', 250.00),
    (18, 'olivia', 175.00),
    (19, 'john', 80.00),
    (4, 'jane', 100.00);
