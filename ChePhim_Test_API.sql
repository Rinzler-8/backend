DROP DATABASE IF EXISTS ChePhim;
CREATE DATABASE ChePhim;
USE ChePhim;

DROP TABLE IF EXISTS Roles;
CREATE TABLE Roles(
                      RoleId     SMALLINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
                      RoleName 	ENUM ('ADMIN', 'MANAGER','USER') UNIQUE
);
INSERT INTO `chephim`.`Roles` (RoleId, RoleName) VALUES ('1', 'ADMIN');
INSERT INTO `chephim`.`Roles` (RoleId, RoleName) VALUES ('2', 'MANAGER');
INSERT INTO `chephim`.`Roles` (RoleId, RoleName) VALUES ('3', 'USER');



DROP TABLE IF EXISTS `Account`;
CREATE TABLE `Account`(

                          accountID            SMALLINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                          email                VARCHAR(50) NOT NULL UNIQUE KEY,
                          username             VARCHAR(50) NOT NULL UNIQUE KEY,
                          CreateDate           DATETIME DEFAULT NOW(),
                          url_avatar           VARCHAR(100) UNIQUE KEY,
                          `password`           VARCHAR(100)
);

/*============================== INSERT DATABASE =======================================*/
/*======================================================================================*/

-- Add data Account

INSERT INTO `Account`(Email						, Username			, CreateDate  				,`password`													)
VALUES
('admin@gmail.com'				, 'admin'			,'2022-05-19' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi' 	),
('Email1@gmail.com'				, 'Username1'	 	,'2021-03-05' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi' 	),
('Email2@gmail.com'				, 'Username2'	 	,'2020-06-05' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	),
('Email3@gmail.com'			, 'Username3'	 	,'2019-06-07' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	),
('Email4@gmail.com'			, 'Username4'	 	,'2018-03-04' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	),
('Email5@gmail.com'			, 'Username5'	 	,'2020-02-10' ,		    '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	),
('Email6@gmail.com'				, 'Username6'	 	,'2017-02-11' ,		    '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	);

-- INSERT INTO `Account`(email							, username		, CreateDate  				,`password`														)
-- VALUES
-- 					('admin@gmail.com'				, 'admin'		,'2022-05-19' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi' 		),
-- 					('Email1@gmail.com'				, 'Username1'  	,'2021-03-05' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi' 		),
-- 					('Email2@gmail.com'				, 'Username2'	,'2020-06-05' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'		),
--                     ('Email3@gmail.com'				, 'Username3'	,'2019-06-07' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'		),
--                     ('Email4@gmail.com'				, 'Username4'	,'2018-03-04' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'		),
--                     ('Email5@gmail.com'				, 'Username5'	,'2020-02-10' ,		    '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'		),
--                     ('Email6@gmail.com'				, 'Username6'	,'2017-02-11' ,		    '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'		);


CREATE TABLE account_roles(
                              account_ID 	SMALLINT ,
                              role_ID     SMALLINT UNSIGNED  ,
                              PRIMARY KEY(role_ID,account_ID),
                              FOREIGN KEY (account_ID)  REFERENCES `Account`(accountID),
                              FOREIGN KEY (role_ID)  REFERENCES `Roles`(RoleId)
);
INSERT INTO account_roles(account_ID,	role_ID)
VALUES					 (1			,		1),
                           (1			,		2),
                           (1			,		3),
                           (2			,		3),
                           (3			,		2),
                           (2			,		2);