
SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shop`
--

DROP DATABASE IF EXISTS shop;
CREATE DATABASE shop;
USE shop;

-- --------------------------------------------------------
DROP TABLE IF EXISTS `User`;
CREATE TABLE IF NOT EXISTS `User` (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE KEY,
    email VARCHAR(150) NOT NULL UNIQUE KEY,
    mobile VARCHAR(10) NOT NULL,
    `password` VARCHAR(120) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    update_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    avatar_path TEXT,
    url_avatar VARCHAR(100) UNIQUE KEY,
    `status` TINYINT DEFAULT 0, -- 0: Not Active, 1: Active
    block_exp_date DATETIME,
    token VARCHAR(255),
    token_creation_date DATETIME
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;


-- --------------------------------------------------------
--
-- Table structure for table Registration_User_Token
--
DROP TABLE IF EXISTS 	`Registration_User_Token`;
CREATE TABLE IF NOT EXISTS `Registration_User_Token` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `token` CHAR(36) NOT NULL UNIQUE,
    user_id SMALLINT NOT NULL,
    expiry_date DATETIME NOT NULL
);                  
   
-- --------------------------------------------------------
--
-- Table structure for table Reset_Password_Token
--
DROP TABLE IF EXISTS 	`Reset_Password_Token`;
CREATE TABLE IF NOT EXISTS `Reset_Password_Token` (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `token` CHAR(36) NOT NULL UNIQUE,
    user_id SMALLINT NOT NULL,
    expiry_date DATETIME NOT NULL
);

-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS `Role` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(150) NOT NULL
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

-- --------------------------------------------------------
DROP TABLE IF EXISTS User_Role;
CREATE TABLE IF NOT EXISTS User_Role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES `User` (id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (role_id)
        REFERENCES `Role` (id)
        ON UPDATE CASCADE ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

-- --------------------------------------------------------
--
-- Table structure for table `Manufacturer`
--
DROP TABLE IF EXISTS Manufacturer;
CREATE TABLE IF NOT EXISTS Manufacturer (
    manufacturer_id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    manufacturer_name ENUM('SAMSUNG', 'APPLE', 'XIAOMI', 'OPPO') NOT NULL UNIQUE KEY
);

-- --------------------------------------------------------
--
-- Table structure for table `Category`
--
DROP TABLE IF EXISTS Category;
CREATE TABLE IF NOT EXISTS Category (
    category_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(30) NOT NULL UNIQUE KEY,
    description VARCHAR(800) NOT NULL,
    created_At DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_At DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- --------------------------------------------------------
--
-- Table structure for table `Product`
--
DROP TABLE IF EXISTS Product;
CREATE TABLE IF NOT EXISTS Product (
    product_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE KEY,
    price varchar(50) NOT NULL,
    product_info VARCHAR(200) NOT NULL,
    product_detail VARCHAR(500),
    rating_star TINYINT UNSIGNED,
    product_image_name VARCHAR(500) NOT NULL,
    manufacturer_id SMALLINT UNSIGNED NOT NULL,
    category_id SMALLINT UNSIGNED NOT NULL,
    FOREIGN KEY (manufacturer_id)
        REFERENCES Manufacturer (manufacturer_id),
    FOREIGN KEY (category_id)
        REFERENCES Category (category_id)
);

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `Discount` (
    discountID BIGINT NOT NULL PRIMARY KEY,
    discountName VARCHAR(100) NOT NULL,
    discountPercent DECIMAL NOT NULL,
    active BOOLEAN NOT NULL,
    created_At DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_At DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- --------------------------------------------------------
--

-- Table structure for table `Cart`
--
DROP TABLE IF EXISTS `Cart`;
CREATE TABLE IF NOT EXISTS `Cart`(
cart_id bigint NOT NULL PRIMARY KEY auto_increment,
user_id int DEFAULT NULL,
product_id SMALLINT UNSIGNED DEFAULT NULL,
quantity int DEFAULT NULL,
total_price double DEFAULT NULL,
-- image VARCHAR(500) NOT NULL,
created_At DATETIME DEFAULT current_timestamp,
FOREIGN KEY (product_id) REFERENCES `Product` (product_id) ON DELETE CASCADE
-- FOREIGN KEY (user_id) REFERENCES `User` (id) ON DELETE CASCADE
);

-- --------------------------------------------------------
DROP TABLE IF EXISTS `Checkout`;
CREATE TABLE IF NOT EXISTS `Checkout` (
    order_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    session_id BIGINT NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    mobile VARCHAR(10) NOT NULL, 
    status ENUM('Chờ Xác Nhận', 'Đang Chuẩn Bị Hàng', 'Đang Giao Hàng', 'Đã Giao Hàng'),
    delivery_address VARCHAR(400),
    payment_type TINYINT DEFAULT 0, -- 0: COD, 1: Banking
    created_At DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_At DATETIME DEFAULT CURRENT_TIMESTAMP,
    Note VARCHAR(535),
    FOREIGN KEY (user_id) REFERENCES User (id) ON DELETE CASCADE
);

-- F:\Documents\ISD\Project\backend\shop.sql 
-- ----------------------------------------------
-- Table structure for table `Order_Items`
--

CREATE TABLE IF NOT EXISTS `Order_Items` (
  item_id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
  order_id bigint NOT NULL,
  session_id BIGINT NOT NULL,
  product_id SMALLINT UNSIGNED NOT NULL,
  quantity int not null,
  created_At DATETIME DEFAULT current_timestamp,
  modified_At  DATETIME DEFAULT current_timestamp,
  FOREIGN KEY (order_id) REFERENCES `Checkout` (order_id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES `Product` (product_id) ON DELETE CASCADE);


--
-- Table structure for table `Payment_Details`
--
  
CREATE TABLE IF NOT EXISTS `Payment_Details` (
  paymentID bigint NOT NULL PRIMARY KEY,
  user_id bigint NOT NULL,
  amount int not null,
  paymentStatus ENUM('Chờ Thanh Toán', 'Đã Thanh Toán')NOT NULL,
  paymentType ENUM("COD", "BANKING" , "MOMO", "VNPAY") not null,
  created_At DATETIME DEFAULT current_timestamp,
  modified_At  DATETIME DEFAULT current_timestamp,
  Note VARCHAR (535),
  FOREIGN KEY (user_id) REFERENCES `User` (id) ON DELETE CASCADE );

-- --------------------------------------------------------

--
-- Table structure for table `Order_Payment`
--

-- CREATE TABLE IF NOT EXISTS `Order_Payment` (
--   orderID bigint NOT NULL,
--   paymentID bigint NOT NULL,
--   FOREIGN KEY (orderID) REFERENCES `Order_Details` (orderID) ON DELETE CASCADE,
--   FOREIGN KEY (paymentID) REFERENCES `Payment_Details` (paymentID) ON DELETE CASCADE);

-- --------------------------------------------------------

/*============================== INSERT DATABASE =======================================*/
/*======================================================================================*/

-- --------------------------------------------------------
--
-- Dumping data for table `User`
--
INSERT INTO `user` (`username`,`email`,`mobile`,`password`, `avatar_path`, `status`) VALUES 
('admin1', 'admin1@gmail.com', '0984328735', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', ' ', '1'),
('admin2', 'admin2@gmail.com', '0684621963', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', ' ', '1'),
('manager1', 'manager1@gmail.com', '084984161', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', ' ', '0'),
('manager2', 'manager2@gmail.com', '084984161', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', ' ', '0'),
('manager3', 'manager3@gmail.com', '084984161', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', ' ', '0'),
('user1', 'user1@gmail.com', '084984161', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', ' ', '0'),
('user2', 'user2@gmail.com', '084984161', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', ' ', '0'),
('user3', 'user3@gmail.com', '084984161', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', ' ', '0'),
('user4', 'user4@gmail.com', '084984161', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', ' ', '0'),
('user5', 'user5@gmail.com', '084984161', '$2a$12$sMExyEbvpAu432Iaci3HKer1CXG8h8YECE7gIkUGw3MPJTR5fjGCK', ' ', '0'),
('user6', 'user6@gmail.com', '084984161', '$2a$12$sMExyEbvpAu432Iaci3HKer1CXG8h8YECE7gIkUGw3MPJTR5fjGCK', ' ', '0'),
('user7', 'user7@gmail.com', '084984161', '$2a$12$sMExyEbvpAu432Iaci3HKer1CXG8h8YECE7gIkUGw3MPJTR5fjGCK', ' ', '0'),
('user8', 'user8@gmail.com', '084984161', '$2a$12$h0wlfbltPOuFRggGTMqFGuHAA/T6o0JBl5chKo1BpwyMmpvxefYP2
', ' ', '1'),
('user9', 'user9@gmail.com', '0849841612', '$2a$12$gJir45gPwRAusyuQ5J/PHuzIGAd7LZy6iIMy3qoe1giH3A3lX0o1q
', ' ', '0'),
('user10', 'user10@gmail.com', '0849841612', '$2a$12$QWUSgWW4PAPxCoSFoD4cOekdp0TRqpmFxbzdf7iZZ7CkqWwbLY3eO', ' ', '1');

-- --------------------------------------------------------
--
-- Dumping data for table `Role`
--
INSERT INTO `role` (`name`) VALUES 
('ADMIN'),
('MANAGER'),
('USER');

-- --------------------------------------------------------
--
-- Dumping data for table `UserRole`
--
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES 
('1','1'),
('2','1'),
('3','2'),
('4','2'),
('5','2'),
('6','3'),
('7','3'),
('8','3'),
('9','3'),
('10','3'),
('11','3'),
('12','3'),
('13','3'),
('14','3'),
('15','3');

-- Add data Manufacturer
INSERT INTO Manufacturer	(manufacturer_name	) 
VALUES 					   ('SAMSUNG'	        ),
						   ('APPLE'		        ),
					       ('XIAOMI'        	),
						   ('OPPO'		        ); 
                           
-- Add data Category
INSERT INTO Category(category_name) 
VALUES
						('Điện thoại'),
						('Tablet'	),
						('Laptop'	);      
                        
-- Add data Product
INSERT INTO Product (name, 							price,		 	product_info,																	 product_detail, 	rating_star,  product_image_name, manufacturer_id, category_id)			
VALUES 				('Samsung Galaxy S22 Ultra 5G', '30990000',	'6.9 inches, Chip MediaTek Helio G85 (12nm) mạnh mẽ, Ram 4G, Pin 7000 mAh ',	'ProductDetail1',        5,	       'ImgMobile1.png',         '1',       '1'),			
				    ('Samsung Galaxy A52s 5G',      '9000000',	'Hiệu năng ưu việt, đa nhiệm- Chip xử lí Snapdragon 778G 5G và RAM 8GB',	    'ProductDetail2',        4,	       'ImgMobile2.png',         '1',       '1'),
                    ('Samsung Galaxy A72',         '10100000',	'Màn hình Super AMOLED tần số quét 90Hz, độ sáng cao 800 nit.',	                'ProductDetail3',        3,	       'ImgMobile3.png',         '1',       '1'),
					('iPhone 11 64GB I Chính hãng','11690000',	'Hiệu năng mượt mà, ổn định - Chip A13, RAM 4GB',	                            'ProductDetail4',        4,	       'ImgMobile4.png',         '2',       '1'),
                    ('iPhone 13 Pro Max 128GB',    '29690000',	'Hiệu năng vượt trội - Chip Apple A15 Bionic mạnh mẽ, hỗ trợ mạng 5G',	        'ProductDetail5',        5,	       'ImgMobile5.png',         '2',       '1'),
                    ('Apple iPad Pro 11 2021',     '19990000',	'Đỉnh cao công nghệ màn hình - Màn hình Liquid Retina, tần số quét 120Hz',	    'ProductDetail6',        4,	       'ImgMobile6.png',         '2',       '2'),
                    ('Xiaomi Pad 5',               '8990000',	'Thiết kế mỏng nhẹ, tinh tế - Thiết kế vuông vức, chỉ dày khoảng 7mm',	        	'ProductDetail7',        5,	       'ImgMobile7.png',         '3',       '2'),
                    ('Apple MacBook Pro 13',       '30300000',	'Xử lý đồ hoạ mượt mà - Chip M1',	                                            'ProductDetail8',        5,	       'ImgMobile8.png',         '2',       '3'),
                    ('Apple Mac mini M1 256GB',    '17500000',	'Đa nhiệm tốt - Ram 8GB cho phép mở cùng lúc nhiều ứng dụng',	                'ProductDetail9',        4,	       'ImgMobile1.png',         '2',       '3');		

-- Add data Cart
INSERT INTO `Cart` (`cart_id`, `user_id`, `product_id`, `quantity`, `total_price`,`created_At`)
VALUES
	(1,1,1,1,0,NULL),
	(2,1,2,1,0,NULL);