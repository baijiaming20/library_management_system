/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.11 : Database - library_management_system
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`library_management_system` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `library_management_system`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_pwd` varchar(20) DEFAULT NULL,
  `admin_email` varchar(320) DEFAULT NULL,
  PRIMARY KEY (`admin_id`,`admin_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`admin_id`,`admin_name`,`admin_pwd`,`admin_email`) values 
(1,'admin','123456','test@123.com'),
(2,'root','123456',NULL);

/*Table structure for table `book` */

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(30) DEFAULT NULL,
  `book_author` varchar(20) DEFAULT NULL,
  `book_publish` varchar(11) DEFAULT NULL,
  `book_type` int(20) DEFAULT NULL,
  `book_price` double DEFAULT NULL,
  `book_introduction` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`book_id`),
  KEY `book_type` (`book_type`),
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`book_type`) REFERENCES `book_type` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `book` */

insert  into `book`(`book_id`,`book_name`,`book_author`,`book_publish`,`book_type`,`book_price`,`book_introduction`) values 
(1,'java编程思想','埃克尔','机械工业',1,50.123,'无'),
(3,'java编程思想','埃克尔','机械工业',1,50.123,'无'),
(4,'java编程思想','埃克尔','机械工业',1,50.123,'无'),
(5,'C++ Primer Plus','普拉塔','人民邮电出版社',1,72,'无'),
(6,'C++ Primer Plus','普拉塔','人民邮电出版社',1,72,'无'),
(7,'新概念英语','外语教学与研究出版社','亚历山大',6,40.5,'无'),
(8,'纯粹理性批判','康德','人民出版社',7,100,'无'),
(9,'毛泽东选集','毛泽东','人民出版社',7,81,'无');

/*Table structure for table `book_type` */

DROP TABLE IF EXISTS `book_type`;

CREATE TABLE `book_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `book_type` */

insert  into `book_type`(`type_id`,`type_name`) values 
(1,'计算机'),
(6,'英语'),
(7,'哲学');

/*Table structure for table `borrow` */

DROP TABLE IF EXISTS `borrow`;

CREATE TABLE `borrow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `book_id` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `borrow_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `borrow_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

/*Data for the table `borrow` */

insert  into `borrow`(`id`,`user_id`,`book_id`,`date`,`return_date`) values 
(21,3,1,'2021-10-10','2021-11-10'),
(25,3,3,'2021-10-10','2021-11-10'),
(26,1,4,'2021-10-10','2021-11-10'),
(28,1,5,'2021-10-10','2021-11-10'),
(29,2,6,'2021-10-10','2021-11-10');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_pwd` varchar(30) DEFAULT NULL,
  `user_email` varchar(320) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`user_id`,`user_name`,`user_pwd`,`user_email`) values 
(1,'张三','123456','123@123.com'),
(2,'李四','123123',NULL),
(3,'admin','123456','');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
