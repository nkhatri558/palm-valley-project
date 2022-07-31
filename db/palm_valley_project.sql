/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 10.1.10-MariaDB : Database - palm_valley
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`palm_valley` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `palm_valley`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_name` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `active` enum('0','1') DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  KEY `created_by` (`created_by`),
  KEY `modified_by` (`modified_by`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `account_ibfk_2` FOREIGN KEY (`modified_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `account` */

insert  into `account`(`account_id`,`account_name`,`contact`,`description`,`active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (4,'osama','0123-1233456','qerqewrqerqwer','0',8,'2019-07-17 21:56:13',8,'2019-07-17 22:07:16'),(5,'rashid','0313-8763444','asdfghjzxcvbn\n','1',9,'2019-07-17 22:02:13',8,'2019-07-17 22:02:47'),(6,'nand','0123-1231231','abc','1',9,'2019-10-18 16:18:13',9,'2019-10-18 16:18:13');

/*Table structure for table `booking` */

DROP TABLE IF EXISTS `booking`;

CREATE TABLE `booking` (
  `booking_id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_date` datetime DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `total` decimal(16,4) DEFAULT NULL,
  `active` enum('0','1') DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`booking_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `booking` */

/*Table structure for table `booking_details` */

DROP TABLE IF EXISTS `booking_details`;

CREATE TABLE `booking_details` (
  `booking_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_id` int(11) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `active` enum('0','1') DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`booking_detail_id`),
  KEY `booking_id` (`booking_id`),
  KEY `room_id` (`room_id`),
  CONSTRAINT `booking_details_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`),
  CONSTRAINT `booking_details_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `booking_details` */

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `active` enum('0','1') DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`category_id`),
  KEY `created_by` (`created_by`),
  KEY `modified_by` (`modified_by`),
  CONSTRAINT `category_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `category_ibfk_2` FOREIGN KEY (`modified_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `category` */

insert  into `category`(`category_id`,`category_name`,`description`,`active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (1,'Fruits','Apple, Mangoes ','1',8,'2019-07-18 12:31:10',8,'2019-07-18 12:54:45'),(2,'Fast Food','Broast, Zinger, Mayo Roll','0',8,'2019-07-18 12:57:27',8,'2019-07-18 12:58:06'),(3,'','','0',8,'2019-07-18 13:00:00',8,'2019-07-18 13:00:00'),(4,'Fast Food','Bar B Q','1',9,'2019-07-19 21:47:34',9,'2019-07-19 21:47:34'),(5,'test','ttest','0',9,'2019-07-20 12:45:13',9,'2019-07-20 12:45:22'),(6,'1','1','0',9,'2019-07-21 12:24:02',9,'2019-07-21 12:24:06');

/*Table structure for table `expense` */

DROP TABLE IF EXISTS `expense`;

CREATE TABLE `expense` (
  `expense_id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` decimal(16,4) DEFAULT NULL,
  `remarks` varchar(1000) DEFAULT NULL,
  `expense_date` datetime DEFAULT NULL,
  `active` enum('0','1') DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`expense_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `expense` */

/*Table structure for table `order_details` */

DROP TABLE IF EXISTS `order_details`;

CREATE TABLE `order_details` (
  `order_details_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` decimal(16,4) DEFAULT NULL,
  `active` enum('0','1') DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`order_details_id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  KEY `created_by` (`created_by`),
  KEY `modified_by` (`modified_by`),
  CONSTRAINT `order_details_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `order_details_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `order_details_ibfk_3` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_details_ibfk_4` FOREIGN KEY (`modified_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=latin1;

/*Data for the table `order_details` */

insert  into `order_details`(`order_details_id`,`order_id`,`product_id`,`quantity`,`price`,`active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (1,3,8,1,'100.0000','1',9,'2019-07-24 23:52:55',9,'2019-09-06 15:49:07'),(2,4,7,1,'12.0000','1',9,'2019-07-24 23:56:35',9,'2019-09-01 14:18:04'),(3,4,8,4,'100.0000','1',9,'2019-07-24 23:56:35',9,'2019-09-01 14:18:04'),(4,4,8,0,'100.0000','0',9,'2019-07-25 16:51:11',9,'2019-09-01 14:18:04'),(5,4,9,14,'100.0000','1',9,'2019-07-25 16:51:11',9,'2019-09-01 14:18:04'),(6,4,7,0,'12.0000','0',9,'2019-07-25 16:51:11',9,'2019-09-01 14:18:04'),(7,4,10,12,'8.0000','1',9,'2019-07-25 16:51:11',9,'2019-09-01 14:18:05'),(8,6,7,1,'12.0000','1',19,'2019-07-25 17:09:47',9,'2019-09-01 14:31:39'),(9,6,10,1,'8.0000','1',19,'2019-07-25 17:09:47',9,'2019-09-01 14:31:39'),(10,6,8,1,'100.0000','1',19,'2019-07-25 17:09:47',9,'2019-09-01 14:31:39'),(11,6,9,1,'100.0000','1',19,'2019-07-25 17:09:47',9,'2019-09-01 14:31:40'),(12,7,7,2,'12.0000','1',16,'2019-07-30 20:18:36',9,'2019-09-06 23:25:37'),(13,7,7,2,'12.0000','0',16,'2019-07-30 20:18:36',9,'2019-09-06 23:18:40'),(14,7,9,7,'100.0000','0',16,'2019-07-30 20:18:36',9,'2019-09-06 23:20:24'),(15,8,7,2,'12.0000','1',19,'2019-07-31 15:55:41',9,'2019-09-01 14:33:03'),(16,8,10,2,'8.0000','1',19,'2019-07-31 15:55:41',9,'2019-09-01 14:33:03'),(17,9,7,9,'12.0000','1',19,'2019-07-31 15:59:33',9,'2019-09-06 23:30:26'),(18,9,10,2,'8.0000','1',19,'2019-07-31 15:59:33',9,'2019-09-06 23:30:26'),(19,10,7,1,'12.0000','1',19,'2019-07-31 16:04:15',9,'2019-09-01 14:33:10'),(20,16,7,1,'12.0000','1',19,'2019-08-31 11:53:42',9,'2019-09-06 15:58:37'),(21,16,10,2,'8.0000','1',19,'2019-08-31 11:53:42',9,'2019-09-06 15:58:37'),(22,17,10,2,'8.0000','1',19,'2019-08-31 11:56:24',9,'2019-09-01 15:41:32'),(23,17,9,3,'100.0000','1',19,'2019-08-31 11:56:24',9,'2019-09-01 15:41:32'),(24,18,7,1,'12.0000','1',19,'2019-08-31 19:16:14',19,'2019-08-31 19:16:14'),(25,18,10,1,'8.0000','1',19,'2019-08-31 19:16:14',19,'2019-08-31 19:16:14'),(26,17,8,1,'100.0000','1',9,'2019-09-01 14:26:23',9,'2019-09-01 15:41:32'),(27,3,7,1,'12.0000','1',9,'2019-09-01 14:42:08',9,'2019-09-06 15:49:07'),(28,9,9,8,'100.0000','0',9,'2019-09-01 15:28:42',9,'2019-09-06 23:30:04'),(29,9,8,2,'100.0000','1',9,'2019-09-01 15:28:42',9,'2019-09-06 23:30:26'),(30,3,9,1,'100.0000','1',9,'2019-09-01 15:39:09',9,'2019-09-06 15:49:08'),(31,17,7,1,'12.0000','1',9,'2019-09-01 15:40:35',9,'2019-09-01 15:41:32'),(32,20,7,1,'12.0000','1',19,'2019-09-04 11:24:14',9,'2019-09-04 11:24:55'),(33,20,10,1,'8.0000','1',19,'2019-09-04 11:24:14',9,'2019-09-04 11:24:55'),(34,21,7,1,'12.0000','1',19,'2019-09-04 11:32:40',9,'2019-09-04 11:33:09'),(35,21,10,1,'8.0000','1',19,'2019-09-04 11:32:40',9,'2019-09-04 11:33:09'),(36,21,8,1,'100.0000','1',19,'2019-09-04 11:32:40',9,'2019-09-04 11:33:09'),(37,21,9,1,'100.0000','1',19,'2019-09-04 11:32:40',9,'2019-09-04 11:33:09'),(38,22,7,1,'12.0000','1',19,'2019-09-04 11:37:18',19,'2019-09-04 11:37:18'),(39,23,7,1,'12.0000','0',19,'2019-09-04 11:38:05',9,'2019-09-07 00:43:20'),(40,7,9,4,'100.0000','0',9,'2019-09-06 23:25:18',9,'2019-09-06 23:25:24'),(41,24,7,4,'12.0000','0',19,'2019-09-06 23:31:53',9,'2019-09-06 23:32:25'),(42,24,10,2,'8.0000','0',19,'2019-09-06 23:31:53',9,'2019-09-06 23:32:23'),(43,24,8,1,'100.0000','0',19,'2019-09-06 23:31:53',9,'2019-09-06 23:32:21'),(44,24,9,3,'100.0000','0',19,'2019-09-06 23:31:53',9,'2019-09-06 23:32:14'),(45,25,7,2,'12.0000','1',19,'2019-10-11 17:09:02',19,'2019-10-11 17:09:02'),(46,25,8,1,'100.0000','1',19,'2019-10-11 17:09:02',19,'2019-10-11 17:09:02'),(47,26,10,1,'8.0000','1',19,'2019-10-11 17:10:54',19,'2019-10-11 17:10:54'),(48,26,9,1,'100.0000','1',19,'2019-10-11 17:10:54',19,'2019-10-11 17:10:54'),(49,27,7,2,'12.0000','1',19,'2019-10-18 17:46:07',19,'2019-10-18 17:46:07'),(50,27,9,2,'100.0000','1',19,'2019-10-18 17:46:07',19,'2019-10-18 17:46:07'),(51,27,8,2,'100.0000','1',19,'2019-10-18 17:46:07',19,'2019-10-18 17:46:07'),(52,28,7,1,'12.0000','1',19,'2019-10-24 22:04:48',19,'2019-10-24 22:04:48'),(53,28,9,1,'100.0000','1',19,'2019-10-24 22:04:48',19,'2019-10-24 22:04:48'),(54,29,8,1,'100.0000','1',19,'2019-10-24 22:05:19',19,'2019-10-24 22:05:19'),(55,29,7,1,'12.0000','1',19,'2019-10-24 22:05:19',19,'2019-10-24 22:05:19');

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(255) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `order_date` timestamp NULL DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `grand_total` double(16,4) DEFAULT NULL,
  `amount_paid` double(16,4) DEFAULT NULL,
  `active` enum('0','1') DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `customer_id` (`customer_id`),
  KEY `account_id` (`account_id`),
  KEY `created_by` (`created_by`),
  KEY `modified_by` (`modified_by`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`),
  CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `orders_ibfk_4` FOREIGN KEY (`modified_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

/*Data for the table `orders` */

insert  into `orders`(`order_id`,`order_no`,`customer_id`,`order_date`,`account_id`,`status`,`grand_total`,`amount_paid`,`active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (1,'JWC-58',13,'2019-07-24 00:00:00',5,'unpaid',NULL,NULL,'0',9,'2019-07-24 23:51:07',9,'2019-07-24 23:51:07'),(2,'MUO-355',13,'2019-07-24 00:00:00',5,'unpaid',0.0000,0.0000,'0',9,'2019-07-24 23:52:15',9,'2019-09-01 14:26:56'),(3,'IGN-471',13,'2019-07-24 00:00:00',5,'paid',212.0000,251.0000,'0',9,'2019-07-24 23:52:55',9,'2019-09-06 15:49:08'),(4,'ZTE-352',13,'2019-07-24 00:00:00',5,'unpaid',1908.0000,1331.0400,'1',9,'2019-07-24 23:56:35',9,'2019-09-01 14:18:05'),(6,'TKB-895',19,'2019-07-25 00:00:00',5,'unpaid',220.0000,220.0000,'0',19,'2019-07-25 17:09:47',9,'2019-09-01 14:31:40'),(7,'ZJG-478',16,'2019-07-30 00:00:00',5,'paid',448.0000,900.0000,'1',16,'2019-07-30 20:18:36',9,'2019-09-06 23:25:37'),(8,'TCE-514',19,'2019-07-31 00:00:00',5,'paid',40.0000,50.0000,'1',19,'2019-07-31 15:55:41',9,'2019-09-01 14:33:03'),(9,'UXT-884',19,'2019-07-31 00:00:00',5,'unpaid',1448.0000,1030.0000,'1',19,'2019-07-31 15:59:33',9,'2019-09-06 23:30:26'),(10,'BMM-916',19,'2019-07-31 00:00:00',5,'paid',12.0000,20.0000,'1',19,'2019-07-31 16:04:15',9,'2019-09-01 14:33:10'),(11,'FQG-934',19,'2019-08-30 00:00:00',5,'unpaid',0.0000,0.0000,'0',19,'2019-08-30 19:38:52',9,'2019-09-06 15:57:36'),(12,'KIC-217',19,'2019-08-30 00:00:00',5,'unpaid',NULL,NULL,'0',19,'2019-08-30 19:39:03',19,'2019-08-30 19:39:03'),(13,'VIE-158',19,'2019-08-30 00:00:00',5,'unpaid',NULL,NULL,'0',19,'2019-08-30 19:39:05',19,'2019-08-30 19:39:05'),(14,'QHT-651',19,'2019-08-30 00:00:00',5,'unpaid',NULL,NULL,'0',19,'2019-08-30 19:39:11',19,'2019-08-30 19:39:11'),(15,'WJU-410',19,'2019-08-30 00:00:00',5,'unpaid',NULL,NULL,'0',19,'2019-08-30 19:39:11',19,'2019-08-30 19:39:11'),(16,'CBC-212',19,'2019-08-31 00:00:00',5,'paid',28.0000,34.0000,'1',19,'2019-08-31 11:53:42',9,'2019-09-06 15:58:37'),(17,'ZGV-822',19,'2019-08-31 00:00:00',5,'unpaid',328.0000,260.0000,'1',19,'2019-08-31 11:56:24',9,'2019-09-01 15:41:12'),(18,'LMG-737',19,'2019-08-31 00:00:00',5,'unpaid',NULL,NULL,'0',19,'2019-08-31 19:16:14',19,'2019-08-31 19:16:14'),(19,'LAM-35',19,'2019-09-04 00:00:00',5,'unpaid',NULL,NULL,'0',19,'2019-09-04 11:19:18',19,'2019-09-04 11:19:18'),(20,'RUH-201',19,'2019-09-04 00:00:00',5,'paid',20.0000,30.0000,'1',19,'2019-09-04 11:24:14',9,'2019-09-04 11:24:55'),(21,'WSH-903',19,'2019-09-04 00:00:00',5,'paid',220.0000,300.0000,'1',19,'2019-09-04 11:32:40',9,'2019-09-04 11:33:09'),(22,'FLL-96',19,'2019-09-04 00:00:00',5,'paid',12.0000,15.0000,'1',19,'2019-09-04 11:37:18',19,'2019-09-04 11:37:18'),(23,'VOH-545',19,'2019-09-04 00:00:00',5,'paid',12.0000,15.0000,'0',19,'2019-09-04 11:38:05',9,'2019-09-06 16:11:36'),(24,'MPH-226',19,'2019-09-06 00:00:00',5,'unpaid',740.0000,500.0000,'1',19,'2019-09-06 23:31:53',9,'2019-09-06 23:32:27'),(25,'QFN-193',19,'2019-10-11 00:00:00',5,'paid',124.0000,150.0000,'1',19,'2019-10-11 17:09:02',19,'2019-10-11 17:09:02'),(26,'CZD-294',19,'2019-10-11 00:00:00',5,'paid',108.0000,130.0000,'1',19,'2019-10-11 17:10:54',19,'2019-10-11 17:10:54'),(27,'HNL-316',19,'2019-10-18 00:00:00',5,'paid',424.0000,501.0000,'1',19,'2019-10-18 17:46:07',19,'2019-10-18 17:46:07'),(28,'KFR-615',19,'2019-10-24 00:00:00',5,'unpaid',112.0000,100.0000,'1',19,'2019-10-24 22:04:48',19,'2019-10-24 22:04:48'),(29,'ABQ-178',19,'2019-10-24 00:00:00',5,'unpaid',112.0000,100.0000,'1',19,'2019-10-24 22:05:19',19,'2019-10-24 22:05:19');

/*Table structure for table `package` */

DROP TABLE IF EXISTS `package`;

CREATE TABLE `package` (
  `package_id` int(11) NOT NULL AUTO_INCREMENT,
  `package_name` varchar(255) DEFAULT NULL,
  `price` double(11,3) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `active` enum('1','0') DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`package_id`),
  KEY `created_by` (`created_by`),
  KEY `modified_by` (`modified_by`),
  CONSTRAINT `package_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `package_ibfk_2` FOREIGN KEY (`modified_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `package` */

insert  into `package`(`package_id`,`package_name`,`price`,`count`,`active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (1,'Adult',500.000,1,'1',9,'2019-09-03 13:33:10',9,'2019-09-03 13:33:10'),(2,'Female',500.000,1,'1',9,'2019-09-03 16:46:17',9,'2019-09-03 16:46:17'),(3,'child',400.000,1,'0',9,'2019-09-03 16:46:54',9,'2019-09-03 16:47:04');

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `barcode` varchar(255) DEFAULT NULL,
  `price` decimal(16,4) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `active` enum('0','1') DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `category_id` (`category_id`),
  KEY `created_by` (`created_by`),
  KEY `modified_by` (`modified_by`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
  CONSTRAINT `product_ibfk_2` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `product_ibfk_3` FOREIGN KEY (`modified_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `product` */

insert  into `product`(`product_id`,`category_id`,`name`,`barcode`,`price`,`size`,`quantity`,`active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (1,1,'Apple','0123','1.0000','3',123,'0',8,'2019-07-19 19:44:23',9,'2019-07-20 12:32:13'),(2,1,'Banana','012312','1.0000','1',1,'0',8,'2019-07-19 19:52:35',8,'2019-07-19 20:16:13'),(3,4,'Chicken Tikka','123123','123.0000','250',12,'0',9,'2019-07-19 21:48:17',9,'2019-07-20 12:32:09'),(4,1,'Mango','JGD-810','1.0000','1',12,'0',9,'2019-07-20 12:31:57',9,'2019-07-21 14:50:40'),(5,4,'burgar','GLE-789','12.0000','1',1,'0',9,'2019-07-20 12:45:45',9,'2019-07-21 14:50:42'),(6,1,'Mango','ZNV-327','13.0000','1',12,'0',9,'2019-07-21 13:54:45',9,'2019-07-21 13:55:32'),(7,1,'mango','CGC-690','12.0000','1',64,'1',9,'2019-07-21 14:51:14',9,'2019-07-25 16:46:44'),(8,4,'Burger','USH-259','100.0000','1',67,'1',9,'2019-07-24 19:59:35',9,'2019-07-24 19:59:35'),(9,4,'Mayo Roll','PVC-254','100.0000','1',64,'1',9,'2019-07-25 16:40:09',9,'2019-07-25 16:40:09'),(10,1,'Banana','PMX-503','8.0000','1',76,'1',9,'2019-07-25 16:40:34',9,'2019-07-25 16:47:50');

/*Table structure for table `room` */

DROP TABLE IF EXISTS `room`;

CREATE TABLE `room` (
  `room_id` int(11) NOT NULL AUTO_INCREMENT,
  `room_type_id` int(11) DEFAULT NULL,
  `room_no` int(11) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `active` enum('0','1') DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`room_id`),
  KEY `room_type_id` (`room_type_id`),
  CONSTRAINT `room_ibfk_1` FOREIGN KEY (`room_type_id`) REFERENCES `room_type` (`room_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `room` */

/*Table structure for table `room_type` */

DROP TABLE IF EXISTS `room_type`;

CREATE TABLE `room_type` (
  `room_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `price` decimal(16,4) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `active` enum('0','1') DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`room_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `room_type` */

/*Table structure for table `service` */

DROP TABLE IF EXISTS `service`;

CREATE TABLE `service` (
  `service_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_name` varchar(255) DEFAULT NULL,
  `amount` decimal(16,4) DEFAULT NULL,
  `booking_id` int(11) DEFAULT NULL,
  `active` enum('0','1') DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`service_id`),
  KEY `booking_id` (`booking_id`),
  CONSTRAINT `service_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `service` */

/*Table structure for table `stock` */

DROP TABLE IF EXISTS `stock`;

CREATE TABLE `stock` (
  `stock_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `whole_sale_price` decimal(16,4) DEFAULT NULL,
  `retail_price` decimal(16,4) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `active` enum('0','1') DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`stock_id`),
  KEY `account_id` (`account_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `stock_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`),
  CONSTRAINT `stock_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `stock` */

insert  into `stock`(`stock_id`,`name`,`account_id`,`product_id`,`quantity`,`whole_sale_price`,`retail_price`,`date`,`active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (1,'a',5,4,1,'1.0000','1.0000','2019-07-20 00:00:00','0',9,'2019-07-20 22:57:51',9,'2019-07-21 14:50:10'),(2,'dsf',5,5,123,'123123.0000','123.0000','2019-08-20 00:00:00','0',9,'2019-07-20 23:16:33',9,'2019-07-20 23:16:49'),(3,'d',5,4,1,'1.0000','1.0000','2019-05-20 00:00:00','0',9,'2019-07-20 23:29:44',9,'2019-07-21 14:50:14'),(4,'asd',5,5,1,'1.0000','1.0000','2019-07-21 00:00:00','0',9,'2019-07-21 12:23:15',9,'2019-07-21 12:23:34'),(5,'a',5,4,2,'10.0000','10.0000','2019-07-21 00:00:00','0',9,'2019-07-21 13:52:00',9,'2019-07-21 14:44:44'),(6,'a',5,4,10,'10.0000','15.0000','2019-07-21 00:00:00','0',9,'2019-07-21 13:56:18',9,'2019-07-21 14:43:15'),(7,'mango',5,7,10,'10.0000','12.0000','2019-07-21 00:00:00','1',9,'2019-07-21 14:51:50',9,'2019-07-21 14:51:50'),(8,'mango',5,7,10,'10.0000','15.0000','2019-07-21 00:00:00','0',9,'2019-07-21 14:52:20',9,'2019-07-21 14:52:32');

/*Table structure for table `tax` */

DROP TABLE IF EXISTS `tax`;

CREATE TABLE `tax` (
  `tax_id` int(11) NOT NULL AUTO_INCREMENT,
  `percentage` int(11) DEFAULT NULL,
  `date_from` date DEFAULT NULL,
  `date_to` date DEFAULT NULL,
  `active` enum('0','1') DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`tax_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `tax` */

insert  into `tax`(`tax_id`,`percentage`,`date_from`,`date_to`,`active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (1,15,'2019-09-02','2019-09-03','1',9,'2019-09-02 17:33:19',9,'2019-09-02 17:33:19'),(2,20,'2019-09-02','2019-09-02','0',9,'2019-09-02 17:42:43',9,'2019-09-02 17:42:43');

/*Table structure for table `ticket` */

DROP TABLE IF EXISTS `ticket`;

CREATE TABLE `ticket` (
  `ticket_id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_no` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` enum('1','0') DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ticket_id`),
  KEY `created_by` (`created_by`),
  KEY `modified_by` (`modified_by`),
  CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`modified_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `ticket` */

insert  into `ticket`(`ticket_id`,`ticket_no`,`contact`,`date`,`active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (1,'ZME-872','0343-8333944','2019-09-05 22:05:37','1',9,'2019-09-05 22:06:40',9,'2019-09-05 22:06:40'),(2,'MLK-222','0343-8333944','2019-09-06 22:48:36','1',9,'2019-09-05 23:28:42',9,'2019-09-06 22:48:43'),(3,'MQR-410','1234-1234567','2019-09-06 22:07:51','0',9,'2019-09-06 22:06:04',9,'2019-09-06 22:07:51'),(4,'GRS-949','0300-0000000','2019-09-07 00:42:24','1',9,'2019-09-07 00:26:43',9,'2019-09-07 00:42:35'),(5,'WCC-641','0123-1234456','2019-10-04 18:50:36','1',9,'2019-10-04 18:49:42',9,'2019-10-04 18:50:59'),(6,'FGR-829','0343-8333944','2019-10-11 18:03:37','1',9,'2019-10-11 18:04:06',9,'2019-10-11 18:04:06'),(7,'PZU-504','0343-8333944','2019-10-11 18:23:02','1',9,'2019-10-11 18:23:29',9,'2019-10-11 18:23:29');

/*Table structure for table `ticket_details` */

DROP TABLE IF EXISTS `ticket_details`;

CREATE TABLE `ticket_details` (
  `ticket_details_id` int(11) NOT NULL AUTO_INCREMENT,
  `package_id` int(11) DEFAULT NULL,
  `ticket_id` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `active` enum('1','0') DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ticket_details_id`),
  KEY `package_id` (`package_id`),
  KEY `ticket_id` (`ticket_id`),
  KEY `created_by` (`created_by`),
  KEY `modified_by` (`modified_by`),
  CONSTRAINT `ticket_details_ibfk_1` FOREIGN KEY (`package_id`) REFERENCES `package` (`package_id`),
  CONSTRAINT `ticket_details_ibfk_2` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`ticket_id`),
  CONSTRAINT `ticket_details_ibfk_3` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `ticket_details_ibfk_4` FOREIGN KEY (`modified_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Data for the table `ticket_details` */

insert  into `ticket_details`(`ticket_details_id`,`package_id`,`ticket_id`,`quantity`,`active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (1,1,1,1,'1',9,'2019-09-05 22:06:40',9,'2019-09-05 22:06:40'),(2,2,1,1,'1',9,'2019-09-05 22:06:40',9,'2019-09-05 22:06:40'),(3,1,2,2,'1',9,'2019-09-05 23:28:43',9,'2019-09-05 23:28:43'),(4,2,2,2,'0',9,'2019-09-05 23:28:43',9,'2019-09-05 23:28:43'),(5,1,3,1,'1',9,'2019-09-06 22:06:04',9,'2019-09-06 22:06:04'),(6,1,4,1,'1',9,'2019-09-07 00:26:43',9,'2019-09-07 00:26:43'),(7,2,4,2,'1',9,'2019-09-07 00:26:43',9,'2019-09-07 00:26:43'),(8,1,5,1,'1',9,'2019-10-04 18:49:42',9,'2019-10-04 18:49:42'),(9,2,5,2,'0',9,'2019-10-04 18:49:42',9,'2019-10-04 18:50:49'),(10,1,6,1,'1',9,'2019-10-11 18:04:07',9,'2019-10-11 18:04:07'),(11,2,6,2,'1',9,'2019-10-11 18:04:07',9,'2019-10-11 18:04:07'),(12,2,7,2,'1',9,'2019-10-11 18:23:29',9,'2019-10-11 18:23:29'),(13,1,7,2,'1',9,'2019-10-11 18:23:29',9,'2019-10-11 18:23:29');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `nic` varchar(255) DEFAULT NULL,
  `user_type` enum('CUSTOMER','EMPLOYEE','USER') DEFAULT NULL,
  `active` enum('0','1') DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `created_by` (`created_by`),
  KEY `modified_by` (`modified_by`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `users_ibfk_2` FOREIGN KEY (`modified_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

/*Data for the table `users` */

insert  into `users`(`user_id`,`username`,`password`,`dob`,`contact`,`nic`,`user_type`,`active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (8,'nand','123','1999-02-09','0343-8333944','44107-8075717-9','USER','0',NULL,NULL,8,'2019-07-17 19:22:39'),(9,'khatri','123','2018-12-17','1233-1231231','12334-1231231-1','USER','1',8,'2019-07-17 19:22:13',8,'2019-07-19 18:56:26'),(10,'eidan','123','2019-07-19','0311-1231231','12345-1234566-1','USER','1',8,'2019-07-19 21:32:32',8,'2019-07-19 21:32:46'),(11,'eidann','123','2019-07-19','0311-1231231','12345-1234566-1','USER','1',9,'2019-07-20 12:33:40',9,'2019-07-21 12:22:22'),(12,'admin','admin','2019-07-20','0333-7586021','45104-5772934-9','USER','0',9,'2019-07-20 12:46:56',9,'2019-07-20 12:47:01'),(13,'123','','2019-07-21','0333-7586021','45104-5772934-9','CUSTOMER','1',9,'2019-07-21 12:22:53',9,'2019-07-21 12:22:53'),(14,'Rashid',NULL,'2019-07-25','0312-1234567','12345-1234567-1','CUSTOMER','1',9,'2019-07-25 15:57:01',9,'2019-07-25 15:57:01'),(15,'Vinod',NULL,'1997-07-08','0123-1234567','12345-1234567-1','CUSTOMER','1',9,'2019-07-25 16:03:37',9,'2019-07-25 16:03:37'),(16,'neel',NULL,'2019-07-25','0123-1234567','12345-1234567-1','CUSTOMER','1',9,'2019-07-25 16:20:23',9,'2019-07-25 16:20:23'),(17,'bilal',NULL,'2019-07-25','0123-1234567','12345-1234456-1','CUSTOMER','1',9,'2019-07-25 16:23:29',9,'2019-07-25 16:23:29'),(18,'zahoor',NULL,'2019-07-25','0123-1234567','12345-1234567-1','CUSTOMER','1',9,'2019-07-25 16:25:26',9,'2019-07-25 16:25:26'),(19,'Walk Away Customer','','2019-07-25','0000-0000000','00000-0000000-0','CUSTOMER','1',9,'2019-07-25 17:05:40',9,'2019-07-25 17:05:40');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
