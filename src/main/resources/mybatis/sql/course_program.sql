/*
SQLyog  v12.2.6 (64 bit)
MySQL - 5.7.19-log : Database - paper_program
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`paper_program` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `paper_program`;

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

insert  into `class`(`id`,`course_id`,`name`,`create_user`,`create_time`,`update_user`,`update_time`,`delete_flag`) values 
('testclass1','test1','19信计丰杰',NULL,NULL,'test','2019-05-11',0),
('testclass2','test1','19云计算1211',NULL,NULL,'test','2019-05-11',0);

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

insert  into `course`(`id`,`teacher_id`,`course_name`,`course_info`,`pre_course`,`course_plan`,`bibliography`,`courseware`,`create_user`,`create_time`,`update_user`,`update_time`,`delete_flag`) values 
('test1','test','C++语言',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),
('test2','test','Java语言',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),
('test3','test','数据结构与算法',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0);

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
  `example_answer` text,
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

insert  into `course_question`(`id`,`course_id`,`question_no`,`level`,`type`,`title`,`info`,`answer`,`example_answer`,`system_in`,`system_out`,`create_time`,`create_user`,`update_time`,`update_user`,`delete_flag`) values 
('5cd3d6b4b94dab0c7cb43d8d','test1',2,1,1,'最小的数','<p>从下面的选项中选择最小的数：</p><p>A.1</p><p>B.2</p><p>C.3</p><p>D.4</p>','A',NULL,NULL,NULL,'2019-05-09','','2019-05-14','test',0),
('5cd3d777b94dab3984dc555f','test1',3,1,2,'输出Hello World！','<p>从控制台中输出Hello World!</p>',NULL,NULL,'',NULL,'2019-05-09','',NULL,NULL,0),
('5cd6e2a3f58c215598246cb2','test1',4,2,2,'最大的数','<p>从控制台输出最大的数</p>',NULL,NULL,'1 2 3 4 5 11 231','231','2019-05-11','test','2019-05-14','test',0);

/*Table structure for table `grade` */

DROP TABLE IF EXISTS `grade`;

CREATE TABLE `grade` (
  `id` varchar(24) NOT NULL,
  `student_id` varchar(24) NOT NULL,
  `operation_id` varchar(24) NOT NULL,
  `answered` varchar(300) DEFAULT NULL,
  `grade` int(11) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0-未开放查询；1-开放查询',
  `create_time` date DEFAULT NULL,
  `create_user` varchar(24) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `update_user` varchar(24) DEFAULT NULL,
  `delete_flag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`,`operation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `grade` */

insert  into `grade`(`id`,`student_id`,`operation_id`,`answered`,`grade`,`status`,`create_time`,`create_user`,`update_time`,`update_user`,`delete_flag`) values 
('5cdcff9b79034b12bcee874b','5cdacabaf58c21566c1a78ff','5cdbecc3f58c2117048a975f','5cd3d6b4b94dab0c7cb43d8d,10',NULL,0,NULL,NULL,NULL,NULL,0);

/*Table structure for table `operation` */

DROP TABLE IF EXISTS `operation`;

CREATE TABLE `operation` (
  `id` varchar(24) NOT NULL,
  `course_id` varchar(24) NOT NULL,
  `class_id` varchar(24) DEFAULT NULL,
  `title` varchar(30) DEFAULT NULL,
  `finished_condition` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0-已修改完成完成；1-未修改完成',
  `questions` varchar(100) DEFAULT NULL,
  `start_time` date DEFAULT NULL,
  `end_time` date DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1' COMMENT '0-未发布；1-发布',
  `create_user` varchar(24) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `update_user` varchar(24) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `delete_flag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `operation` */

insert  into `operation`(`id`,`course_id`,`class_id`,`title`,`finished_condition`,`questions`,`start_time`,`end_time`,`status`,`create_user`,`create_time`,`update_user`,`update_time`,`delete_flag`) values 
('5cdbecc3f58c2117048a975f','test1','testclass1','测试布置作业',1,'5cd3d6b4b94dab0c7cb43d8d,1,10;5cd3d777b94dab3984dc555f,2,10;5cd6e2a3f58c215598246cb2,3,10','2019-05-15','2019-05-18',1,'test','2019-05-15',NULL,NULL,0),
('operationTest1','test1',NULL,'测试作业',1,NULL,'2019-05-12','2019-05-18',0,NULL,NULL,'test','2019-05-15',1);

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

/*Table structure for table `program_answer` */

DROP TABLE IF EXISTS `program_answer`;

CREATE TABLE `program_answer` (
  `id` varchar(24) NOT NULL,
  `question_id` varchar(24) NOT NULL,
  `system_in` text COMMENT '一个问题对应的输入',
  `system_out` text COMMENT '问题对应的输出',
  `create_user` varchar(20) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `update_user` varchar(24) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `delete_flag` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `program_answer` */

insert  into `program_answer`(`id`,`question_id`,`system_in`,`system_out`,`create_user`,`create_time`,`update_user`,`update_time`,`delete_flag`) values 
('111','11111',NULL,'Hello World1\r\n',NULL,NULL,NULL,NULL,NULL),
('1110','111110',NULL,'Hello World10',NULL,NULL,NULL,NULL,NULL),
('1111','111111',NULL,'Hello World11',NULL,NULL,NULL,NULL,NULL),
('1112','111112',NULL,'Hello World12',NULL,NULL,NULL,NULL,NULL),
('1113','111113',NULL,'Hello World13',NULL,NULL,NULL,NULL,NULL),
('1114','111114',NULL,'Hello World14',NULL,NULL,NULL,NULL,NULL),
('1115','111115',NULL,'Hello World15',NULL,NULL,NULL,NULL,NULL),
('112','11112',NULL,'Hello World2',NULL,NULL,NULL,NULL,NULL),
('113','11113',NULL,'Hello World3',NULL,NULL,NULL,NULL,NULL),
('114','11114',NULL,'Hello World4',NULL,NULL,NULL,NULL,NULL),
('116','11116',NULL,'Hello World6',NULL,NULL,NULL,NULL,NULL),
('117','11117',NULL,'Hello World7',NULL,NULL,NULL,NULL,NULL),
('118','11118',NULL,'Hello World8',NULL,NULL,NULL,NULL,NULL),
('119','11119',NULL,'Hello World9',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `program_questions` */

DROP TABLE IF EXISTS `program_questions`;

CREATE TABLE `program_questions` (
  `id` varchar(24) NOT NULL,
  `title` varchar(100) NOT NULL,
  `question_no` int(11) NOT NULL,
  `info` text,
  `level` int(11) NOT NULL COMMENT '1-简单；2-中等；3-困难',
  `total_passed` int(11) DEFAULT NULL,
  `total_tried` int(11) DEFAULT NULL,
  `pass_rate` decimal(10,2) DEFAULT NULL,
  `create_user` varchar(24) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `update_user` varchar(24) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `delete_flag` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `program_questions` */

insert  into `program_questions`(`id`,`title`,`question_no`,`info`,`level`,`total_passed`,`total_tried`,`pass_rate`,`create_user`,`create_time`,`update_user`,`update_time`,`delete_flag`) values 
('11111','Hello World1',1,'<p>在控制台输出Hello World1</p>\r\n<p>例如</p>\r\nHello world1',1,10,25,0.40,NULL,NULL,NULL,NULL,NULL),
('111110','Hello World10',10,'在控制台输出Hello World10',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL),
('111111','Hello World11',11,'在控制台输出Hello World11',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL),
('111112','Hello World12',12,'在控制台输出Hello World12',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL),
('111113','Hello World13',13,'在控制台输出Hello World13',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL),
('111114','Hello World14',14,'在控制台输出Hello World14',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL),
('111115','Hello World15',15,'在控制台输出Hello World15',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL),
('11112','Hello World2',2,'在控制台输出Hello World2',2,0,0,NULL,NULL,NULL,NULL,NULL,NULL),
('11113','Hello World3',3,'在控制台输出Hello World3',3,0,0,NULL,NULL,NULL,NULL,NULL,NULL),
('11114','Hello World4',4,'在控制台输出Hello World4',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL),
('11116','Hello World6',6,'在控制台输出Hello World6',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL),
('11117','Hello World7',7,'在控制台输出Hello World7',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL),
('11118','Hello World8',8,'在控制台输出Hello World8',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL),
('11119','Hello World9',9,'在控制台输出Hello World9',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `id` varchar(24) NOT NULL,
  `class_id` varchar(24) NOT NULL,
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

insert  into `student`(`id`,`class_id`,`name`,`email`,`password`,`student_code`,`info`,`create_user`,`create_time`,`update_user`,`update_time`,`delete_flag`) values 
('12','testclass1','丰杰12','11113@qq.com','123456','20112',NULL,NULL,NULL,NULL,NULL,0),
('2','testclass1','丰杰2','1113@qq.com','123456','2012',NULL,NULL,NULL,NULL,NULL,0),
('3','testclass1','丰杰3','1114@qq.com','123456','2013',NULL,NULL,NULL,NULL,NULL,0),
('4','testclass1','丰杰4','1115@qq.com','123456','2014',NULL,NULL,NULL,NULL,NULL,0),
('5','testclass1','丰杰5','1116@qq.com','123456','2015',NULL,NULL,NULL,NULL,NULL,0),
('5cdacabaf58c21566c1a78ff','testclass1','丰杰','18866856479@126.com','EFG@AB','201511011032',NULL,'test','2019-05-14',NULL,NULL,0),
('5cdacabaf58c21566c1a7900','testclass1','张三','11212@qq.com','FGEFGEGE','202020202020',NULL,'test','2019-05-14',NULL,NULL,0),
('6','testclass1','丰杰6','1117@qq.com','123456','2016',NULL,NULL,NULL,NULL,NULL,0),
('7','testclass1','丰杰7','1118@qq.com','123456','2017',NULL,NULL,NULL,NULL,NULL,0),
('8','testclass1','丰杰8','1119@qq.com','123456','2018',NULL,NULL,NULL,NULL,NULL,0),
('9','testclass1','丰杰9','11110@qq.com','123456','2019',NULL,NULL,NULL,NULL,NULL,0);

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

insert  into `teacher`(`id`,`username`,`email`,`password`,`info`,`create_user`,`create_time`,`update_user`,`update_time`,`delete_flag`) values 
('test','丰杰','18866856479@126.com','EFG@AB',NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(24) NOT NULL,
  `username` varchar(20) NOT NULL,
  `account` varchar(20) DEFAULT NULL,
  `password` varchar(40) NOT NULL,
  `email` varchar(40) NOT NULL,
  `info` varchar(100) DEFAULT NULL,
  `total_program` int(11) DEFAULT NULL COMMENT '用户做题的总数量',
  `total_correct_rate` decimal(10,0) DEFAULT NULL COMMENT '用户提交的正确率',
  `week_total_program` int(11) DEFAULT NULL COMMENT '本周用户做题的总数量',
  `week_correct_rate` decimal(10,0) DEFAULT NULL COMMENT '本周用户提交的正确率',
  `create_time` date DEFAULT NULL,
  `create_user` varchar(20) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `update_user` varchar(20) DEFAULT NULL,
  `delete_flag` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`account`,`password`,`email`,`info`,`total_program`,`total_correct_rate`,`week_total_program`,`week_correct_rate`,`create_time`,`create_user`,`update_time`,`update_user`,`delete_flag`) values 
('5cb4327f6c5cf14c345e56ed','丰杰',NULL,'EFG@AB','18866856479@126.com','这是一个用来测试的用户',NULL,NULL,NULL,NULL,NULL,NULL,'2019-05-04','丰杰',NULL),
('test','测试',NULL,'text','text@text.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
('test1','test1',NULL,'text1','text1@text.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
('test2','test2',NULL,'text2','text2@text.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
('test3','test3',NULL,'text3','text3@text.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
('test4','test4',NULL,'text4','text4@text.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
