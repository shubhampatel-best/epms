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
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `employeeId` int unsigned NOT NULL AUTO_INCREMENT,
  `userDetailsId` int unsigned NOT NULL,
  `employeeRoleId` int unsigned NOT NULL,
  `gender` varchar(10) NOT NULL,
  `DOB` date NOT NULL,
  `hiringDate` date NOT NULL,
  `leavingDate` date DEFAULT NULL,
  `salary` double NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`employeeId`),
  UNIQUE KEY `employeeId_UNIQUE` (`employeeId`),
  KEY `fk_userDetailsId_Employee_idx` (`userDetailsId`),
  KEY `fk_employeeRoleId_idx` (`employeeRoleId`),
  KEY `fk_updatedBy_Employee_idx` (`updatedBy`),
  KEY `fk_createdBy_Employee_idx` (`createdBy`),
  CONSTRAINT `fk_createdBy_Employee` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_employeeRoleId` FOREIGN KEY (`employeeRoleId`) REFERENCES `enuemployeerole` (`employeeRoleId`),
  CONSTRAINT `fk_updatedBy_Employee` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_userDetailsId_Employee` FOREIGN KEY (`userDetailsId`) REFERENCES `userdetails` (`userDetailsId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,8,2,'Male','1987-01-01','2021-01-01',NULL,50000,'2022-02-24 15:45:53','2022-02-24 15:45:53',1,NULL,NULL),(2,9,3,'Female','1990-03-02','2022-01-01',NULL,20000,'2022-02-24 15:47:56','2022-02-24 15:47:56',1,NULL,NULL),(3,10,2,'Male','1986-01-11','2021-01-11',NULL,60000,'2022-02-24 15:49:40','2022-02-24 15:49:40',1,NULL,NULL),(4,17,3,'Female','1989-11-20','2021-11-20',NULL,120000,'2022-02-24 16:32:45','2022-02-24 16:32:45',1,NULL,NULL),(5,18,1,'Female','1989-08-08','2021-08-08',NULL,56000,'2022-02-24 16:34:02','2022-02-24 16:34:02',1,NULL,NULL),(6,19,1,'Male','1990-01-02','2020-01-02',NULL,85000,'2022-02-24 16:35:13','2022-02-24 16:35:13',1,NULL,NULL),(7,20,4,'Male','1991-03-05','2021-03-05',NULL,12000,'2022-02-24 16:47:54','2022-02-24 16:47:54',1,NULL,NULL),(8,21,4,'Male','1991-06-22','2020-06-22',NULL,15000,'2022-02-24 16:48:57','2022-02-24 16:48:57',1,NULL,NULL),(9,22,5,'Male','1992-05-04','2021-05-04',NULL,23000,'2022-02-24 16:50:37','2022-02-24 16:50:37',1,NULL,NULL),(10,23,5,'Male','1993-06-09','2021-06-09',NULL,25000,'2022-02-24 16:52:07','2022-02-24 16:52:07',1,NULL,NULL),(11,24,6,'Male','1991-07-22','2021-07-22',NULL,24000,'2022-02-24 16:53:25','2022-02-24 16:53:25',1,NULL,NULL),(12,25,6,'Male','1992-09-12','2020-09-12',NULL,28900,'2022-02-24 16:54:38','2022-02-24 16:54:38',1,NULL,NULL),(13,26,7,'Male','1992-08-11','2019-08-11',NULL,32000,'2022-02-24 16:57:10','2022-02-24 16:57:10',1,NULL,NULL),(14,27,7,'Male','1993-05-05','2021-05-05',NULL,36000,'2022-02-24 16:58:08','2022-02-24 16:58:08',1,NULL,NULL),(15,28,8,'Male','1993-06-06','2020-06-06',NULL,25000,'2022-02-24 16:59:13','2022-02-24 16:59:13',1,NULL,NULL),(16,29,8,'Male','1992-07-07','2021-07-07',NULL,56000,'2022-02-24 17:00:16','2022-02-24 17:00:16',1,NULL,NULL),(17,30,9,'Female','1989-08-08','2020-08-08',NULL,80000,'2022-02-24 17:01:13','2022-02-24 17:01:13',1,NULL,NULL),(18,31,9,'Male','1992-11-29','2021-11-29',NULL,25690,'2022-02-24 17:02:11','2022-02-24 17:02:11',1,NULL,NULL);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-24 19:51:56
