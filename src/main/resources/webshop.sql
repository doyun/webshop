-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.7-rc-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.2.0.4947
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for webshop
DROP DATABASE IF EXISTS `webshop`;
CREATE DATABASE IF NOT EXISTS `webshop` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `webshop`;


-- Dumping structure for table webshop.brand
DROP TABLE IF EXISTS `brand`;
CREATE TABLE IF NOT EXISTS `brand` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table webshop.brand: ~3 rows (approximately)
DELETE FROM `brand`;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` (`id`, `name`) VALUES
	(1, 'Burton'),
	(3, 'Gnu'),
	(2, 'Jones');
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;


-- Dumping structure for table webshop.flex
DROP TABLE IF EXISTS `flex`;
CREATE TABLE IF NOT EXISTS `flex` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table webshop.flex: ~2 rows (approximately)
DELETE FROM `flex`;
/*!40000 ALTER TABLE `flex` DISABLE KEYS */;
INSERT INTO `flex` (`id`, `name`) VALUES
	(2, 'Shiffer'),
	(1, 'Softer');
/*!40000 ALTER TABLE `flex` ENABLE KEYS */;


-- Dumping structure for table webshop.oder_payment
DROP TABLE IF EXISTS `oder_payment`;
CREATE TABLE IF NOT EXISTS `oder_payment` (
  `payment_uid` varchar(36) NOT NULL,
  `order_id` int(11) NOT NULL,
  PRIMARY KEY (`order_id`),
  CONSTRAINT `oder_credit_card_order_id_foreign` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table webshop.oder_payment: ~0 rows (approximately)
DELETE FROM `oder_payment`;
/*!40000 ALTER TABLE `oder_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `oder_payment` ENABLE KEYS */;


-- Dumping structure for table webshop.order
DROP TABLE IF EXISTS `order`;
CREATE TABLE IF NOT EXISTS `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(20) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status_info` varchar(100) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `payment_type` varchar(20) DEFAULT NULL,
  `delivery_type` varchar(20) DEFAULT NULL,
  `price` decimal(7,2) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_user_id_foreign_idx` (`user_id`),
  CONSTRAINT `order_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=142 DEFAULT CHARSET=utf8;

-- Dumping data for table webshop.order: ~14 rows (approximately)
DELETE FROM `order`;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` (`id`, `status`, `date`, `status_info`, `user_id`, `payment_type`, `delivery_type`, `price`, `address`) VALUES
	(126, 'IS_FORMING', '2015-05-14 18:50:48', NULL, 10, NULL, NULL, NULL, NULL),
	(127, 'CONFIRMED', '2015-05-14 18:51:46', NULL, 10, 'cash', 'address', 293.65, 'adfasdf'),
	(128, 'IS_FORMING', '2015-05-15 10:15:56', NULL, 10, NULL, NULL, NULL, NULL),
	(129, 'CONFIRMED', '2015-05-15 10:18:23', NULL, 10, 'card', 'address', 2070.32, 'Gagarina avenue 12 12'),
	(130, 'CONFIRMED', '2015-05-15 10:20:40', NULL, 10, 'card', 'address', 293.65, '.'),
	(131, 'IS_FORMING', '2015-05-15 10:24:49', NULL, 10, NULL, NULL, NULL, NULL),
	(132, 'CONFIRMED', '2015-05-15 16:53:42', NULL, 10, 'cash', 'address', 1119.80, 'sdfsdfg'),
	(133, 'IS_FORMING', '2015-05-15 18:49:44', NULL, 10, 'cash', 'address', 2140.79, 'hiuihihiioihjipjph'),
	(134, 'CONFIRMED', '2015-05-18 13:30:40', NULL, 10, 'cash', 'address', 761.91, 'asdf'),
	(135, 'IS_FORMING', '2015-05-18 13:53:55', NULL, 10, 'cash', 'point', 726.42, 'sdf'),
	(136, 'CONFIRMED', '2015-05-18 14:19:29', NULL, 10, 'cash', 'address', 293.65, 'asdf'),
	(137, 'CONFIRMED', '2015-05-18 14:26:40', NULL, 10, 'cash', 'address', 796.11, 'khklj'),
	(138, 'CONFIRMED', '2015-05-18 15:17:30', NULL, 10, 'cash', 'address', 223.96, 'asfd'),
	(139, 'IS_FORMING', '2015-05-22 18:34:51', NULL, 10, NULL, NULL, NULL, NULL),
	(140, 'CONFIRMED', '2015-05-26 19:13:15', NULL, 10, 'cash', 'address', 293.65, 'fsfaw'),
	(141, 'IS_FORMING', '2015-05-28 11:14:32', NULL, 10, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;


-- Dumping structure for table webshop.order_item
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE IF NOT EXISTS `order_item` (
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `price` decimal(7,2) NOT NULL,
  PRIMARY KEY (`order_id`,`product_id`),
  KEY `order_item_product_id_foreign_idx` (`product_id`),
  CONSTRAINT `order_item_order_id_foreign` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `order_item_product_id_foreign` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table webshop.order_item: ~24 rows (approximately)
DELETE FROM `order_item`;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` (`order_id`, `product_id`, `amount`, `price`) VALUES
	(126, 4, 1, 293.65),
	(127, 4, 1, 293.65),
	(128, 4, 1, 293.65),
	(128, 8, 2, 594.96),
	(128, 9, 2, 849.00),
	(129, 3, 1, 427.46),
	(129, 4, 4, 293.65),
	(129, 13, 1, 468.26),
	(130, 4, 1, 293.65),
	(131, 4, 2, 293.65),
	(132, 2, 5, 223.96),
	(133, 2, 4, 223.96),
	(133, 3, 1, 427.46),
	(133, 7, 1, 349.23),
	(133, 13, 1, 468.26),
	(134, 4, 1, 293.65),
	(134, 13, 1, 468.26),
	(135, 1, 1, 502.46),
	(135, 2, 1, 223.96),
	(136, 4, 1, 293.65),
	(137, 1, 1, 502.46),
	(137, 4, 1, 293.65),
	(138, 2, 1, 223.96),
	(139, 4, 1, 293.65),
	(140, 4, 1, 293.65),
	(141, 1, 1, 502.46);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;


-- Dumping structure for table webshop.product
DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `brand` int(11) NOT NULL,
  `model` varchar(80) NOT NULL,
  `flex` int(11) NOT NULL,
  `price` decimal(7,2) NOT NULL,
  `rocker_type` int(11) NOT NULL,
  `picture` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `brand_idx` (`brand`),
  KEY `camber_idx` (`rocker_type`),
  KEY `flex_idx` (`flex`),
  CONSTRAINT `product_brand_foreign` FOREIGN KEY (`brand`) REFERENCES `brand` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `product_camber_foreign` FOREIGN KEY (`rocker_type`) REFERENCES `rocker_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `product_flex_foreign` FOREIGN KEY (`flex`) REFERENCES `flex` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- Dumping data for table webshop.product: ~12 rows (approximately)
DELETE FROM `product`;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`id`, `brand`, `model`, `flex`, `price`, `rocker_type`, `picture`) VALUES
	(1, 1, 'Custom X Snowboard 2015', 2, 502.46, 1, 'pictures\\burton-custom-x-snowboard-2015-152.jpg'),
	(2, 1, 'Genie Snowboard - Women\'s 2015', 1, 223.96, 3, 'pictures\\burton-genie-snowboard-women-s-2015-140.jpg'),
	(3, 1, 'Custom Flying V Snowboard 2015', 1, 427.46, 4, 'pictures\\burton-custom-flying-v-snowboard-2015-154.jpg'),
	(4, 1, 'Name Dropper Snowboard 2015', 2, 293.65, 1, 'pictures\\burton-name-dropper-snowboard-2015-155.jpg'),
	(5, 3, 'Billy Goat C3 BTX Snowboard 2015', 2, 377.26, 6, 'pictures\\gnu-billy-goat-c3-snowboard-2015-156.jpg'),
	(6, 3, 'Ladies Choice Gold Edition EC2 BTX Snowboard - Women\'s 2015', 1, 419.32, 2, 'pictures\\gnu-ladies-choice-gold-edition-ec2-btx-snowboard-women-s-2015-145-5.jpg'),
	(7, 3, 'Ladies Smart Pickle BTX Snowboard - Women\'s 2015', 2, 349.23, 5, 'pictures\\gnu-ladies-smart-pickle-btx-snowboard-women-s-2015-140.jpg'),
	(8, 3, 'Beast DC3 BTX Splitboard 2015', 2, 594.96, 2, 'pictures\\gnu-beast-dc3-btx-splitboard-2015-158.jpg'),
	(9, 2, 'Solution Splitboard 2015', 2, 849.00, 4, 'pictures\\jones-solution-splitboard-2015.jpg'),
	(10, 2, 'Explorer Splitboard 2015', 2, 679.00, 2, 'pictures\\jones-explorer-splitboard-2015-155.jpg'),
	(11, 2, 'Solution Splitboard - Women\'s 2015', 2, 849.00, 4, 'pictures\\solution-splitboard-womens-2015.jpg'),
	(12, 2, 'Hovercraft Splitboard 2015', 1, 779.00, 4, 'pictures\\jones-hovercraft-splitboard-2015-152.jpg'),
	(13, 1, 'Burton Antler Snowboard 2015', 1, 468.26, 6, 'pictures\\burton-antler-snowboard-2015-151-5.jpg');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;


-- Dumping structure for table webshop.rocker_type
DROP TABLE IF EXISTS `rocker_type`;
CREATE TABLE IF NOT EXISTS `rocker_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Dumping data for table webshop.rocker_type: ~6 rows (approximately)
DELETE FROM `rocker_type`;
/*!40000 ALTER TABLE `rocker_type` DISABLE KEYS */;
INSERT INTO `rocker_type` (`id`, `name`) VALUES
	(1, 'Camber'),
	(6, 'Camber/Rocker/Camber'),
	(3, 'Flat'),
	(2, 'Rocker'),
	(4, 'Rocker/Camber/Rocker'),
	(5, 'Rocker/Flat/Rocker');
/*!40000 ALTER TABLE `rocker_type` ENABLE KEYS */;


-- Dumping structure for table webshop.role
DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table webshop.role: ~0 rows (approximately)
DELETE FROM `role`;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`name`) VALUES
	('USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


-- Dumping structure for table webshop.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(25) NOT NULL,
  `last_name` varchar(25) NOT NULL,
  `email` varchar(70) NOT NULL,
  `passhash` varchar(32) NOT NULL,
  `avatar_path` varchar(100) NOT NULL,
  `role` varchar(45) NOT NULL DEFAULT 'USER',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `user_role_foreign_idx` (`role`),
  CONSTRAINT `user_role_foreign` FOREIGN KEY (`role`) REFERENCES `role` (`name`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- Dumping data for table webshop.user: ~6 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `first_name`, `last_name`, `email`, `passhash`, `avatar_path`, `role`) VALUES
	(10, 'Nikita', 'Doyun', 'doyun.nikita@gmail.com', 'AA25DBAA557DEEA578A7B40B66690CBB', 'avatar\\16d146a4-d73d-40fb-be6a-5ca68635740f.jpg', 'USER'),
	(11, 'sdf', 'asdf', 'asdf', 'asdf', 'asdf', 'USER'),
	(12, 'asdf', 'asdf', 'asdfasdf', 'asdf', 'asdf', 'USER'),
	(13, 'asdf', 'asdfad', 'safdasdf@asdf.com', 'AA25DBAA557DEEA578A7B40B66690CBB', 'images/default.jpg', 'USER'),
	(14, 'asdf', 'asdfasdf', 'asdfasdfsdf@sdf.com', 'AA25DBAA557DEEA578A7B40B66690CBB', 'images/default.jpg', 'USER'),
	(15, 'asdf', 'asdf', 'asdf@sf.com', 'AA25DBAA557DEEA578A7B40B66690CBB', 'avatar\\ac5a2d16-1935-4343-8143-94f089dcb7af.png', 'USER'),
	(16, 'da', 'sdf', 'asdf@sadf.com', 'AA25DBAA557DEEA578A7B40B66690CBB', '', 'USER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
