
SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `GenuineDignity`
--
DROP DATABASE IF EXISTS Genuine_Dignity;
DROP DATABASE IF EXISTS GenuineDignity;
CREATE DATABASE GenuineDignity;
USE GenuineDignity;

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
DROP TABLE IF EXISTS `Registration_User_Token`;
CREATE TABLE IF NOT EXISTS `Registration_User_Token` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `token` CHAR(36) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    expiry_date DATETIME NOT NULL
);                  
   
-- --------------------------------------------------------
--
-- Table structure for table `Refresh_Token`
--	
DROP TABLE IF EXISTS `Refresh_Token`;
CREATE TABLE IF NOT EXISTS `Refresh_Token` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `token` CHAR(36) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    expiry_date DATETIME NOT NULL
);        
   
-- --------------------------------------------------------
--
-- Table structure for table Reset_Password_Token
--
DROP TABLE IF EXISTS `Reset_Password_Token`;
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
    stock_qty BIGINT NOT NULL,
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
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
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
    Note VARCHAR(800)
);


-- ----------------------------------------------
-- Table structure for table `Order_Items`
--
CREATE TABLE IF NOT EXISTS `Order_Items` (
  item_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  id BIGINT NOT NULL,
  session_id BIGINT NOT NULL,
  product_id BIGINT UNSIGNED NOT NULL,
  quantity int NOT NULL,
  created_At DATETIME DEFAULT CURRENT_TIMESTAMP,
  modified_At  DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id) REFERENCES `Order` (id) ON DELETE CASCADE,
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
('admin1', 'madboss1803@gmail.com', 'Phuc', 'Nguyen','0969935388', 'Hanoi', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', '141.png', '1'),
('admin2', 'admin2@gmail.com', 'Viktor', 'Nguyen','0969935388', 'HCM','$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', 'gentleCleanser.png', '1'),
('user1', 'crazyboss1801@gmail.com', 'Viktor', 'Vu','0849841615', 'Da Nang','$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', '141.png', '1'),
('user2', 'madboss1801@gmail.com', 'Viktor', 'Ngo','0913345869', 'Phu Quoc','$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', '141.png', '1'),
('user3', 'user3@gmail.com', 'TH', 'Truemilk','084984161', 'Bac Giang','$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', 'polish.png', '0'),
('user4', 'user4@gmail.com', 'TH', 'Truemilk','084984161', 'Bac Giang','$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', 'polish.png', '0'),
('user5', 'user5@gmail.com', 'TH', 'Truemilk','084984161', 'Bac Giang','$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', 'polish.png', '0'),
('user6', 'user6@gmail.com', 'TH', 'Truemilk','084984161', 'Bac Giang','$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', 'polish.png', '0'),
('user7', 'user7@gmail.com', 'TH', 'Truemilk','084984161', 'Bac Giang','$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', 'polish.png', '0'),
('user8', 'user8@gmail.com', 'TH', 'Truemilk','084984161', 'Bac Giang','$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', 'polish.png', '0'),
('user9', 'user9@gmail.com', 'TH', 'Truemilk','084984161', 'Bac Giang','$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', 'polish.png', '0'),
('user10', 'use10@gmail.com', 'TH', 'Truemilk','084984161', 'Bac Giang','$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', 'polish.png', '0');
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
INSERT INTO Product (name,    price,    product_info,    product_detail,    rating_star,    product_image_name,    category_id,    stock_qty)			
VALUES 				('GENTLE CLEANSER', '1600000',	'Sữa rửa mặt dịu nhẹ cho mọi loại da',	
					'Sữa rửa mặt nhẹ nhàng cho mọi loại da được thiết kế với tác dụng loại bỏ đi các tạp chất giúp làn da có được cảm giác tươi mát, sạch sẽ và giàu độ ẩm.',    5,	   'gentleCleanser.png',    '1',  '0'),			
				    ('EXFOLIATING CLEANSER', '9000000',	'Sữa rửa mặt dịu nhẹ cho da thường đến da dầu',	
                    'Sữa rửa mặt tẩy tế bào chết dành cho da thường đến da dầu, loại bỏ bã nhờn trên bề mặt, mang lại cảm giác sạch cho da, nhưng vẫn không làm bong tróc và khô da.',    4,	   'exfoliatingCleanser.png',    '1',  '50'),
                    ('HYDRATING CLEANSER', '10100000',	'Sữa rửa mặt dịu nhẹ cho da thường đến da khô',	
                    'Sữa rửa mặt tác động kép dành cho da thường đến da khô, giúp làm sạch và cấp nước cho da, giảm thiểu tình trạng khô và kích ứng cho da nhạy cảm.',    3,	   'hydratingCleanser.png',    '1',  '50'),
					('EYE BRIGHTENING CRÈME','3900000', 'Kem chống nhăn, giảm thâm, bọng mắt',	
                    'Được thiết kế đặc biệt cho vùng da mắt mỏng manh. Giúp giảm thiểu các dấu hiệu lão hóa, bao gồm bọng mắt, quầng thâm và nếp nhăn',    4,	   'eyeBrightening.png',    '7',  '50'),
                    ('RENEWAL CRÈME', '3500000',	'Kem cấp ẩm và làm dịu dành cho mọi loại da',	
                    'Kem cấp ẩm cho da. Ngoài ra còn làm dịu da và giảm thiểu mẩn đỏ xuất hiện trên da.',    5,	   'renewalPads.png',    '8',  '50'),
                    ('RECOVERY CRÈME', '3500000',	'Kem cấp ẩm và làm dịu da dành cho da khô và da nhạy cảm',	
                    'Sản phẩm chứa retinol làm giảm sự xuất hiện của các nếp nhăn. Bên cạnh đó, hợp chất chứa các phân tử lipid dạng sáp hỗ trợ quá trình cấp nước giúp bề mặt giúp da mịn màng hơn',    5,	   'recoveryCreme.png',    '8',  '50'),
                    ('GROWTH FACTOR EYE SERUM', '3900000',	'Serum điều trị nhăn, lão hóa vùng mắt',	
                    'Được chứng minh lâm sàng bởi công nghệ yếu tố tăng trưởng ZO® Growth Factor Eye Serum được thiết kế giảm sự xuất hiện của các đường biểu cảm và nếp nhăn ở mắt, làm đầy hốc mắt, cấp ẩm, làm dịu và tái tạo sức sống cho đôi mắt mệt mỏi',    4,	   'renewalPads.png',    '7',  '50'),
                    ('EXFOLIATING POLISH', '8990000',	'Tẩy tế bào chết dành cho mọi loại da',	
                    'Các tinh thể magiê nhẹ nhàng tẩy tế bào da chết, giúp làn da trở nên sáng mịn và đều màu.
					Theo đánh giá Good Housekeeping Institute Beauty Lab, Exfoliating Polish là một trong 6 loại tẩy tế bào chết trên mặt tốt nhất để có làn da sáng mịn.',    5,	   'polish.png',    '2',  '50'),
                    ('HYDRATING CRÈME', '3400000',	'Kem cấp ẩm và làm dịu da sau khi điều trị hay da bị kích ứng',	
                    'Giúp làm giảm tạm thời các triệu chứng của da khô nghiêm trọng, làm dịu da và các kích ứng có thể nhìn thấy đồng thời bổ sung độ ẩm tự nhiên cho da trong quá trình hỗ trợ phục hồi được Hiệp hội Eczema Hoa Kỳ công nhận.',    5,	   'hydratingCreme.png',    '8',  '50'),
                    ('COMPLEXION CLEARING MASQUE', '1500000',	'Mặt nạ đất sét giúp làm sạch da và điều trị mụn',	
                    'Mặt nạ làm từ đất sét tự nhiên giúp làm sạch lỗ chân lông và hút dầu thừa giúp ngăn ngừa mụn, đồng thời dưỡng ẩm cho da để chống lại tình trạng khô da.',    5,	   'clearingMasque.png',    '5',  '50'),
                    ('ENZYMATIC PEEL', '2200000',	'Peel nhẹ tại nhà',	
                    'Peel nhẹ tại nhà để tạo ra một làn da mềm mại và sáng da. Tăng cường tái tạo bề mặt da, giảm thiểu mụn trứng cá, giúp đồng đều màu da và cấp ẩm',    5,	   'enzymaticPeel.png',    '5',  '50'),
                    ('BODY EMULSION', '3200000',	'Sữa dưỡng thể',	
                    'Dưỡng thể toàn thân đa tác động giúp cải thiện kết cấu da, độ mịn và vẻ ngoài tổng thể của làn da khỏe mạnh',    5,	   'bodyEmulsion.png',    '9',  '50'),
                    ('CELLULITE CONTROL', '3200000',	'Sữa dưỡng thể',	
                    'Sự hòa quyện độc đáo giữa các thành phần được kích hoạt suốt ngày đêm để giảm thiểu sự xuất hiện của các vết rạn ở đùi và các dấu hiệu có thể nhìn thấy của lớp mỡ thừa tích tụ trên da. Làm mịn rõ rệt hông, đùi và mông để có vẻ ngoài thon gọn hơn',    5,	   'cellulite.png',    '9',  '50'),
                    ('RADICAL NIGHT REPAIR', '5600000',	'Kem chống lão hóa cải thiện nếp nhăn sâu',	
                    'Công thức với 1% retinol để cải thiện kết cấu da và sắc tố không đồng đều, và giảm thiểu sự xuất hiện của nếp nhăn.',    5,	   'radicalNightRepair.png',    '4',  '50'),
                    ('WRINKLE + TEXTURE REPAIR', '4700000',	'Điều trị lão hóa da mức độ nhẹ - trung bình',	
                    'Với hệ thống phân phối vi nhũ tương, Wrinkle + Texture Repair là một loại retinol hiệu lực cao giúp kích hoạt lại quá trình đổi mới da để khôi phục độ ẩm tự nhiên, cải thiện rõ rệt sự xuất hiện của các nếp nhăn và cải thiện kết cấu da.',    5,	   'textureRepair.png',    '4',  '50'),
                    ('CALMING TONER', '1400000',	'Nước hoa hồng dành cho da khô, da nhạy cảm',	
                    'Toner cân bằng độ pH làm dịu, loại bỏ tạp chất và tiếp thêm sinh lực cho làn da yếu và nhạy cảm.',    5,	   'calmingToner.png',    '3',  '50'),
                    ('COMPLEXION RENEWAL PADS', '1600000',	'Cân bằng pH cho da thường đến da dầu',	
                    'Miếng đệm ẩm  giúp giảm thiểu dầu trên bề mặt da và tẩy tế bào chết làm tắc nghẽn lỗ chân lông và bụi bẩn',    5,	   'renewalPads.png',    '3',  '50'),
                    ('DAILY SHEER BROAD SPECTRUM SPF 50', '2300000',	'Kem chống nắng hóa học phổ rộng',	
                    'Chống nước và mồ hôi, kem chống nắng không nhờn rít với phức hợp ZOX12® thấm vào da nhanh chóng, mang lại lớp phủ mờ trong và giúp bảo vệ da của bạn khỏi tia UVA / UVB, IR-A, ánh sáng HEV đồng thời dưỡng ẩm cho da',    5,	   'dailySheer.png',    '6',  '50'),
                    ('SUNSCREEN + POWDER BROAD-SPECTRUM - LIGHT', '30300000',	'Phấn chống nắng cung cấp độ che phủ phù hợp với các sắc tố da, nâng tông da',	
                    'Sunscreen + Powder SPF 30 được cung cấp bởi Triple-Spectrum Protection® và công nghệ chống oxy hóa ZO® độc quyền, mang lại khả năng bảo vệ da vượt trội kết hợp với ánh nhũ phủ như phấn nền giúp làn da bạn trở nên mịn màng và lấp lánh. Gồm ba tone màu: sáng – sáng vừa – nâu sáng',    5,	   'powderBroad.png',    '6',  '50'),
                    ('SUNSCREEN + PRIMER SPF 30', '2200000',	'Kem chống nắng vật lý phổ rộng',	
                    'Kem chống nắng tác động kép với phức hợp ZOX12® bảo vệ chống lại các tác hại từ tia UVA / UVB, IR-A và từ ánh sáng HEV. Dưỡng ẩm và có thể được xem như một lớp lót trang điểm cho một lớp nền mịn màng, làm mờ các khuyết điểm trên da',    5,	   'primer.png',    '6',  '50'),
                    ('SMART TONE BROAD SPECTRUM SPF 50', '2400000',	'Kem chống nắng hóa học phổ rộng',	
                    'Được thiết kế để phù hợp với hầu hết các tông màu da, kem chống nắng siêu mịn này chứa phức hợp ZOX12® bảo vệ da chống lại các tác hại lão hóa từ tia UVA / UVB, IR-A và HEV',    5,	   'smartTone.png',    '6',  '50'),
                    ('INTENSE EYE CRÈME', '3900000',	'Kem dưỡng đặc trị vùng mắt bằng retinol giúp giảm nếp nhăn, đường nhăn',
	                'Điều trị mắt bằng retinol được nhắm mục tiêu để giảm nếp nhăn và đường nhăn rõ rệt, đồng thời cải thiện làn da và kết cấu da tổng thể. Bộ khuếch tán quang học giúp giảm quầng thâm và bọng mắt',    4,	   'eyeCreme.png',    '7',  '50');		

-- Add data Cart
-- INSERT INTO `Cart` (`cart_id`, `user_id`, `product_id`, `quantity`, `total_price`,`created_At`)
-- VALUES
-- 	(1,1,1,1,0,NULL),
-- 	(2,2,2,1,0,NULL);
    
-- Add data Order
INSERT INTO `Order` (`id`,`user_id`, `session_id`, `first_name`,`last_name`, `mobile`,`status`, `delivery_address`, `payment_type`, `created_At`, `Note`) VALUES 
('1', '3', '65487', 'Phuc', 'Hoang','0969935388', '1', 'Hanoi', '1' , '2023-05-16 00:00:00', ''),
('2', '3', '65485', 'Phuc','Hoang','0969935388', '0', 'HCM','0', '2023-05-16 00:00:00', ''),
('3', '3', '65488', 'Phuc','Hoang','0849841615', '5', 'Da Nang','1' , '2023-05-16 00:00:00', ''),
('4', '3', '65484', 'Phuc','Hoang','0913345869', '2', 'Phu Quoc','0','2023-05-16 00:00:00', ''),
('5', '3', '65483', 'Phuc','Hoang','084984161', '3', 'Bac Giang','1','2023-05-16 00:00:00', ''),
('6', '3', '65482', 'Phuc','Hoang','084984161', '4', 'Bac Giang','1','2023-05-16 00:00:00', ''),
('7', '3', '65481', 'Phuc','Hoang','084984161', '1', 'Bac Giang','0','2023-05-16 00:00:00', ''),
('8', '3', '65482', 'Phuc','Hoang','084984161', '4', 'Bac Giang','1','2023-05-16 00:00:00', ''),
('9', '3', '3523', 'Phuc','Hoang','084984161', '1', 'Bac Giang','0','2023-05-16 00:00:00', ''),
('10', '3', '43635', 'Phuc','Hoang','084984161', '4', 'Bac Giang','1','2023-05-16 00:00:00', ''),
('11', '3', '23612', 'Phuc','Hoang','084984161', '1', 'Bac Giang','0','2023-05-16 00:00:00', '')
    