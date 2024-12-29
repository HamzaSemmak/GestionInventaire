DELETE FROM products;
DELETE FROM users;
DELETE FROM categories;

INSERT INTO categories (name, description) VALUES
('Electronics', 'Electronic devices and accessories'),
('Accessories', 'Product accessories'),
('Furniture', 'Furniture and office items'),
('Home Appliances', 'Appliances for home use'),
('Tools', 'Hand and power tools'),
('Personal Care', 'Personal grooming and care products');

INSERT INTO products (name, category_id, stock, price) VALUES
('Wireless Mouse',1 , 150, 25.99),
('Bluetooth Speaker',1 , 80, 49.99),
('Laptop Sleeve', 2, 200, 15.99),
('Smartphone Charger',1 , 300, 9.99),
('Gaming Headset',1 , 50, 79.99),
('LED Monitor',1 , 120, 199.99),
('Office Chair', 3, 30, 119.99),
('Desk Lamp', 3, 100, 29.99),
('Wireless Keyboard',1 , 180, 39.99),
('Smartwatch',1 , 70, 159.99),
('4K TV',1 , 60, 799.99),
('Bluetooth Earbuds',1 , 250, 69.99),
('Gaming Laptop',1 , 45, 1299.99),
('Electric Kettle', 4, 150, 39.99),
('Smart Thermostat', 4, 80, 99.99),
('Robot Vacuum', 4, 40, 249.99),
('Cordless Drill', 5, 100, 59.99),
('Coffee Maker', 4, 90, 89.99),
('LED Light Bulbs', 4, 500, 10.99),
('E-Book Reader',1 , 120, 129.99),
('Digital Camera',1 , 70, 299.99),
('Smart Door Lock', 4, 60, 179.99),
('Portable Power Bank',1 , 200, 29.99),
('Electric Shaver', 6, 120, 49.99),
('Hair Dryer', 6, 150, 39.99),
('Massage Gun', 6, 80, 99.99);


INSERT INTO users (username, email, password) VALUES
('hsemmak', 'hsemmak@gmail.com', '971608bd4cc289b03016660660d389fd');
