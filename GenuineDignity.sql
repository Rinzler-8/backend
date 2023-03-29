
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
--
-- Table structure for table `User`
--
DROP TABLE IF EXISTS `User`;
CREATE TABLE IF NOT EXISTS `User` (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
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
-- Table structure for table `Registration_User_Token`
--
DROP TABLE IF EXISTS 	`Registration_User_Token`;
CREATE TABLE IF NOT EXISTS `Registration_User_Token` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `token` CHAR(36) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
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
    user_id BIGINT NOT NULL,
    expiry_date DATETIME NOT NULL
);

-- --------------------------------------------------------
--
-- Table structure for table `Role`
--
CREATE TABLE IF NOT EXISTS `Role` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(150) NOT NULL
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

-- --------------------------------------------------------
--
-- Table structure for table `user_role`
--
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
DROP TABLE IF EXISTS Category;
CREATE TABLE IF NOT EXISTS Category (
    category_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
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
    product_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE KEY,
    price VARCHAR(50) NOT NULL,
    product_info VARCHAR(200) NOT NULL,
    product_detail VARCHAR(500),
    rating_star TINYINT UNSIGNED,
    product_image_name VARCHAR(500) NOT NULL,
    category_id BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (category_id)
        REFERENCES Category (category_id)
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
    user_id BIGINT NOT NULL,
    product_id BIGINT UNSIGNED NOT NULL,
    quantity INT DEFAULT NULL,
    total_price DOUBLE DEFAULT NULL,
    created_At DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id)
        REFERENCES `Product` (product_id)
        ON DELETE CASCADE
);

-- --------------------------------------------------------
--
-- Table structure for table `Order`
--
DROP TABLE IF EXISTS `Order`;
CREATE TABLE IF NOT EXISTS `Order` (
    order_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    session_id BIGINT NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    mobile VARCHAR(10) NOT NULL,
    -- status ENUM('UNCONFIRMED', 'CONFIRMED', 'PREPARING', 'DELIVERING', 'DELIVERED') NOT NULL,
    status TINYINT DEFAULT 0,
    delivery_address VARCHAR(400),
    payment_type TINYINT DEFAULT 0,
    created_At DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_At DATETIME DEFAULT CURRENT_TIMESTAMP,
    Note VARCHAR(800),
    FOREIGN KEY (user_id)
        REFERENCES User (id)
        ON DELETE CASCADE
);


-- ----------------------------------------------
-- Table structure for table `Order_Items`
--
CREATE TABLE IF NOT EXISTS `Order_Items` (
  item_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT NOT NULL,
  session_id BIGINT NOT NULL,
  product_id BIGINT UNSIGNED NOT NULL,
  quantity int NOT NULL,
  created_At DATETIME DEFAULT CURRENT_TIMESTAMP,
  modified_At  DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (order_id) REFERENCES `Order` (order_id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES `Product` (product_id) ON DELETE CASCADE);


-- --------------------------------------------------------
--
-- Table structure for table `Payment_Details`
--
CREATE TABLE IF NOT EXISTS `Payment_Details` (
  payment_id BIGINT NOT NULL PRIMARY KEY,
  user_id BIGINT NOT NULL,
  amount int NOT NULL,
  paymentStatus ENUM('Chờ Thanh Toán', 'Đã Thanh Toán')NOT NULL,
  paymentType ENUM("COD", "BANKING" , "MOMO", "VNPAY") NOT NULL,
  created_At DATETIME DEFAULT CURRENT_TIMESTAMP,
  modified_At  DATETIME DEFAULT CURRENT_TIMESTAMP,
  Note VARCHAR (535),
  FOREIGN KEY (user_id) REFERENCES `User` (id) ON DELETE CASCADE );

-- --------------------------------------------------------

--
-- Table structure for table `Order_Payment`
--

-- CREATE TABLE IF NOT EXISTS `Order_Payment` (
--   orderID BIGINT NOT NULL,
--   paymentID BIGINT NOT NULL,
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
('admin1', 'madboss1803@gmail.com', 'Phuc', 'Nguyen','0984328735', 'Hanoi', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', '141.png', '1'),
('admin2', 'admin2@gmail.com', 'Viktor', 'Nguyen','0684621963', 'HCM','$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', 'gentleCleanser.png', '1'),
('user1', 'crazyboss1801@gmail.com', 'Quyen', 'Luu','084984161', 'Da Nang','$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', '', '0'),
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
                           
-- Add data Category
INSERT INTO Category(category_name) 
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
				    ('EXFOLIATING CLEANSER', '9000000',	'Sữa rửa mặt dịu nhẹ cho da thường đến da dầu',	
                    'ProductDetail2',    4,	   'exfoliatingCleanser.png',    '1'),
                    ('HYDRATING CLEANSER', '10100000',	'Sữa rửa mặt dịu nhẹ cho da thường đến da khô',	
                    'ProductDetail3',    3,	   'hydratingCleanser.png',    '1'),
					('EYE BRIGHTENING CRÈME','11690000', 'Kem chống nhăn, giảm thâm, bọng mắt',	
                    'ProductDetail4',    4,	   'eyeBrightening.png',    '7'),
                    ('RENEWAL CRÈME', '29690000',	'Kem cấp ẩm và làm dịu dành cho mọi loại da',	
                    'ProductDetail5',    5,	   'renewalPads.png',    '8'),
                    ('RECOVERY CRÈME', '3500000',	'Kem cấp ẩm và làm dịu da dành cho da khô và da nhạy cảm',	
                    'ProductDetail5',    5,	   'recoveryCreme.png',    '8'),
                    ('GROWTH FACTOR EYE SERUM', '19990000',	'Serum điều trị nhăn, lão hóa vùng mắt',	
                    'ProductDetail6',    4,	   'renewalPads.png',    '7'),
                    ('EXFOLIATING POLISH', '8990000',	'Tẩy tế bào chết dành cho mọi loại da',	
                    'ProductDetail7',    5,	   'polish.png',    '2'),
                    ('HYDRATING CRÈME', '3400000',	'Kem cấp ẩm và làm dịu da sau khi điều trị hay da bị kích ứng',	
                    'ProductDetail7',    5,	   'hydratingCreme.png',    '8'),
                    ('COMPLEXION CLEARING MASQUE', '1500000',	'Mặt nạ đất sét giúp làm sạch da và điều trị mụn',	
                    'ProductDetail7',    5,	   'clearingMasque.png',    '5'),
                    ('ENZYMATIC PEEL', '2200000',	'Peel nhẹ tại nhà',	
                    'ProductDetail7',    5,	   'enzymaticPeel.png',    '5'),
                    ('BODY EMULSION', '3200000',	'Sữa dưỡng thể',	
                    'ProductDetail7',    5,	   'bodyEmulsion.png',    '9'),
                    ('CELLULITE CONTROL', '3200000',	'Sữa dưỡng thể',	
                    'ProductDetail8',    5,	   'cellulite.png',    '9'),
                    ('RADICAL NIGHT REPAIR', '5600000',	'Kem chống lão hóa cải thiện nếp nhăn sâu',	
                    'ProductDetail8',    5,	   'radicalNightRepair.png',    '4'),
                    ('WRINKLE + TEXTURE REPAIR', '4700000',	'Điều trị lão hóa da mức độ nhẹ - trung bình',	
                    'ProductDetail8',    5,	   'textureRepair.png',    '4'),
                    ('CALMING TONER', '1400000',	'Nước hoa hồng dành cho da khô, da nhạy cảm',	
                    'ProductDetail8',    5,	   'calmingToner.png',    '3'),
                    ('COMPLEXION RENEWAL PADS', '1600000',	'Cân bằng pH cho da thường đến da dầu',	
                    'ProductDetail8',    5,	   'renewalPads.png',    '3'),
                    ('DAILY SHEER BROAD SPECTRUM SPF 50', '2300000',	'Kem chống nắng hóa học phổ rộng',	
                    'ProductDetail8',    5,	   'dailySheer.png',    '6'),
                    ('SUNSCREEN + POWDER BROAD-SPECTRUM - LIGHT', '30300000',	'Phấn chống nắng cung cấp độ che phủ phù hợp với các sắc tố da, nâng tông da',	
                    'ProductDetail8',    5,	   'powderBroad.png',    '6'),
                    ('SUNSCREEN + PRIMER SPF 30', '2200000',	'Kem chống nắng vật lý phổ rộng',	
                    'ProductDetail8',    5,	   'primer.png',    '6'),
                    ('SMART TONE BROAD SPECTRUM SPF 50', '2400000',	'Kem chống nắng hóa học phổ rộng',	
                    'ProductDetail8',    5,	   'smartTone.png',    '6'),
                    ('INTENSE EYE CRÈME', '17500000',	'Kem dưỡng đặc trị vùng mắt bằng retinol giúp giảm nếp nhăn, đường nhăn',
	                'ProductDetail9',    4,	   'eyeCreme.png',    '7');		

-- Add data Cart
INSERT INTO `Cart` (`cart_id`, `user_id`, `product_id`, `quantity`, `total_price`,`created_At`)
VALUES
	(1,1,1,1,0,NULL),
	(2,2,2,1,0,NULL);