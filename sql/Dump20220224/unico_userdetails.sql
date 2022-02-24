CREATE DATABASE  IF NOT EXISTS `unico` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `unico`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: unico
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `userdetails`
--

DROP TABLE IF EXISTS `userdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userdetails` (
  `userDetailsId` int unsigned NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `serviceProviderName` varchar(45) DEFAULT NULL,
  `addressId` int unsigned NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `mobileNumber` varchar(45) NOT NULL,
  `roleName` varchar(125) NOT NULL DEFAULT 'ROLE_CUSTOMER',
  `isAdmin` tinyint NOT NULL DEFAULT '0',
  `isCustomer` tinyint NOT NULL DEFAULT '1',
  `isEmployee` tinyint NOT NULL DEFAULT '0',
  `isServiceProvider` tinyint NOT NULL DEFAULT '0',
  `isAuth` tinyint NOT NULL DEFAULT '0',
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  `resetPasswordToken` varchar(255) DEFAULT NULL,
  `resetPasswordTokenTime` datetime DEFAULT NULL,
  PRIMARY KEY (`userDetailsId`),
  UNIQUE KEY `userId_UNIQUE` (`userDetailsId`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `mobileNumber_UNIQUE` (`mobileNumber`),
  KEY `updatedBy_idx` (`createdBy`,`updatedBy`),
  KEY `updatedBy_idx1` (`updatedBy`),
  KEY `userAddress_idx` (`addressId`),
  CONSTRAINT `createdBy` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `updatedBy` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `userAddress` FOREIGN KEY (`addressId`) REFERENCES `address` (`addressId`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userdetails`
--

LOCK TABLES `userdetails` WRITE;
/*!40000 ALTER TABLE `userdetails` DISABLE KEYS */;
INSERT INTO `userdetails` VALUES (1,NULL,NULL,'Shubham',1,'shubhampatel9395@gmail.com','$2a$10$PE8CMDR3dkFM1LTvJaLCFuBCVVuQzP/Iwt3McP0lsSeZ/HiG2W2Cq','8735889977','ROLE_SERVICEPROVIDER',0,0,0,1,1,'2022-02-24 15:18:11','2022-02-24 15:21:29',1,NULL,NULL,NULL,NULL),(2,'Admin','Patel',NULL,2,'admin@gmail.com','$2a$10$hI8JCsMBFXhLXsIbUV79geyqi.bjATH7jz.2oMQ4lZi/PJvZBuAcy','9913801105','ROLE_ADMIN',1,0,0,0,1,'2022-02-24 15:19:33','2022-02-24 15:20:11',1,NULL,NULL,NULL,NULL),(3,'Chandni','Patel',NULL,3,'chandni@gmail.com','$2a$10$Cl99GIQ0x5plAZDXfFRtNufgg0pEcxFCUl5wYIEi92OoqnIZLmIZ2','8866616641','ROLE_CUSTOMER',0,1,0,0,1,'2022-02-24 15:24:09','2022-02-24 15:24:09',1,NULL,NULL,NULL,NULL),(4,NULL,NULL,'Mamta',4,'mamta@gmail.com','$2a$10$i4wfrn2iycWr0Z2KIcACkeR/LvjDyePMnRAZrFGfM1NaCTMs4ZYau','8401245442','ROLE_SERVICEPROVIDER',0,0,0,1,1,'2022-02-24 15:29:03','2022-02-24 18:20:55',1,NULL,NULL,NULL,NULL),(5,'Kartik','Shah',NULL,5,'kartik@gmail.com','$2a$10$JdlFAxFeIy5D.VZGCQ5PDOug0VbUQsj3qNpE/V9zk6QW/sdQerZEa','7890909090','ROLE_CUSTOMER',0,1,0,0,1,'2022-02-24 15:36:38','2022-02-24 15:36:38',1,NULL,NULL,NULL,NULL),(6,'Priyam','Patel',NULL,6,'priyam@gmail.com','$2a$10$M93wWHwe6mz3ahwZYiqq2e0y4gQdS2w5xQtmI3nF3TIAx/HXppNZm','7676765666','ROLE_CUSTOMER',0,1,0,0,1,'2022-02-24 15:42:36','2022-02-24 15:42:36',1,NULL,NULL,NULL,NULL),(8,'Lalit','Patel',NULL,8,'lalit@gmail.com','$2a$10$MXEvk2petMBnyBvqJchzNuGki3Fbd/TCYzF4TXu5.siyxQ1pEtoya','9876777777','ROLE_EVENTORGANIZER',0,0,1,0,1,'2022-02-24 15:45:53','2022-02-24 15:45:53',1,NULL,NULL,NULL,NULL),(9,'Teena','Patel',NULL,9,'teena@gmail.com','$2a$10$GJWWEqNAqpfjvOYbfbO2e.6c4CMgJTVytf3GNktZLpmnj/L1uC.06','9767777777','ROLE_EMPLOYEE',0,0,1,0,1,'2022-02-24 15:47:56','2022-02-24 15:47:56',1,NULL,NULL,NULL,NULL),(10,'Yatharth','Patel',NULL,10,'yatharth@gmail.com','$2a$10$6UQAGTWnw3HqI2hmidmSbex9mxhZDAOCTzm1hWEF6loiY6yvsuI66','9345678889','ROLE_EVENTORGANIZER',0,0,1,0,1,'2022-02-24 15:49:40','2022-02-24 15:49:40',1,NULL,NULL,NULL,NULL),(11,NULL,NULL,'Pranshi',11,'pranshi@gmail.com','$2a$10$Evik6R/Yjn1zluO9G3GHau.sswsPMtBbDXmq1Px7I.XPtVxfGz24K','9089000000','ROLE_SERVICEPROVIDER',0,0,0,1,1,'2022-02-24 15:51:54','2022-02-24 18:20:58',1,NULL,NULL,NULL,NULL),(12,NULL,NULL,'Teerth',12,'teerth@gmail.com','$2a$10$vGfihXVDeXRftZtRlmVC3uRAQPFg/OCTM0bIJL3LLDeIRzD/3uBam','9567788888','ROLE_SERVICEPROVIDER',0,0,0,1,1,'2022-02-24 15:52:56','2022-02-24 18:21:16',1,NULL,NULL,NULL,NULL),(13,NULL,NULL,'Riddhi',13,'riddhi@gmail.com','$2a$10$kUzz1sy5n1O9JB6ELsCAhuV0qI5mj7FXdDHZ3kDig7RWocEKdZmpK','8989898989','ROLE_SERVICEPROVIDER',0,0,0,1,1,'2022-02-24 15:57:49','2022-02-24 18:21:04',1,NULL,NULL,NULL,NULL),(14,NULL,NULL,'Yug Joshi',14,'yug@gmail.com','$2a$10$SXCKrlf2k/uNTutq9HbbGu0ldyRASagpVpHVW0Y2qUyaMnrWNfMii','9676777777','ROLE_SERVICEPROVIDER',0,0,0,1,1,'2022-02-24 15:59:01','2022-02-24 18:21:20',1,NULL,NULL,NULL,NULL),(15,NULL,NULL,'Hemant',15,'hemant@gmail.com','$2a$10$q.4eEcMS26suaiaIpj/N..2GTHLoMPRK27dEqvyHcGZ83r5l8phD2','9765678899','ROLE_SERVICEPROVIDER',0,0,0,1,1,'2022-02-24 16:00:23','2022-02-24 18:20:51',1,NULL,NULL,NULL,NULL),(16,NULL,NULL,'Smith Patil',16,'smith@gmail.com','$2a$10$Dz.vZEPQVKKyn5lX4kdUNet82isKQfZqVZi6LgYtfSbeDYSayZTp2','9080808080','ROLE_SERVICEPROVIDER',0,0,0,1,1,'2022-02-24 16:01:49','2022-02-24 18:21:07',1,NULL,NULL,NULL,NULL),(17,'Bharvi','Bhatt',NULL,19,'bharvi@gmail.com','$2a$10$ti8tndpb.8iyxDDz4lA/wehSGEvskQX6qmglS/Q/rWtUfuGpKV.ga','7898564512','ROLE_EMPLOYEE',0,0,1,0,1,'2022-02-24 16:32:45','2022-02-24 16:32:45',1,NULL,NULL,NULL,NULL),(18,'Riya','Patel',NULL,20,'riya@gmail.com','$2a$10$foJWX4hRjDKuQHOGeuPnK.nvsFdS8a7MDZc66dl9STwGtNMW6aFiG','8094051015','ROLE_EMPLOYEE',0,0,1,0,1,'2022-02-24 16:34:02','2022-02-24 16:34:02',1,NULL,NULL,NULL,NULL),(19,'Darshan','Parmar',NULL,21,'darshan@gmail.com','$2a$10$pM3axPoAOSL36nCVilvLA.WJHAvnlKIXzem4Ti7jGSLBBkNM9ptTK','5051051415','ROLE_EMPLOYEE',0,0,1,0,1,'2022-02-24 16:35:13','2022-02-24 16:35:13',1,NULL,NULL,NULL,NULL),(20,'Arkan','Mansuri',NULL,22,'arkan@gmail.com','$2a$10$fknw2P8XTVq04fA4DHzqFuR6Krt4ZKuYDYoJhIbTHH6yIeE2Hs/hy','4066620444','ROLE_EMPLOYEE',0,0,1,0,1,'2022-02-24 16:47:54','2022-02-24 16:47:54',1,NULL,NULL,NULL,NULL),(21,'Kush','Patel',NULL,23,'kush@gmail.com','$2a$10$YdW37JqrhtX3BI1hMa.D5OhVbDfZ3RuyI0z3qmqkLSmV.YpLmvyTy','5054540566','ROLE_EMPLOYEE',0,0,1,0,1,'2022-02-24 16:48:57','2022-02-24 16:48:57',1,NULL,NULL,NULL,NULL),(22,'Harsh','Tanna',NULL,24,'harshtanna@gmail.com','$2a$10$lmATAWOHmo43L0tdBcd6duXFWQ87MkKUVysN.yTCoj/IkbSNExpDK','0404504670','ROLE_EMPLOYEE',0,0,1,0,1,'2022-02-24 16:50:37','2022-02-24 16:50:37',1,NULL,NULL,NULL,NULL),(23,'Harsh','Patel',NULL,25,'harshpatel@gmail.com','$2a$10$qI0hIwlIc58SbiRqs3DZGuvHRU6VsYgii1OO.jho8cZcYKsNVur7e','4084980000','ROLE_EMPLOYEE',0,0,1,0,1,'2022-02-24 16:52:07','2022-02-24 16:52:07',1,NULL,NULL,NULL,NULL),(24,'Dev','Patel',NULL,26,'dev@gmail.com','$2a$10$NEB0DcH10WIbqWcGVoNOu.migExeDzviqdi9oO8Uob88rqn7q29a6','4596780080','ROLE_EMPLOYEE',0,0,1,0,1,'2022-02-24 16:53:25','2022-02-24 16:53:25',1,NULL,NULL,NULL,NULL),(25,'Darshan','Dabhi',NULL,27,'darshandabhi@gmail.com','$2a$10$WWVXKVTxKwicWv0FCbwBhOcJREWtpqli6FtcXPmh51TAuu/uOXjaS','8978009870','ROLE_EMPLOYEE',0,0,1,0,1,'2022-02-24 16:54:38','2022-02-24 16:54:38',1,NULL,NULL,NULL,NULL),(26,'Henil','Patel',NULL,28,'henil@gmail.com','$2a$10$4Nq2AlDtxRSk.Bh1qrcYDeEd/0elNpYYqIg2pcq5cCliYKubesdfu','7890740102','ROLE_EMPLOYEE',0,0,1,0,1,'2022-02-24 16:57:10','2022-02-24 16:57:10',1,NULL,NULL,NULL,NULL),(27,'Hardik','Solanki',NULL,29,'hardik@gmail.com','$2a$10$u5J1CzVm2WEwFiMT6LPAueuvPSAwuO3hbcENEdoguCGzD5Ym0FRR2','4898091220','ROLE_EMPLOYEE',0,0,1,0,1,'2022-02-24 16:58:08','2022-02-24 16:58:08',1,NULL,NULL,NULL,NULL),(28,'Nisarg','Bhavsar',NULL,30,'nisargbhavsar@gmail.com','$2a$10$XIdFW16LPkwcllfOlou3Seepi/YAcXMXuQ0ISoTjQUlSzagk3JGvO','4055457800','ROLE_EMPLOYEE',0,0,1,0,1,'2022-02-24 16:59:13','2022-02-24 16:59:13',1,NULL,NULL,NULL,NULL),(29,'Nisarg','Thakkar',NULL,31,'nisargthakkar@gmail.com','$2a$10$q/DYE9eAMPErIiicntJIrOECN9SdlciIgt6WSyb7rmRf0Lux0EJAe','8405106540','ROLE_EMPLOYEE',0,0,1,0,1,'2022-02-24 17:00:16','2022-02-24 17:00:16',1,NULL,NULL,NULL,NULL),(30,'Franie','Shah',NULL,32,'franie@gmail.com','$2a$10$p0vKRmpWn1bgxJmIdlS5HO3LGHU7H70kRSyMDUsJGDVuWnLhrtyRq','8908965103','ROLE_EMPLOYEE',0,0,1,0,1,'2022-02-24 17:01:13','2022-02-24 17:01:13',1,NULL,NULL,NULL,NULL),(31,'Sahil','Jani',NULL,33,'sahil@gmail.com','$2a$10$Le9NlO9W3Fdbs83/DdDh8.v54fJ8b4E2Uwz4Kp0QIijo4/hVJrHcC','0987005455','ROLE_EMPLOYEE',0,0,1,0,1,'2022-02-24 17:02:11','2022-02-24 17:02:11',1,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `userdetails` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-24 19:51:54