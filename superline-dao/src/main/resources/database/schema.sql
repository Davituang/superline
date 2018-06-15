/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.6.38 : Database - redio
*********************************************************************
*/

CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL,
  `telephone` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;
