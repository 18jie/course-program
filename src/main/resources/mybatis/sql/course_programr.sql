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

/*Table structure for table `course_question` */

DROP TABLE IF EXISTS `course_question`;

CREATE TABLE `course_question` (
  `id` varchar(24) NOT NULL,
  `course_id` varchar(24) NOT NULL,
  `question_no` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '题目类型。1-选择题；2-编程题',
  `title` varchar(30) DEFAULT NULL,
  `info` text,
  `answer` varchar(10) DEFAULT NULL,
  `system_in` text,
  `system_out` text,
  `create_time` date DEFAULT NULL,
  `create_user` varchar(24) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `update_user` varchar(24) DEFAULT NULL,
  `delete_flag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `course_question` */

insert  into `course_question`(`id`,`course_id`,`question_no`,`level`,`type`,`title`,`info`,`answer`,`system_in`,`system_out`,`create_time`,`create_user`,`update_time`,`update_user`,`delete_flag`) values ('5cd3d6b4b94dab0c7cb43d8d','test1',2,1,1,'最小的数','<p>从下面的选项中选择最小的数：</p><p>A.1</p><p>B.2</p><p>C.3</p><p>D.4</p>','A',NULL,NULL,'2019-05-09','',NULL,NULL,0),('5cd3d777b94dab3984dc555f','test1',3,1,2,'输出Hello World！','<p>从控制台中输出Hello World!</p>',NULL,'',NULL,'2019-05-09','',NULL,NULL,0);

/*Table structure for table `operation` */

DROP TABLE IF EXISTS `operation`;

CREATE TABLE `operation` (
  `id` varchar(24) NOT NULL,
  `course_id` varchar(24) NOT NULL,
  `title` varchar(30) DEFAULT NULL,
  `finished_condition` tinyint(1) DEFAULT NULL COMMENT '0-已修改完成完成；1-未修改完成',
  `questions` varchar(100) DEFAULT NULL,
  `start_time` date DEFAULT NULL,
  `end_time` date DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0' COMMENT '0-未发布；1-发布',
  `create_user` varchar(24) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `update_user` varchar(24) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `delete_flag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `operation` */

/*Table structure for table `operation_grade` */

DROP TABLE IF EXISTS `operation_grade`;

CREATE TABLE `operation_grade` (
  `id` varchar(24) NOT NULL,
  `operation_id` varchar(24) NOT NULL,
  `student_id` varchar(24) NOT NULL,
  `grade` int(11) DEFAULT NULL,
  `create_user` varchar(24) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `update_user` varchar(24) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `delete_flag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `operation_grade` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
