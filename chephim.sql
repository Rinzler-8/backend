
SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `chephim`
--

DROP DATABASE IF EXISTS chephim;
CREATE DATABASE chephim;
USE chephim;

-- --------------------------------------------------------
--
-- Table structure for table `Film`
--
CREATE TABLE IF NOT EXISTS Film(
id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
title VARCHAR(150) NOT NULL,
vn_title VARCHAR(150) NOT NULL,
story_line TEXT NOT NULL,
released_at DATETIME NOT NULL,
duration INT NOT NULL,
poster_path TEXT NOT NULL,
`type` ENUM('movie', 'series') NOT NULL,
gross BIGINT,
seasons TINYINT,
episodes INT
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `Season`
--
CREATE TABLE IF NOT EXISTS Season(
id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
season_no INT NOT NULL,
episodes INT NOT NULL,
film_id BIGINT NOT NULL,
FOREIGN KEY (film_id) REFERENCES Film(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `Episode`
--
CREATE TABLE IF NOT EXISTS Episode(
id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
episode_no INT NOT NULL,
title VARCHAR(150) NOT NULL,
vn_title VARCHAR(150) NOT NULL,
story_line TEXT NOT NULL,
released_at DATETIME NOT NULL,
duration INT NOT NULL,
season_id BIGINT NOT NULL,
FOREIGN KEY (season_id) REFERENCES Season(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;


-- --------------------------------------------------------
--
-- Table structure for table `Photo`
--
CREATE TABLE IF NOT EXISTS Photo(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
path_name TEXT NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `FilmPhoto`
--
CREATE TABLE IF NOT EXISTS FilmPhoto(
film_id BIGINT NOT NULL,
photo_id BIGINT,
FOREIGN KEY (film_id) REFERENCES Film(id) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (photo_id) REFERENCES Photo(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `Video`
--
CREATE TABLE IF NOT EXISTS Video(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
path_name TEXT NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `FilmPhoto`
--
CREATE TABLE IF NOT EXISTS FilmVideo(
film_id BIGINT NOT NULL,
video_id BIGINT, 
FOREIGN KEY (film_id) REFERENCES Film(id) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (video_id) REFERENCES Video(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `ProductionCompany`
--
CREATE TABLE IF NOT EXISTS ProductionCompany(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(150) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `FilmProductionCompany`
--
CREATE TABLE IF NOT EXISTS FilmProductionCompany(
film_id BIGINT NOT NULL,
production_company_id BIGINT,
FOREIGN KEY (film_id) REFERENCES Film(id) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (production_company_id) REFERENCES ProductionCompany(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `Country`
--
CREATE TABLE IF NOT EXISTS Country(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(150) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `FilmCountry`
--
CREATE TABLE IF NOT EXISTS FilmCountry(
film_id BIGINT NOT NULL,
country_id BIGINT NOT NULL,
FOREIGN KEY (film_id) REFERENCES Film(id) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (country_id) REFERENCES Country(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `Language`
--
CREATE TABLE IF NOT EXISTS `Language`(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(150) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `FilmLanguage`
--
CREATE TABLE IF NOT EXISTS FilmLanguage(
film_id BIGINT NOT NULL,
language_id BIGINT NOT NULL,
FOREIGN KEY (film_id) REFERENCES Film(id) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (language_id) REFERENCES `Language`(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `Category`
--
CREATE TABLE IF NOT EXISTS Category(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(150) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `FilmCategory`
--
CREATE TABLE IF NOT EXISTS FilmCategory(
film_id BIGINT NOT NULL,
category_id BIGINT NOT NULL,
FOREIGN KEY (film_id) REFERENCES Film(id) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (category_id) REFERENCES Category(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;


-- --------------------------------------------------------
--
-- Table structure for table `People`
--
CREATE TABLE IF NOT EXISTS People(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
profile_picture TEXT,
`name` VARCHAR(150) NOT NULL,
short_introduction TEXT NOT NULL,
height SMALLINT,
spouse VARCHAR(150),
parents VARCHAR(255),
dob DATE NOT NULL,
birthplace VARCHAR(255) NOT NULL,
fullname VARCHAR(255) NOT NULL,
`role` VARCHAR(255) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `PeopleFilm`
--
CREATE TABLE IF NOT EXISTS PeopleFilm(
film_id BIGINT NOT NULL,
people_id BIGINT NOT NULL,
FOREIGN KEY (film_id) REFERENCES Film(id) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (people_id) REFERENCES People(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `FilmCharacter`
--
CREATE TABLE IF NOT EXISTS FilmCharacter(
film_id BIGINT NOT NULL,
people_id BIGINT NOT NULL,
`name` VARCHAR(150) NOT NULL,
FOREIGN KEY (film_id) REFERENCES Film(id) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (people_id) REFERENCES `People`(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `User`
--
CREATE TABLE IF NOT EXISTS `User`(
id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(50) NOT NULL UNIQUE KEY,
email VARCHAR(150) NOT NULL UNIQUE KEY,
`password` VARCHAR(120) NOT NULL,
created_at DATETIME NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
update_at DATETIME NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
avatar_path TEXT,
url_avatar VARCHAR(100) UNIQUE KEY,
`status` TINYINT DEFAULT 0, -- 0: Not Active, 1: Active
-- `status` ENUM('ACTIVE', 'INACTIVE'),
block_exp_date DATETIME,
token VARCHAR(255),
token_creation_date DATETIME
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;


-- --------------------------------------------------------
--
-- Table structure for table Registration_User_Token
--
DROP TABLE IF EXISTS 	`Registration_User_Token`;
CREATE TABLE IF NOT EXISTS `Registration_User_Token` ( 	
	id 				INT AUTO_INCREMENT PRIMARY KEY,
	`token`	 		CHAR(36) NOT NULL UNIQUE,
	user_id			SMALLINT NOT NULL,
	expiry_date 	DATETIME NOT NULL
    -- FOREIGN KEY (accountid)  REFERENCES Account(id)
);                  
   
-- --------------------------------------------------------
--
-- Table structure for table Reset_Password_Token
--
DROP TABLE IF EXISTS 	`Reset_Password_Token`;
CREATE TABLE IF NOT EXISTS `Reset_Password_Token` ( 	
	id 				INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	`token`	 		CHAR(36) NOT NULL UNIQUE,
	user_id			SMALLINT  NOT NULL,
	expiry_date 	DATETIME NOT NULL
);  



-- --------------------------------------------------------
--
-- Table structure for table `Watchlist`
--
CREATE TABLE IF NOT EXISTS Watchlist(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(150) NOT NULL,
`description` TEXT,
created_at DATETIME NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
user_id BIGINT NOT NULL,
FOREIGN KEY (user_id) REFERENCES `User`(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `FilmWatchlist`
--
CREATE TABLE IF NOT EXISTS FilmWatchlist(
film_id BIGINT NOT NULL,
watchlist_id BIGINT NOT NULL,
added_at DATETIME NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
FOREIGN KEY (film_id) REFERENCES Film(id) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (watchlist_id) REFERENCES Watchlist(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;


-- --------------------------------------------------------
--
-- Table structure for table `Role`
--
CREATE TABLE IF NOT EXISTS `Role`(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(150) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `UserRole`
--
CREATE TABLE IF NOT EXISTS User_Role(
user_id BIGINT NOT NULL,
role_id BIGINT NOT NULL,
FOREIGN KEY (user_id) REFERENCES `User`(id) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (role_id) REFERENCES `Role`(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `RatingReviews`
--
CREATE TABLE IF NOT EXISTS RatingReviews(
film_id BIGINT NOT NULL,
user_id BIGINT NOT NULL,
rating_star TINYINT,
review LONGTEXT,
FOREIGN KEY (film_id) REFERENCES Film(id) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (user_id) REFERENCES User(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `RatingReviews`
--
CREATE TABLE IF NOT EXISTS News(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
title VARCHAR(255) NOT NULL,
thumbnail TEXT NOT NULL,
content LONGTEXT NOT NULL,
created_at DATETIME NOT NULL,
user_id BIGINT NOT NULL,
FOREIGN KEY (user_id) REFERENCES User(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Dumping data for table `Film`
--
INSERT INTO `film` (`title`, `vn_title`, `story_line`, `released_at`, `duration`, `poster_path`, `type`, `gross`, `seasons`, `episodes`) VALUES 
('Shawshank Redemption', 'Nhà Tù Shawshank', 'Andrew, một nhân viên nhà băng, bị kết án chung thân sau khi giết vợ và nhân tình của cô. 
Anh một mực cho rằng mình bị oan. Andy bị đưa tới nhà tù Shawshank. Tại đây, thế giới ngầm của các phạm nhân, sự hà khắc của hệ thống quản giáo 
xung đột và giành nhau quyền thống trị. Chỉ có các phạm nhân trung lập là bị kẹt ở giữa và có thể bỏ mạng. Làm quen với tay quản lý chợ đen 
- Red, Andy dần thích nghi với cuộc sống tại Shawshank. Song, kế hoạch lớn hơn việc tồn tại ở nhà tù này đang được anh suy tính.', 
'1994-09-10', '142','', 'movie', '28884504', NULL, NULL),
('The Queen Gambit', 'Gambit Hậu', 'Ở một cô nhi viện những năm 1950, một cô gái trẻ bộc lộ tài năng cờ vua đáng kinh ngạc và bắt đầu hành 
trình đến đỉnh vinh quang trong khi vật lộn với thói nghiện ngập.', '2020-10-23', '60', '', 'series', NULL, '1', '7'),
('Blade Runner 2049', 'Tội Phạm Nhân Bản 2049', 'Trong một nhiệm vụ, viên cảnh sát K - vốn là một người máy đời mới - phát hiện thi thể của 
một rô-bốt nữ đã qua đời khi mổ sinh con nhiều năm trước. Để ngăn chặn một cuộc chiến tranh giữa các rô-bốt nhân bản với con người, K được 
bí mật giao nhiệm vụ tìm đứa trẻ và phá hủy tất cả các bằng chứng liên quan đến nó.', '2017-10-05', '164', '', 'movie', '259352064', NULL, NULL),
('Django Unchained', 'Hành Trình Django', 'Với sự giúp đỡ của một thợ săn tiền thưởng người Đức, một nô lệ được tự do lên đường giải cứu 
vợ mình khỏi một chủ đồn điền tàn bạo ở Mississippi.', '2012-12-11', '165', '', 'movie', '426074373', NULL, NULL),
('Good Omens', 'Thiện Báo', 'bộ phim kể về ác quỷ Crowley (David Tennant) và thiên thần Aziraphale (Michael Sheen), những người bạn lâu năm, đã 
quen với cuộc sống trên Trái đất, tìm cách ngăn chặn Antichrist đến và cùng với nó là Armageddon, trận chiến cuối cùng giữa Thiên đường và Địa 
ngục', '2019-05-31', '55', '', 'series', NULL, '1', '6'),
('Inception', 'Kẻ Đánh Cắp Giấc Mơ', 'Một tên trộm đánh cắp bí mật của công ty thông qua việc sử dụng công nghệ chia sẻ giấc mơ được giao 
nhiệm vụ ngược lại là gieo một ý tưởng vào tâm trí của một C.E.O.', '2010-07-08', '148', '', 'movie', '836848102', NULL, NULL),
('The Sandman', 'Người Cát', 'Sau nhiều năm bị giam cầm, Morpheus – Chúa tể Cõi Mộng – bắt đầu cuộc hành trình xuyên qua các thế giới để tìm 
lại những thứ đã bị cướp đoạt và khôi phục sức mạnh.', '2022-08-05', '45', '', 'series', NULL, '1', '11'),
('The Dark Knight', 'Kỵ Sĩ Bóng Đêm', 'The Dark Knight là phần tiếp theo của Batman Begins kể về thành phố Gotham bị đảo lộn do hàng loạt vụ 
giết người xảy ra mà không tìm ra hung thủ.', '2008-07-18', '152', '', 'movie', '1006102277', NULL, NULL),
('The Godfather', 'Bố Già', 'Bố già (The Godfather) là một bộ phim hình sự sản xuất năm 1972 dựa theo tiểu thuyết cùng tên của nhà văn Mario 
Puzo và do Francis Ford Coppola đạo diễn. Phim xoay quanh diễn biến của gia đình mafia gốc Ý Corleone trong khoảng 10 năm từ 1945 đến 1955. ', 
'1972-03-24', '175', '', 'movie', '250341816', NULL, NULL),
('The Haunting of Bly Manor', 'Chuyện Ma Ám Ở Trang Viên Bly', 'Chết không có nghĩa là biến mất. Một cô gia sư bị cuốn vào những bí mật rùng 
rợn trong phim lãng mạn kiểu gothic này, từ tác giả của "Chuyện ma ám ở căn nhà họ Hill".', '2020-10-09', '50', '', 'series', NULL, '1', '9');

-- --------------------------------------------------------
--
-- Dumping data for table `Season`
--
INSERT INTO `Season` (`season_no`, `episodes`, `film_id`) VALUES 
('1', '7', '2'),
('1', '6', '5'),
('1', '11', '7'),
('1', '9', '10');

-- --------------------------------------------------------
--
-- Dumping data for table `Episode`
--
INSERT INTO `episode` (`episode_no`, `title`, `vn_title`, `story_line`, `released_at`, `duration`, `season_id`) VALUES
('1', 'Openings', 'Khai cuộc', 'Được đưa vào cô nhi viện năm 9 tuổi, Beth phát triển năng khiếu kỳ lạ về cờ vua – và ngày càng lệ thuộc vào thứ thuốc an thần màu xanh lá cây được phát cho bọn trẻ.', '2020-10-23', '59', '1'),
('2', 'Exchanges', 'Đổi quân', 'Bất ngờ bị cuốn vào cuộc sống mới đầy khó xử ở vùng ngoại ô, cô gái tuổi teen Beth tìm hiểu các bạn cùng lớp ở trường trung học và ấp ủ kế hoạch tham gia giải đấu cờ vua.', '2020-10-23', '65', '1'),
('3', 'Double Pawns', 'Chồng đôi tốt', 'Chuyến đi đến Cincinnati đưa Beth và mẹ cô vào vòng xoáy của việc di chuyển và báo chí đưa tin. Beth đặt mục tiêu lọt vào giải Mỹ Mở rộng ở Las Vegas.', '2020-10-23', '46', '1'),
('4', 'Middle Game', 'Trung cuộc', 'Lớp tiếng Nga mở ra cánh cửa đến một bối cảnh xã hội mới. Tại thành phố Mexico, Beth gặp Borgov đáng sợ, còn mẹ cô thân thiết với một người bạn qua thư.', '2020-10-23', '49', '1'),
('5', 'Fork', 'Đòn chĩa đôi', 'Trở về nhà ở Kentucky, Beth vẫn đang choáng váng. Cô kết nối lại với một đối thủ cũ – người đề nghị giúp cô mài giũa kỹ năng cho Giải Vô địch Mỹ.', '2020-10-23', '48', '1'),
('6', 'Adjournment', 'Thời gian tạm hoãn', 'Sau khi tập luyện cùng Benny ở New York, Beth đến Paris để tái đấu với Borgov. Nhưng một đêm hoang dại đã khiến cô rơi vào vòng xoáy tự hủy hoại.', '2020-10-23', '60', '1'),
('7', 'End game', 'Tàn cuộc', 'Một người bạn cũ ghé thăm buộc Beth phải đối mặt với quá khứ và cân nhắc lại các ưu tiên của mình – vừa đúng lúc cho trận đấu lớn nhất trong cuộc đời cô.', '2020-10-23', '68', '1'),
('1', 'In The Beginning', 'Khởi nguồn', 'Bắt đầu vào 11 năm trước, hai thực thể bất tử quyết định rằng có lẽ đây không phải là lúc để bắt đầu tận thế.', '2019-05-31', '51', '2'),
('2', 'The Book', 'Cuốn sách', 'Lãng phí nhiều năm theo đuôi sai đứa trẻ, Aziraphale và Crowley phải bắt đầu lại cuộc hành trình tìm kiếm kẻ phản Chúa thực sự. Có lẽ câu chuyện của Agnes Nutter cùng lời tiên tri nổi tiếng sẽ chứa đáp án?', '2019-05-31', '56', '2'),
('3', 'Hard Times', 'Thời điểm khó khăn', 'Cùng chứng kiến tình bạn giữa Aziraphale và Crowley qua nhiều thế kỷ. Trong khi ở thời điểm hiện tại, hậu duệ của Agnes Nutter đặt chân đến Tadfiled cùng nhiệm vụ để cứu thế giới', '2019-05-31', '57', '2'),
('4', 'Saturday Morning Funtime', 'Cuộc vui sáng thứ bảy', 'Tình bạn của Aziraphale và Crowley bị thử thách bởi những đấng tối cao. Tận thế thực sự bắt đầu, với sức mạnh của kẻ phản Chúa đang tàn phá khắp nơi trên Trái Đất', '2019-05-31', '56', '2'),
('5', 'The Doomsday Option', 'Lựa chọn ngày tàn', 'Aziraphale và Crowley tìm đến căn cứ không quân Tadfield khi họ cố gắng ngăn chặn Adam và Tứ kỵ sĩ bắt đầu ngày tận thế. Nhưng một người thì kẹt trong bể nước thánh trong khi người còn lại bị thiêu bởi lửa địa ngục, liệu họ có đến đó kịp thời?', '2019-05-31', '52', '2'),
('6', 'The Very Last Day of the Rest of Their Lives', 'Những ngày cuối đời họ', 'Liệu Adam, Crowley và Aziraphale có thể hợp tác với nhau để chống lại quyền lực của Thiên đường và Địa ngục cũng như ngăn chặn ngày tận thế? Và số phận nào đang chờ họ nếu họ làm vậy? Câu chuyện đi đến hồi kết, cũng có thể là hồi kết của thế giới.', '2019-05-31', '54', '2'),
('1', 'Sleep of the Just', 'Ngủ sâu','Trong khi tìm kiếm một cơn ác mộng xổng ra thế giới tỉnh thức, Morpheus rơi vào tay Roderick Burgess, một nhà huyền bí đang tìm cách triệu hồi và giam cầm Chết Chóc.', '2022-08-05', '54', '3'),
('2', 'Imperfect Hosts', 'Chủ nhà không hoàn hảo', 'Morpheus bắt đầu hành trình tìm lại các công cụ quyền lực của mình – cát, đá hồng ngọc và nón sắt – bằng cách đến gặp hai anh em lập dị khét tiếng.','2022-08-05','38','3'),
('3', 'Dream a Little Dream of Me', 'Mơ về tôi một chút', 'Morpheus lần theo dấu vết của người cuối cùng được biết đang sở hữu cát của mình và nhận được bài học không ngờ tới về nhân loại. Bà Ethel đến thăm con trai mình.','2022-08-05', '46', '3'),
('4', 'A Hope in Hell', 'Tia hy vọng ở địa ngục', 'Một manh mối về vị trí nón sắt của mình buộc Morpheus phải đến gặp Lucifer. Trong hoang mang, ông John nhận được sự giúp đỡ từ một người Samari tốt bụng.', '2022-08-05','45','3'),
('5', '24/7', '24/7', 'Khi Morpheus mất cảnh giác, ông John vào một quán ăn để quan sát những người xung quanh rồi đưa lý thuyết của mình về sự thật và dối trá vào một bài kiểm tra đáng sợ.', '2022-08-05', '54', '3'),
('6', 'The Sound of Her Wings', 'Âm thanh của cánh', 'Cảm thấy mất phương hướng, Morpheus đi theo người chị gái chăm chỉ của mình. Cô đưa lời khuyên và khuyến khích anh kết nối lại với một người quen cũ.','2022-08-05', '53', '3'),
('7', 'The Doll`s House', 'Nhà búp bê', 'Lucienne đến gặp Morpheus và thông báo tin tức đáng ngại. Rose Walker đi tìm gia đình. Những người ngưỡng mộ tác phẩm của Corinthian âm mưu thu hút sự chú ý của anh.', '2022-08-05', '49', '3'),
('8', 'Playing House', 'Nhà vui chơi', 'Khi Morpheus sắp tiếp cận một trong những tạo tác bị mất của mình, Rose dốc sức tìm kiếm em trai và vô tình biến giấc mơ của một người bạn thành hiện thực.', '2022-08-05','50','3'),
('9', 'Collectors', 'Kẻ sưu tầm', 'Những xáo trộn kỳ lạ làm rung chuyển Cõi Mộng, Rose bắt đầu chuyến phượt với một người bạn mới, còn Corinthian xuất hiện cùng một vị khách ở một hội nghị rùng rợn.', '2022-08-05', '49', '3'),
('10', 'Lost Hearts', 'Những trái tim đã mất', 'Khi cơn lốc Giấc Mơ ngày càng mạnh mẽ và bức tường giữa các cõi suy yếu, Rose phải đưa ra lựa chọn khó khăn. Morpheus đối đầu với Corinthian.', '2022-08-05', '46', '3'),
('11', 'Dream of a Thounsand Cats/ Calliope', 'Mơ thấy một ngàn con mèo/ Calliope', 'Trong tuyển tập gồm hai phần truyện này, một chú mèo Xiêm mơ về thế giới mới và một nhà văn khao khát cảm hứng chạm trán Morpheus.', '2022-08-19', '64', '3'),
('1', 'The Great Good Place', 'Nơi tốt đẹp nhất', 'Nhận chăm nom hai đứa bé mồ côi tại trang viên tráng lệ ở nước Anh, cô gia sư người Mỹ đầy nhiệt huyết hi vọng tạo nên sự khác biệt. Nhưng nỗi sợ hãi luôn bủa vây cô.', '2020-10-09', '54', '4'),
('2', 'The Pupil', 'Học trò', 'Sau một phen bị hù dọa kinh hãi, Dani tìm cách dạy cho lũ trẻ một bài học. Dù vậy, chúng vẫn có cách khiến người khác phải rùng mình.', '2020-10-09', '45', '4'),
('3', 'The Two Faces, Part One', 'Hai khuôn mặt (Phần 1)', 'Quá khứ thoáng hiện về với Dani và Miles một cách bí ẩn. Peter Quint, Rebecca Jessel và lịch sử éo le giữa họ còn phủ bóng đen mãi về sau.', '2020-10-09', '56', '4'),
('4', 'The Way It Came', 'Bạn bè của bạn bè', 'Dani dằn vặt trong tội lỗi và những mất mát đau đớn ám ảnh cô. Flora và Miles tìm cách đương đầu còn những người làm ở Bly tưởng nhớ người đã khuất bên đống lửa.', '2020-10-09', '53', '4'),
('5', 'The Altar of the Dead', 'Bàn thờ người chết', 'Cô đã chứng kiến tất cả. Ký ức ùa về với nữ quản gia Hannah Grose khi cô hồi tưởng về những người còn sống và hồn ma bị mắc kẹt tại trang viên Bly.', '2020-10-09', '54', '4'),
('6', 'The Jolly Corner', 'Góc vui vẻ', 'Quyết phủ nhận và vùi đầu vào công việc, Henry Wingrave phải dành thời gian nhìn nhận lại mình. Dani tìm cách kết thân với Jamie trong khi Flora đắm chìm trong quá khứ.', '2020-10-09', '66', '4'),
('7', 'The Two Faces, Part Two', 'Hai khuôn mặt (Phần 2)', 'Miles và Flora bị lôi kéo vào một trò chơi ma quái. Đối diện với sự thật, Rebecca đi đến kết cục bất hạnh, còn Hannah có một khám phá gây choáng váng.', '2020-10-09', '59', '4'),
('8', 'The Romance of Certain Clothes', 'Truyền thuyết về vài bộ quần áo cũ', 'Khởi nguồn đen tối của Bly dần sáng tỏ. Từng là quý cô sắt đá của trang viên, Viola bị cơn thịnh nộ khủng khiếp nuốt chửng, giăng bẫy mọi sinh linh xung quanh.', '2020-10-09', '56', '4'),
('9', 'The Beast in the Jungle', 'Con thú trong rừng', 'Cơn mơ đã kết thúc nhưng mối nguy vẫn còn đó. Hannah lấy hết can đảm hành động. Số phận tồi tệ hơn cả cái chết đang rình rập những người ở Bly. Ai sẽ phải trả giá?', '2020-10-09', '51', '4'); 
-- --------------------------------------------------------
--
-- Dumping data for table `ProductionCompany`
--
INSERT INTO `productioncompany` (`name`) VALUES 
('Warner Bros'),
('Sony Pictures'),
('Universal Pictures'),
('Paramount Pictures'),
('Lionsgate Films'),
('The Weinstein Company'),
('Netflix'),
('Columbia Pictures'),
('Walt Disney Studios'),
('Amazon Studios');

-- --------------------------------------------------------
--
-- Dumping data for table `FilmProductionCompany`
--
INSERT INTO `filmproductioncompany` (`film_id`, `production_company_id`) VALUES 
('1','1'),
('2','7'),
('3','8'),
('4','6'),
('5','10'),
('6','1'),
('7','7'),
('8','1'),
('9','4'),
('10','4');

-- --------------------------------------------------------
--
-- Dumping data for table `Country`
--
INSERT INTO `country` (`name`) VALUES 
('Mỹ'),
('Anh'),
('Canada'),
('Tây Ban Nha'),
('Trung Quốc'),
('Nhật Bản'),
('Hàn Quốc'),
('Pháp'),
('Đức'),
('Nga');

-- --------------------------------------------------------
--
-- Dumping data for table `FilmCountry`
--
INSERT INTO `filmcountry` (`film_id`, `country_id`) VALUES 
('1','1'),
('2','1'),
('3','1'),
('3','2'),
('3','3'),
('3','4'),
('4','1'),
('5','1'),
('5','2'),
('6','1'),
('6','2'),
('7','1'),
('7','2'),
('8','1'),
('8','2'),
('9','1'),
('10','1');

-- --------------------------------------------------------
--
-- Dumping data for table `Language`
--
INSERT INTO `language` (`name`) VALUES 
('Anh'),
('Tây Ban Nha'),
('Pháp'),
('Đức'),
('Nga'),
('Ý'),
('Trung Quốc'),
('Nhật Bản'),
('Hàn Quốc'),
('Việt Nam');

-- --------------------------------------------------------
--
-- Dumping data for table `FilmLanguage`
--
INSERT INTO `filmlanguage` (`film_id`, `language_id`) VALUES 
('1','1'),
('2','1'),
('2','2'),
('2','3'),
('2','5'),
('3','1'),
('3','2'),
('3','5'),
('3','7'),
('4','1'),
('4','3'),
('4','4'),
('4','6'),
('5','1'),
('6','1'),
('6','3'),
('6','7'),
('7','1'),
('8','1'),
('8','8'),
('9','1'),
('9','6'),
('10','1');

-- --------------------------------------------------------
--
-- Dumping data for table `Category`
--
INSERT INTO `category` (`name`) VALUES 
('Chính Kịch'),
('Hành Động'),
('Tội Phạm'),
('Phiêu Lưu'),
('Kỳ Bí'),
('Giật Gân'),
('Kinh Dị'),
('Khoa Học - Viễn Tưởng'),
('Hài Kịch'),
('Kỳ Ảo'),
('Lãng Mạn'),
('Hoạt Hình');

-- --------------------------------------------------------
--
-- Dumping data for table `FilmCategory`
--
INSERT INTO `filmcategory` (`film_id`, `category_id`) VALUES 
('1','1'),
('2','1'),
('3','1'),
('3','2'),
('3','5'),
('4','1'),
('4','2'),
('5','9'),
('5','10'),
('6','2'),
('6','4'),
('6','8'),
('7','1'),
('7','7'),
('7','10'),
('8','1'),
('8','2'),
('8','3'),
('9','1'),
('9','3'),
('10','1'),
('10','5'),
('10','7');


-- --------------------------------------------------------
--
-- Dumping data for table `People`
--
INSERT INTO `people` (`name`, `profile_picture`, `short_introduction`, `height`, `spouse`, `parents`, `dob`, `birthplace`, `fullname`, `role`) VALUES
('Tim Robbins', '', 'Timothy Francis "Tim" Robbins (sinh ngày 16 tháng 10 năm 1958)[1] là một Diễn viên, đạo diễn, nhà sản xuất, nhà biên kịch, nhà hoạt động xã hội, nhạc sĩ người Mỹ. Ông nổi tiếng với vai diễn Nuke trong Bull Durham, Jacob Singer trong Jacob`s Ladder, Griffin Mill trong The Player, Andy Dufresne trong The Shawshank Redemption và vai Dave Boyle trong Mystic River, với vai diễn này ông đã giành Giải Oscar cho nam diễn viên phụ xuất sắc nhất.', '196', 'Gratiela Brancusi(ngày 1 tháng 2 năm 2017 - ngày 25 tháng 8 năm 2022) (ly dị)', 'Gil Robbins, Mary Robbins','1958-10-16', 'California, Hoa Kỳ', 'Timothy Francis Robbins', 'Diễn viên'),
('Morgan Freeman', '', 'Morgan Freeman(tên không có chữ đệm vì bố mẹ ông "quên" đặt) [1] sinh ngày 1 tháng 6 năm 1937 là một nam diễn viên, đạo diễn người Mỹ .Ông là một trong những diễn viên được ưa chuộng nhất ở Hollywood[2]. Ông từng đoạt được Giải Oscar cho Nam diễn viên phụ xuất sắc nhất trong phim Million Dollar Baby và được nhiều đề cử cho các vai của ông trong Street Smart, Driving Miss Daisy, The Shawshank Redemption và Invictus.Ông cũng giành giải Quả Cầu Vàng và Screen Actors Guild Award.', '188', 'Myrna Colley-Lee(ngày 16 tháng 6 năm 1984 - ngày 15 tháng 9 năm 2010) (ly dị), Jeanette Adair Bradshaw (ngày 22 tháng 10 năm 1967 - ngày 18 tháng 11 năm 1979) (ly dị)', 'Freeman (Revere), Mayme Edna; Freeman, Morgan Porterfield','1937-06-01', 'Texas, Hoa Kỳ', 'Morgan Freeman', 'Diễn viên'),
('Frank Darabont', '', 'Frank Árpád Darabont (tên khai sinh là Ferenc Árpád Darabont, ngày 28 tháng 1 năm 1959)[1] là một đạo diễn, nhà biên kịch và nhà sản xuất phim người Mỹ gốc Hungary, người đã được đề cử ba giải Oscar và một giải Quả cầu vàng.', '183', 'Karyn Wagner(?-?)', NULL,'1959-01-28', 'Doubs, Pháp', 'Frank Arpad Darabont', 'Đạo diễn'),
('Anya Taylor-Joy', '', 'Anya Josephine Marie Taylor-Joy (sinh ngày 16 tháng 4 năm 1996) là một nữ diễn viên và người mẫu. Cô là người nhận được một số giải thưởng , bao gồm Giải Quả cầu vàng, Giải của Hiệp hội Diễn viên Màn ảnh và Giải Truyền hình do Nhà phê bình lựa chọn, ngoài các đề cử cho Giải thưởng Điện ảnh Viện Hàn lâm Anh và Giải Primetime Emmy', '173', 'Malcolm McRae (2022 - Hiện tại)', 'Dennis Alan Taylor, Jennifer Marina Joy', '1996-04-16', 'Florida, Hoa Kỳ',  'Anya Josephine Marie Taylor-Joy', 'Diễn viên'),
('Chloe Pirrie', '', 'Chloe Pirrie là một nữ diễn viên người Scotland. Cô đã đóng các vai chính trong miniseries The Game năm 2014, bộ phim Shell năm 2012 và bộ phim truyền hình năm 2015 An Inspector Calls.', '174', NULL, NULL, '1987-08-25', 'Edinburgh, Scotland, Vương Quốc Anh', 'Chloe Pirrie','Diễn viên'),
('Scott Frank', '', 'Scott Frank là đạo diễn, nhà sản xuất, nhà biên kịch và tác giả phim người Mỹ. Frank đã nhận được hai đề cử Giải Oscar cho Kịch bản chuyển thể xuất sắc nhất cho Out of Sight và Logan. Tác phẩm điện ảnh của ông, được ghi có và không được ghi nhận, kéo dài đến hàng chục bộ phim.', NULL, NULL, NULL, '1960-03-10', 'Florida, Hoa Kỳ', 'A. Scott Frank', 'Đạo diễn'),
('Allan Scott', '', 'Allan Scott là 1 đạo diễn, biên kịch (sinh ngày 16 tháng 9 năm 1939) tại Scotland. Ông nổi tiếng với các tác phẩm Don`t Look Now (1973) và Kon-Tiki(2012)', NULL, NULL, NULL, '1939-09-16', 'Elgin, Scotland, Vương Quốc Anh', 'Allan George Shiach', 'Biên kịch'),
('Harrison Ford', '', 'Harrison Ford (sinh ngày 13 tháng 7 năm 1942) là một diễn viên, phi công và nhà hoạt động môi trường người Mỹ. Tính đến năm 2020, tổng doanh thu phòng vé nội địa Hoa Kỳ của các bộ phim mà ông tham gia diễn xuất là hơn 5,4 tỷ USD, và tổng doanh thu phòng vé toàn cầu vượt mốc 9,3 tỷ USD,[1] con số này giúp ông đứng vị trí thứ 7 trong danh sách những ngôi sao có doanh thu phòng vé nội địa cao nhất mọi thời đại.', '185', 'Calista Flockhart (ngày 15 tháng 6 năm 2010 - hiện tại); Melissa Mathison (ngày 14 tháng 3 năm 1983 - ngày 6 tháng 1 năm 2004) (ly dị); Mary Marquardt (ngày 18 tháng 6 năm 1964 - ngày 3 tháng 10 năm 1979) (ly dị)', 'Ford (Nidelman), Dorothy; Ford, Christopher', '1942-07-13', 'Chicago, Hoa Kỳ', 'Harrison Ford', 'Diễn viên'),
('Ryan Gosling', '', 'Ryan Thomas Gosling (sinh ngày 12 tháng 11 năm 1980)[1] là một diễn viên và nhạc sĩ người Canada. Anh từng là diễn viên nhí xuất hiện trong Mickey Mouse Club của Disney Channel (1993–95)', '184', 'Eva Mender (2011 - Hiện tại)', 'Gosling (Wilson), Donna Gosling, Thomas Ray', '1980-11-12', 'Ontario, Canada', 'Ryan Thomas Gosling', 'Diễn viên'),
('Denis Villeneuve', '', 'Denis Villeneuve OC CQ là một đạo diễn, biên kịch và nhà sản xuất điện ảnh người Canada gốc Pháp.', '182', 'Tanya Lapointe(? - Hiện tại)', 'Jean Villeneuve, Nicole Demers', '1967-10-03', 'Quebec, Canada','Denis Villeneuve', 'Đạo diễn'),
('Jamie Foxx', '', 'Eric Marlon Bishop (sinh ngày 13 tháng 12 năm 1967), thường được biết đến rộng rãi là Jamie Foxx, là một diễn viên, diễn viên hài và ca sĩ người Mỹ.', '175', NULL, 'Darrell Bishop, Louise Annette Talley', '1967-12-13','Texas, Hoa Kỳ', 'Eric Marlon Bishop', 'Diễn viên'),
('Christoph Waltz', '', 'Christoph Waltz (sinh ngày 4 tháng 10 năm 1956) là một diễn viên điện ảnh, diễn viên phim truyền hình, diễn viên lồng tiếng và nghệ sĩ kịch nói người Áo.', '171', 'Jackie (? - ?) (ly dị); Judith Holste (? - Hiện tại)', 'Johannes Waltz, Elisabeth Urbancic', '1956-10-04', 'Vienna, Áo', 'Christoph Waltz', 'Diễn viên'),
('Quentin Tarantino', '', 'Quentin Jerome Tarantino (sinh ngày 27 tháng 3 năm 1963) là nhà làm phim và nam diễn viên người Mỹ', '185', 'Daniella Pick (ngày 28 tháng 11 năm 2018 - Hiện tại)', 'Tony Tarantino, Connie Zastoupil', '1963-03-27', 'Tennessee, Hoa Kỳ', 'Quentin Jerome Tarantino', 'Đạo diễn'),
('David Tennant', '', 'David John Tennant là một nam diễn viên người Scotland. Anh nổi tiếng với vai diễn hóa thân thứ mười của Bác sĩ trong loạt phim khoa học viễn tưởng Doctor Who của đài BBC.', '185', 'Geogria Tennant (ngày 30 tháng 12 năm 2011 - Hiện tại)', 'Sandy McDonald, Essdale Helen', '1971-04-18', 'Scotland, Vương Quốc Anh', 'David John McDonald', 'Diễn viên'),
('Michael Sheen', '', 'Michael Christopher Sheen là một diễn viên, nhà sản xuất truyền hình và nhà hoạt động chính trị người xứ Wales.', '178', NULL, 'Irene Sheen, Meyrick Sheen', '1969-02-05', 'Xứ Wales, Vương Quốc Anh', 'Michael Christopher Sheen', 'Diễn viên'),
('Douglas Mackinnon', '', 'Douglas Mackinnon là một đạo diễn phim và truyền hình người Scotland đến từ Portree, Isle of Skye. Ông đã đạo diễn nhiều tập phim truyền hình dài tập và ít nhất ba phim truyền hình.', NULL, NULL, NULL, NULL, 'Uig, Vương Quốc Anh', 'Douglas Mackinnon', 'Đạo diễn'),
('Leonardo DiCaprio', '', 'Leonardo Wilhelm DiCaprio (sinh ngày 11 tháng 11 năm 1974) là một diễn viên, nhà sản xuất phim, nhà hoạt động môi trường người Mỹ.', '183', NULL, 'Irmelin DiCaprio, Geogre DiCaprio', '1974-11-11', 'Los Angeles, Hoa Kỳ', 'Leonardo Wilhelm DiCaprio', 'Diễn viên'),
('Joseph Gordon-Levitt', '', 'Joseph Leonard Gordon-Levitt (sinh ngày 17/02/1981) là một diễn viên điện ảnh, đạo diễn, nhà biên kịch và sản xuất phim người Mỹ.', '176', 'Tasha McCauley (ngày 20 tháng 12 năm 2014 - Hiện tại)', 'Jane Gordon-Levitt, Dennis Gordon-Levitt', '1981-02-17', 'Los Angeles, Hoa Kỳ', 'Joseph Leonard Gordon-Levitt', 'Diễn viên'),
('Christopher Nolan', '', 'Christopher Edward Nolan CBE (/ˈnoʊlən/; sinh ngày 30 tháng 7 năm 1970) là một đạo diễn, nhà sản xuất và nhà biên kịch điện ảnh người Anh gốc Mỹ.', '181', 'Emma Thomas (1997 - Hiện tại)', 'Brendan Nolan, Christina Nolan', '1970-07-30', 'London, Vương Quốc Anh', 'Christopher Edward Nolan', 'Đạo diễn'),
('Tom Sturridge', '', 'Thomas Sidney Jerome Sturridge là một diễn viên người Anh được biết đến với các tác phẩm The Sandman, Being Julia, Like Minds và The Boat That Rocked.', '178', NULL, 'Charles Sturridge, Phoebe Nicholls', '1985-12-21', 'London, Vương Quốc Anh', 'Thomas Sidney Jerome Sturridge', 'Diễn viên'),
('Jenna Coleman', '', 'Jenna-Louise Coleman hay Jenna Coleman là diễn viên người Anh, sinh ngày 27 tháng 4 năm 1986. Cô được biết đến nhiều nhất ở vai Jasmine Thomas ở vở Opera xà phòng Emmerdale ( 2012- 2015, 2017)`.', '157', NULL, 'Keith Coleman, Karen Coleman', '1986-04-27', 'Blackpool, Lancashire, Vương Quốc Anh', 'Jenna-Louise Coleman',  'Diễn viên'),
('Neil Gaiman', '', 'Neil Gaiman là một tiểu thuyết gia, nhà văn viết truyện ngắn, tác gia truyện tranh, nhà biên kịch người Anh. Những tác phẩm đáng chú ý của ông bao gồm loạt truyện tranh The Sandman và các tiểu thuyết Stardust, American Gods, Coraline, và The Graveyard Book.', '178', 'Amanda Palmer (10 tháng 11 2010 - Hiện tại)  (1 con), Mary T. McGrath (1985 - ?)  (ly dị)  (3 con)', 'Gaiman, David Bernard, Sheila', '1960-11-10', 'Portchester, Vương Quốc Anh', 'Neil R. Gaiman', 'Tác giả'),
('David S. Goyer', '', 'David Samuel Goyer (sinh ngày 22 tháng 12 năm 1965) là một nhà biên kịch, đạo diễn điện ảnh, tiểu thuyết gia, nhà sản xuất và nhà viết truyện tranh người Mỹ.','168', 'Marina Black (? - Hiện tại)', NULL, '1965-12-22', 'Michigan, Hoa Kỳ', 'David Samuel Goyer', 'Đạo diễn'),
('Christian Bale', '', 'Christian Charles Philip Bale (sinh ngày 30 tháng 1 năm 1974 tại Haverfordwest, Pembrokeshire, Wales) là một diễn viên người Anh.', '183', 'Sibi Blazic (ngày 29 tháng 1 năm 2000 - Hiện tại)', 'David Bale, Gloria Steinem', '1974-01-30', 'Xứ Wales, Vương Quốc Anh', 'Christian Charles Philip Bale', 'Diễn viên'),
('Heath Ledger', '', 'Heathcliff Andrew Ledger là một diễn viên Úc từng đoạt giải Oscar.Sau khi thực hiện các vai truyền hình trong thập niên 1990, Ledger đã phát triển sự nghiệp ở Hollywood. ', '185', NULL, 'Sally Bell, Kim Ledger', '1979-04-04', 'Perth, Úc', 'Heath Andrew Ledger', 'Diễn viên'),
('Marlon Brando', '', 'Marlon Brando, Jr., thường được biết tới với tên Marlon Brando là một diễn viên nổi tiếng người Mỹ. Từng hai lần đoạt Giải Oscar Vai nam chính, Marlon Brando được coi là một trong những diễn viên có ảnh hưởng nhất của lịch sử điện ảnh Mỹ, ông được xếp thứ 4 trong Danh sách 100 ngôi sao điện ảnh của Viện phim Mỹ.', '175', 'Tarita (ngày 10 tháng 8 năm 1962 - ngày 14 tháng 7 năm 1972)  (ly dị), Movita (ngày 4 tháng 6 năm 1960 - năm 1962)  (ly dị), Anna Kashfi (ngày 11 tháng 10 năm 1957 - ngày 22 tháng 4 năm 1959)  (ly dị)', 'Brando Sr., Marlon; Brando (Pennebaker), Dorothy Julia', '1924-04-03', 'Nebraska, Hoa Kỳ', 'Marlon Brando Jr.', 'Diễn viên'),
('Al Pacino', '', 'Alfredo James Pacino (sinh ngày 25 tháng 4 năm 1940), thường được biết đến với tên Al Pacino là một diễn viên nổi tiếng của sân khấu và điện ảnh Hoa Kỳ.', '168', NULL, 'Sal Pacino, Rose Pacino', '1940-04-25', 'New York, Hoa Kỳ', 'Alfredo James Pacino', 'Diễn viên'),
('Francis Ford Coppola', '', 'Francis Ford Coppola (sinh ngày 7 tháng 4 năm 1939) là một nhà đạo diễn, nhà sản xuất và nhà biên kịch phim của điện ảnh Mỹ.', '182', 'Eleanor Coppola (ngày 2 tháng 2 năm 1963 - Hiện tại)', 'Carmine Coppola, Italia Coppola', '1939-04-07', 'Michigan, Hoa Kỳ', 'Francis Ford Coppola', 'Đạo diễn'),
('Victoria Pedretti', '', 'Victoria Pedretti là một nữ diễn viên người Mỹ. Được công nhận là nữ hoàng la hét thời hiện đại, cô được biết đến với vai diễn những nhân vật phản diện bị quấy rầy.', '160', NULL, 'Michael Pedretti', '1995-03-23', 'Pennsylvania, Hoa Kỳ', 'Victoria Pedretti', 'Diễn viên'),
('Oliver Jackson-Cohen', '', 'Oliver Mansour Jackson-Cohen là một diễn viên và người mẫu người Anh. Anh được biết đến với vai Adrian Griffin trong bộ phim chuyển thể Người vô hình năm 2020 và vai Luke Crain và Peter Quint trong các chương trình truyền hình Netflix lần lượt là The Haunting of Hill House và The Haunting of Bly Manor.', '194', NULL, 'Betty Jackson, David Cohen', '1986-10-24', 'London, Vương Quốc Anh', 'Oliver Mansour Jackson-Cohen', 'Diễn viên'),
('Mike Flanagan', '', 'Mike Flanagan là một nhà làm phim người Mỹ và là đối tác của Intrepid Pictures. Tác phẩm của Flanagan đã thu hút được sự khen ngợi của các nhà phê bình vì sự đạo diễn và thiếu phụ thuộc vào những pha hù dọa nhảy vọt; Stephen King, Quentin Tarantino và William Friedkin, trong số những người khác, đã ca ngợi ông.', '183', 'Kate Siegel (ngày 6 tháng 2 năm 2016 - Hiện tại)', 'Timothy Flanagan, Laura Flanagan', '1978-05-20', 'Massachusetts, Hoa Kỳ', 'Mike Flanagan', 'Đạo diễn');

-- --------------------------------------------------------
--
-- Dumping data for table `PeopleFilm`
--

INSERT INTO `peoplefilm` (`film_id`, `people_id`) VALUES 
('1','1'),
('1','2'),
('1','3'),
('2','4'),
('2','5'),
('2','6'),
('2','7'),
('3','8'),
('3','9'),
('3','10'),
('4','11'),
('4','12'),
('4','13'),
('5','14'),
('5','15'),
('5','16'),
('6','17'),
('6','18'),
('6','19'),
('7','20'),
('7','21'),
('7','22'),
('7','23'),
('8','24'),
('8','25'),
('8','19'),
('9','26'),
('9','27'),
('9','28'),
('10','29'),
('10','30'),
('10','31');

-- --------------------------------------------------------
--
-- Dumping data for table `FilmCharacter`
--

INSERT INTO `filmcharacter` (`film_id`, `people_id`, `name`) VALUES 
('1', '1', 'Andy Dufresne'),
('1', '2', 'Ellis Boyd Red Redding'),
('2', '4', 'Beth Harmon'),
('2', '5', 'Alice Harmon'),
('3', '8', 'Rick Deckard'),
('3', '9', 'K'),
('4', '11', 'Django'),
('4', '12', 'Dr. King Schultz'),
('5', '14', 'Aziraphale'),
('5', '15', 'Crowley'),
('6', '17', 'Cobb'),
('6', '18', 'Arthur'),
('7', '20', 'Morpheus/ Dream'),
('7', '21', 'Johanna Constantine'),
('8', '24', 'Bruce Wayne/ Batman'),
('8', '25', 'Joker'),
('9', '26', 'Don Vito Corleone'),
('9', '27', 'Michael Corleone'),
('10', '29', 'Dani Clayton'),
('10', '30', 'Peter Quint');

-- --------------------------------------------------------
--
-- Dumping data for table `User`
--
INSERT INTO `chephim`.`user` (`username`, `email`, `password`, `avatar_path`, `status`) VALUES 
('admin1', 'admin1@gmail.com', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', ' ', 'Active'),
('admin2', 'admin2@gmail.com', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', ' ', 'Inactive'),
('manager1', 'manager1@gmail.com', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', ' ', 'Active'),
('manager2', 'manager2@gmail.com', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', ' ', 'Inactive'),
('manager3', 'manager3@gmail.com', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', ' ', 'Active'),
('user1', 'user1@gmail.com', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', ' ', 'Active'),
('user2', 'user2@gmail.com', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', ' ', 'Inactive'),
('user3', 'user3@gmail.com', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', ' ', 'Active'),
('user4', 'user4@gmail.com', '$2a$12$9Ed36smLhYCCl1V5.7EWguLdY9asTwrvUUoyix5Du/T1CcyswdAwa', ' ', 'Inactive'),
('user5', 'user5@gmail.com', '$2a$12$sMExyEbvpAu432Iaci3HKer1CXG8h8YECE7gIkUGw3MPJTR5fjGCK', ' ', 'Active'),
('user6', 'user6@gmail.com', '$2a$12$sMExyEbvpAu432Iaci3HKer1CXG8h8YECE7gIkUGw3MPJTR5fjGCK', ' ', 'Active'),
('user7', 'user7@gmail.com', '$2a$12$sMExyEbvpAu432Iaci3HKer1CXG8h8YECE7gIkUGw3MPJTR5fjGCK', ' ', 'Active'),
('user8', 'user8@gmail.com', '$2a$12$h0wlfbltPOuFRggGTMqFGuHAA/T6o0JBl5chKo1BpwyMmpvxefYP2
', ' ', 'Active'),
('user9', 'user9@gmail.com', '$2a$12$gJir45gPwRAusyuQ5J/PHuzIGAd7LZy6iIMy3qoe1giH3A3lX0o1q
', ' ', 'Active'),
('user10', 'user10@gmail.com', '$2a$12$QWUSgWW4PAPxCoSFoD4cOekdp0TRqpmFxbzdf7iZZ7CkqWwbLY3eO', ' ', 'Active');

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

-- --------------------------------------------------------
--
-- Dumping data for table `Watchlist`
--
INSERT INTO `watchlist` (`name`, `user_id`) VALUES 
('watchlist1', '1'),
('watchlist2', '2'),
('watchlist3', '3'),
('watchlist4', '4'),
('watchlist5', '5'),
('watchlist6', '6'),
('watchlist7', '7'),
('watchlist8', '8'),
('watchlist9', '9'),
('watchlist10', '10'),
('watchlist11', '11'),
('watchlist12', '12'),
('watchlist13', '13'),
('watchlist14', '14'),
('watchlist15', '15');

-- --------------------------------------------------------
--
-- Dumping data for table `FilmWatchlist`
--
INSERT INTO `filmwatchlist` (`watchlist_id`, `film_id`) VALUES 
('1','1'),
('1','2'),
('1','3'),
('2','4'),
('2','5'),
('2','6'),
('3','7'),
('3','8'),
('3','9'),
('4','10'),
('4','1'),
('4','2'),
('5','3'),
('5','4'),
('5','5'),
('6','6'),
('6','7'),
('6','8'),
('7','9'),
('7','10'),
('7','1'),
('8','2'),
('8','3'),
('8','4'),
('9','5'),
('9','6'),
('9','7'),
('10','8'),
('10','9'),
('10','10'),
('11','1'),
('11','3'),
('11','5'),
('12','7'),
('12','9'),
('12','2'),
('13','4'),
('13','6'),
('13','8'),
('14','10'),
('14','1'),
('14','5'),
('15','2'),
('15','5'),
('15','8');

-- --------------------------------------------------------
--
-- Dumping data for table `RatingReviews`
--

INSERT INTO `ratingreviews` (`film_id`, `user_id`, `rating_star`, `review`) VALUES 
('1', '6', '9', 'Đây là một bộ phim tuyệt vời. Nó được xây dựng tốt, diễn xuất tuyệt vời và các nhân vật rất đáng yêu hoặc trong trường hợp là những con lợn thật đáng ghét.'),
('1', '7', '10', 'Các nhân vật được diễn viên phát triển và diễn giải tốt đến mức bạn thực sự cảm thấy thích họ, điều này khiến toàn bộ câu chuyện càng trở nên hấp dẫn hơn. Cấu trúc xây dựng phân tích cảm xúc như chỉ có Darabont mới có thể làm được. Thật ngoạn mục!'),
('2', '8', '8', 'Tôi đã xem khá nhiều phim về cờ vua và phim này cho đến nay là hay nhất. Nó kết hợp những điều kỳ quặc của việc trở thành một người hiểu biết trong một trò chơi và những vấn đề cơ bản đi kèm với nó. Nói chung trò chơi cờ vua vô địch là cực kỳ nhàm chán vì nó thường diễn ra trong 2 giờ một ván đấu, thường xuyên 10-20 phút / nước đi. Bộ phim này tăng tốc tất cả nhưng vẫn giữ cho mọi thứ có cơ sở và thú vị.'),
('2', '9', '9', 'Đây không phải là loại chương trình điển hình mà tôi thường xem nhưng tôi rất vui vì đã làm được. Nó độc đáo và khác biệt đến mức khó diễn tả thành lời. Nếu bạn đang nghĩ đến việc xem chương trình này, hãy cứ làm đi, bạn sẽ không thất vọng đâu.'),
('3', '10', '8', 'Tôi yêu thích phần tiếp theo này và tận hưởng nhịp độ chậm rãi và cảm giác vô vọng ngay từ đầu. Tôi thích họ đã đi đâu với câu chuyện và cách nó để lại cho bạn những câu hỏi chưa được trả lời như bản gốc. Để mọi chuyện tranh luận giữa các fan. Bộ phim đầu tiên là thiên tài và tôi nghĩ bộ phim này gần với nó. Phần tiếp theo tuyệt vời!'),
('3', '11', '9', 'Chính những ký ức của chúng ta đã tạo nên chúng ta, những cảm xúc và cảm xúc đưa chúng ta đến những nơi xa hơn tầm với của chúng ta, những điều mà giáo viên của chúng ta không thể dạy, khiến chúng ta trở nên chính xác như chúng ta, khi chúng ta du hành từ gần đến xa, thông qua những tưởng tượng dị thường. , với sự gợi nhớ lớn nhất của chúng tôi ...'),
('4', '12', '8', 'Một kiệt tác xuất sắc của một bộ phim. Cách thức khắc nghiệt mà chế độ nô lệ ở Mỹ trong quá khứ được miêu tả chỉ đơn giản là gây kinh ngạc. Quentin Tarantino đã nỗ lực hết mình để cho người xem thấy chế độ nô lệ trong những ngày đó như thế nào. Mỗi nhân vật trong phim đều được chọn lựa một cách khéo léo và thể hiện một cách lộng lẫy. Mỗi cảnh quay với Jamie Foxx và Christoph Waltz đều đáng nhớ và ấn tượng.'),
('4', '13', '9', 'Chỉ đạo tuyệt vời, kịch bản, biểu diễn, nhạc phim, quay phim, thiết kế trang phục, thiết kế sản xuất, tất cả những điều bạn có thể mong đợi từ một bộ phim của Tarantino.'),
('5', '14', '7', 'Tôi muốn thích nó hơn rất nhiều so với thực tế, nhưng tôi sẽ xem đi xem lại những màn trình diễn tuyệt vời của David Tennant và Michael Sheen. Việc xây dựng thế giới được thực hiện rất đẹp và câu chuyện thực sự cảm động. Nhưng thật không may, phần còn lại của dàn diễn viên lại bị lép vế và đôi khi sự hài hước cũng hơi lắt léo.'),
('5', '15', '9', 'Đây là bộ phim hài hay nhất: khiếu hài hước thông minh, cốt truyện hấp dẫn, lời thoại hấp dẫn và diễn xuất tốt.'),
('6', '6', '9', 'Inception hay một cách đáng kinh ngạc. Âm nhạc: kinh điển. Cốt chuyện: Trên cả tuyệt vời. Diễn xuất: Tuyệt vời. Quay phim & Hiệu ứng Đặc biệt: Tuyệt vời. Nhìn chung, đây là một bộ phim tuyệt vời, mặc dù phức tạp, tôi cần phải xem lại lần thứ hai để hiểu hết mọi thứ, đây là bộ phim phải xem đối với bất kỳ ai.'),
('6', '7', '9', 'Thế kỷ 20 có Casablanca, Chiến tranh giữa các vì sao, Bố già, Blade Runner và những tác phẩm khác - đây là kiệt tác đầu tiên của thế kỷ 21. Thực sự kinh ngạc trước bộ phim này tuyệt vời như thế nào, một màn trình diễn đáng kinh ngạc về tất cả các khía cạnh của bộ phim. Câu chuyện tuyệt vời và kỹ thuật quay phim đi kèm với các diễn viên xứng đáng để kể câu chuyện. Đáng chú ý và ngoạn mục.'),
('7', '8', '7', 'The Sandman là một cuộc phiêu lưu giả tưởng ly kỳ, đầy hình ảnh tuyệt đẹp và cốt truyện ám ảnh. Chỉ cần chắc chắn là dừng xem sau 6 tập. Không, tôi nghiêm túc và không nói đùa. Xem 6 tập đầu rồi dừng. Câu chuyện chính kết thúc tốt đẹp, và bạn sẽ cảm thấy hài lòng với bộ truyện.'),
('7', '9', '8', 'Thật tuyệt khi cuối cùng cũng được thấy những cuốn truyện tranh nổi tiếng được chuyển thể thành một bộ truyện hài lòng và phức tạp với mười tập đặc sắc được đóng gói với nhịp độ tốt và vô cùng gay cấn.'),
('8', '10', '9', 'Được đạo diễn tự tin, đen tối, nghiền ngẫm và đóng gói với các phân đoạn hành động ấn tượng và một câu chuyện phức tạp, The Dark Knight bao gồm một bước ngoặt xác định sự nghiệp của Heath Ledger cũng như các màn trình diễn xứng đáng với giải Oscar khác, TDK không chỉ là phim Batman hay nhất mà còn là truyện tranh phim từng được tạo.'),
('8', '11', '9', 'Tôi có thể nói gì, nó được tạo ra bởi Christopher Nolan nên nó phải tốt. Hành động tuyệt vời. Nhân vật tuyệt vời. Giám đốc tuyệt vời. The Dark Knight có tất cả mọi thứ. Tôi không có vấn đề gì với bộ phim này. Nhưng điều khiến tôi không cho bộ phim này điểm 10 là có điều gì đó về nó không hoàn toàn kết nối với tôi.'),
('9', '12', '9', 'Phim này mạnh, kịch bản hay, dàn diễn viên tuyệt vời, diễn xuất xuất sắc và đạo diễn vượt trội. Thật khó để chê một bộ phim làm tốt điều này, nó đã 29 tuổi và đã già đi rất nhiều. Ngay cả khi người xem không thích thể loại phim mafia, họ sẽ xem toàn bộ bộ phim, khán giả sẽ dán mắt vào những gì sẽ xảy ra tiếp theo khi bộ phim tiến triển. Đó là về gia đình, lòng trung thành, lòng tham, các mối quan hệ và cuộc sống thực. Đây là một sự kết hợp tuyệt vời, và phong cách nghệ thuật làm cho bộ phim trở nên đáng nhớ.'),
('9', '13', '10', 'Đây không chỉ là một bộ phim xã hội đen được dàn dựng đẹp mắt. Hoặc một bức chân dung gia đình nổi bật, cho vấn đề đó. Một giai đoạn tuyệt vời. Một nghiên cứu về nhân vật. Một bài học làm phim và là nguồn cảm hứng cho các thế hệ diễn viên, đạo diễn, nhà biên kịch và nhà sản xuất. Đối với tôi, điều này còn hơn thế nữa: đây là bộ phim dứt khoát. 10 sao trên 10.'),
('10', '14', '7', 'Đó là một câu chuyện tình yêu, với một số bóng ma trong đó, vì vậy Đừng mong đợi những cảnh sợ hãi như trong Hill House. Âm nhạc, diễn xuất, bối cảnh, lời thoại, tất cả đều rất kỳ lạ và hipnotic. Mỗi tập phim giải thích điều gì đó liên quan đến từng nhân vật và đến tập 8, bạn sẽ có được bức tranh toàn cảnh về những gì thực sự đang diễn ra. Có rất nhiều bí ẩn với mỗi nhân vật trải qua những kịch tính và đau buồn của riêng họ. Cuối cùng, đó là một câu chuyện tình yêu bi thảm. Một cái đẹp.'),
('10', '15', '8', 'Một bộ phim được làm tốt khác, cốt truyện hấp dẫn, kỹ xảo điện ảnh đẹp và diễn xuất tuyệt vời. Những cảm xúc được truyền tải trong câu chuyện này, và tôi đồng ý rằng nó là một câu chuyện tình yêu, rất ngoạn mục. Trung thực tốt được thực hiện trong tất cả các tài khoản.');
