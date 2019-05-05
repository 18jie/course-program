/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.22-log : Database - course_program
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
USE `course_program`;

/*Table structure for table `class` */

DROP TABLE IF EXISTS `class`;

CREATE TABLE `class` (
  `id` varchar(24) NOT NULL,
  `course_id` varchar(24) NOT NULL COMMENT '指向课程',
  `name` varchar(30) NOT NULL,
  `create_user` varchar(24) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `update_user` varchar(24) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `delete_flag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `class` */

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `id` varchar(24) NOT NULL,
  `teacher_id` varchar(24) DEFAULT NULL,
  `course_name` varchar(50) NOT NULL,
  `course_info` varchar(200) DEFAULT NULL,
  `pre_course` varchar(200) DEFAULT NULL,
  `course_plan` varchar(200) DEFAULT NULL,
  `bibliography` varchar(200) DEFAULT NULL COMMENT '参考书目',
  `courseware` varchar(24) DEFAULT NULL COMMENT '课件地址id',
  `create_user` varchar(24) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `update_user` varchar(24) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `delete_flag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `course` */

insert  into `course`(`id`,`teacher_id`,`course_name`,`course_info`,`pre_course`,`course_plan`,`bibliography`,`courseware`,`create_user`,`create_time`,`update_user`,`update_time`,`delete_flag`) values ('test1','test','C++语言',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),('test2','test','Java语言',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),('test3','test','数据结构与算法',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0);

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `id` varchar(24) NOT NULL,
  `class_id` varchar(24) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `student_code` varchar(24) DEFAULT NULL,
  `info` varchar(100) DEFAULT NULL,
  `create_user` varchar(24) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `update_user` varchar(24) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `delete_flag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `student` */

/*Table structure for table `teacher` */

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE `teacher` (
  `id` varchar(24) NOT NULL,
  `username` varchar(20) NOT NULL,
  `email` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `info` varchar(30) DEFAULT NULL,
  `create_user` varchar(24) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `update_user` varchar(24) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `delete_flag` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `teacher` */

insert  into `teacher`(`id`,`username`,`email`,`password`,`info`,`create_user`,`create_time`,`update_user`,`update_time`,`delete_flag`) values ('test','丰杰','18866856479@126.com','EFG@AB',NULL,NULL,NULL,NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
