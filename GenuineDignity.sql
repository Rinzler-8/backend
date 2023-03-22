
SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Genuine_Dignity`
--

DROP DATABASE IF EXISTS Genuine_Dignity;
CREATE DATABASE Genuine_Dignity;
USE Genuine_Dignity;

-- --------------------------------------------------------
DROP TABLE IF EXISTS `User`;
CREATE TABLE IF NOT EXISTS `User` (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE KEY,
    mobile VARCHAR(10) NOT NULL,
    address VARCHAR(400),
    `password` VARCHAR(120) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    update_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    url_avatar VARCHAR(400),
    `status` TINYINT DEFAULT 0,
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
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE IF NOT EXISTS `user_role` (
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
-- Table structure for table `Category`
--
DROP TABLE IF EXISTS Categories;
CREATE TABLE IF NOT EXISTS Categories (
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
    price VARCHAR(50) NOT NULL,
    product_info VARCHAR(200) NOT NULL,
    product_detail VARCHAR(500),
    rating_star TINYINT UNSIGNED,
    product_image_name VARCHAR(500) NOT NULL,
    category_id SMALLINT UNSIGNED NOT NULL,
    FOREIGN KEY (category_id)
        REFERENCES Categories (category_id)
);

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `Discount` (
    discount_id BIGINT NOT NULL PRIMARY KEY,
    discount_name VARCHAR(100) NOT NULL,
    discount_percent DECIMAL NOT NULL,
    active BOOLEAN NOT NULL,
    created_At DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_At DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- --------------------------------------------------------
--

-- Table structure for table `Cart`
--
DROP TABLE IF EXISTS `Cart`;
CREATE TABLE IF NOT EXISTS `Cart` (
    cart_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id INT DEFAULT NULL,
    product_id SMALLINT UNSIGNED DEFAULT NULL,
    quantity INT DEFAULT NULL,
    total_price DOUBLE DEFAULT NULL,
    created_At DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id)
        REFERENCES `Product` (product_id)
        ON DELETE CASCADE
);

-- --------------------------------------------------------
DROP TABLE IF EXISTS `Order`;
CREATE TABLE IF NOT EXISTS `Order` (
    order_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    session_id BIGINT NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    mobile VARCHAR(10) NOT NULL, 
    -- status ENUM('Đã Xác Nhận', 'Đang Chuẩn Bị Hàng', 'Đang Giao Hàng', 'Đã Giao Hàng'),
    status ENUM('CONFIRMED', 'PREPARING', 'DELIVERING', 'DELIVERED') NOT NULL,
    delivery_address VARCHAR(400),
    payment_type TINYINT DEFAULT 0, -- 0: COD, 1: Banking
    created_At DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_At DATETIME DEFAULT CURRENT_TIMESTAMP,
    Note VARCHAR(800),
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
  FOREIGN KEY (order_id) REFERENCES `Order` (order_id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES `Product` (product_id) ON DELETE CASCADE);


--
-- Table structure for table `Payment_Details`
--
  
CREATE TABLE IF NOT EXISTS `Payment_Details` (
  payment_id bigint NOT NULL PRIMARY KEY,
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
INSERT INTO `user` (`username`,`email`, `first_name`, `last_name`,`mobile`, `address`,`password`, `url_avatar`, `status`) VALUES 
('admin1', 'madboss1803@gmail.com', 'Phuc', 'Nguyen','0984328735', 'Hanoi', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', 'gentleCleanser.png', '1'),
('admin2', 'admin2@gmail.com', 'Viktor', 'Nguyen','0684621963', 'HCM','$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', 'gentleCleanser.png', '1'),
('user1', 'crazyboss1801@gmail.com', 'Quyen', 'Luu','084984161', 'Da Nang','$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', 'gentleCleanser.png', '0'),
('user2', 'user2@gmail.com', 'Thao', 'Ngo','084984161', 'Phu Quoc','$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', 'polish.png', '0'),
('user3', 'user3@gmail.com', 'TH', 'Truemilk','084984161', 'Bac Giang','$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', 'polish.png', '0');

-- --------------------------------------------------------
--
-- Dumping data for table `Role`
--
INSERT INTO `role` (`name`) VALUES 
('ADMIN'),
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
('5','2');
                           
-- Add data Categories
INSERT INTO Categories(category_name) 
VALUES
						('Sữa rửa mặt'), 
						('Tẩy tế bào chết'),
                        ('Toners'),
                        ('Retinols'),
                        ('Peels và mặt nạ'),
                        ('Chống nắng'),
                        ('Chăm sóc mắt'),
                        ('Dưỡng ẩm'),
						('Dưỡng thể');      
                        
-- Add data Product
INSERT INTO Product (name,    price,    product_info,    product_detail,    rating_star,    product_image_name,    category_id)			
VALUES 				('GENTLE CLEANSER', '1600000',	'Sữa rửa mặt dịu nhẹ cho mọi loại da',	
'ProductDetail1',    5,	   'gentleCleanser.png',    '1'),			
				    ('EXFOLIATING CLEANSER', '9000000',	'Sữa rửa mặt dịu nhẹ cho da thường đến da khô',	
                    'ProductDetail2',    4,	   'exfoliatingCleanser.png',    '1'),
                    ('HYDRATING CLEANSER', '10100000',	'Màn hình Super AMOLED tần số quét 90Hz, độ sáng cao 800 nit.',	
                    'ProductDetail3',    3,	   'hydratingCleanser.png',    '1'),
					('EYE BRIGHTENING CRÈME','11690000', 'Hiệu năng mượt mà, ổn định - Chip A13, RAM 4GB',	
                    'ProductDetail4',    4,	   'eyeBrightening.png',    '1'),
                    ('RENEWAL CRÈME', '29690000',	'Hiệu năng vượt trội - Chip Apple A15 Bionic mạnh mẽ, hỗ trợ mạng 5G',	
                    'ProductDetail5',    5,	   'renewalPads.png',    '8'),
                    ('RECOVERY CRÈME', '29690000',	'Hiệu năng vượt trội - Chip Apple A15 Bionic mạnh mẽ, hỗ trợ mạng 5G',	
                    'ProductDetail5',    5,	   'recoveryCreme.png',    '8'),
                    ('GROWTH FACTOR EYE SERUM', '19990000',	'Đỉnh cao công nghệ màn hình - Màn hình Liquid Retina, tần số quét 120Hz',	
                    'ProductDetail6',    4,	   'renewalPads.png',    '2'),
                    ('EXFOLIATING POLISH', '8990000',	'Thiết kế mỏng nhẹ, tinh tế - Thiết kế vuông vức, chỉ dày khoảng 7mm',	
                    'ProductDetail7',    5,	   'polish.png',    '2'),
                    ('HYDRATING CRÈME', '8990000',	'Thiết kế mỏng nhẹ, tinh tế - Thiết kế vuông vức, chỉ dày khoảng 7mm',	
                    'ProductDetail7',    5,	   'hydratingCreme.png',    '8'),
                    ('COMPLEXTION CLEARING MASQUE', '8990000',	'Thiết kế mỏng nhẹ, tinh tế - Thiết kế vuông vức, chỉ dày khoảng 7mm',	
                    'ProductDetail7',    5,	   'clearingMasque.png',    '5'),
                    ('ENZYMATIC PEEL', '8990000',	'Thiết kế mỏng nhẹ, tinh tế - Thiết kế vuông vức, chỉ dày khoảng 7mm',	
                    'ProductDetail7',    5,	   'enzymaticPeel.png',    '2'),
                    ('BODY EMULSION', '8990000',	'Thiết kế mỏng nhẹ, tinh tế - Thiết kế vuông vức, chỉ dày khoảng 7mm',	
                    'ProductDetail7',    5,	   'bodyEmulsion.png',    '9'),
                    ('CELLULITE CONTROL', '30300000',	'Xử lý đồ hoạ mượt mà - Chip M1',	
                    'ProductDetail8',    5,	   'cellulite.png',    '9'),
                    ('RADICAL NIGHT REPAIR', '30300000',	'Xử lý đồ hoạ mượt mà - Chip M1',	
                    'ProductDetail8',    5,	   'radicalNightRepair.png',    '4'),
                    ('WRINKLE + TEXTURE REPAIR', '30300000',	'Xử lý đồ hoạ mượt mà - Chip M1',	
                    'ProductDetail8',    5,	   'textureRepair.png',    '4'),
                    ('CALMING TONER', '1400000',	'Xử lý đồ hoạ mượt mà - Chip M1',	
                    'ProductDetail8',    5,	   'calmingToner.png',    '4'),
                    ('COMPLEXTION RENEWAL PADS', '1600000',	'Xử lý đồ hoạ mượt mà - Chip M1',	
                    'ProductDetail8',    5,	   'renewalPads.png',    '4'),
                    ('DAILY SHEER BROAD SPECTRUM SPF 50', '30300000',	'Xử lý đồ hoạ mượt mà - Chip M1',	
                    'ProductDetail8',    5,	   'dailySheer.png',    '4'),
                    ('SUNSCREEN + POWDER BROAD-SPECTRUM - LIGHT', '30300000',	'Xử lý đồ hoạ mượt mà - Chip M1',	
                    'ProductDetail8',    5,	   'powderBroad.png',    '4'),
                    ('SUNSCREEN + PRIMER SPF 30', '30300000',	'Xử lý đồ hoạ mượt mà - Chip M1',	
                    'ProductDetail8',    5,	   'primer.png',    '4'),
                    ('SMART TONE BROAD SPECTRUM SPF 50', '30300000',	'Xử lý đồ hoạ mượt mà - Chip M1',	
                    'ProductDetail8',    5,	   'smartTone.png',    '4'),
                    ('INTENSE EYE CRÈME', '17500000',	'Đa nhiệm tốt - Ram 8GB cho phép mở cùng lúc nhiều ứng dụng',	                'ProductDetail9',        4,	   'eyeCreme.png',    '3');		

-- Add data Cart
INSERT INTO `Cart` (`cart_id`, `user_id`, `product_id`, `quantity`, `total_price`,`created_At`)
VALUES
	(1,1,1,1,0,NULL),
	(2,1,2,1,0,NULL);