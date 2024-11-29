-- Chèn dữ liệu vào bảng users
INSERT INTO user (id,created_by, created_date, modified_date, modified_by, username, email, password, full_name, role, phone_number)
VALUES
    (1,'admin', NOW(), NULL, NULL, 'john_doe', 'john.doe@example.com', 'password123', 'John Doe', 'user', '123456789'),
    (2,'admin', NOW(), NULL, NULL, 'jane_doe', 'jane.doe@example.com', 'password456', 'Jane Doe', 'admin', '987654321'),
    (3,'admin', NOW(), NULL, NULL, 'michael_smith', 'michael.smith@example.com', 'password789', 'Michael Smith', 'user', '555666777');


INSERT INTO category (id, name)
VALUES
    (1, 'Pop'),
    (2, 'Rock'),
    (3, 'Jazz'),
    (4, 'Classical');

INSERT INTO music (id,title, composer_id, demo_url, full_url, price, is_approved, is_purchased, category_id, image_url)
VALUES
    (1,'Summer Breeze', 2, 'http://example.com/demo1.mp3', 'http://example.com/full1.mp3', 9.99, 1, 0, 1, 'http://example.com/image1.jpg'),
    (2,'Rock Anthem', 2, 'http://example.com/demo2.mp3', 'http://example.com/full2.mp3', 12.99, 1, 1, 2, 'http://example.com/image2.jpg'),
    (3,'Smooth Jazz', 2, 'http://example.com/demo3.mp3', 'http://example.com/full3.mp3', 7.99, 1, 0, 3, 'http://example.com/image3.jpg'),
    (4,'Classical Symphony', 2, 'http://example.com/demo4.mp3', 'http://example.com/full4.mp3', 14.99, 0, 0, 4, 'http://example.com/image4.jpg');

INSERT INTO purchase (user_id, music_id, purchase_date, amount)
VALUES
    (1, 2, NOW(), 12.99),
    (1, 3, NOW(), 7.99);
INSERT INTO wallets (user_id, balance, updated_at)
VALUES
    (1, 100.00, NOW()),  -- Ví của John Doe
    (2, 200.00, NOW()),  -- Ví của Jane Doe
    (3, 50.00, NOW());   -- Ví của Michael Smith


