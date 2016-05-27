-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: localhost    Database: advertisingagency
-- ------------------------------------------------------
-- Server version	5.6.26-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customers` (
  `customerId` int(11) NOT NULL AUTO_INCREMENT,
  `name_Key` varchar(255) DEFAULT NULL,
  `logoPath` varchar(255) DEFAULT NULL,
  `webAddress` varchar(255) DEFAULT NULL,
  `description_Key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`customerId`),
  UNIQUE KEY `name_Key_UNIQUE` (`name_Key`),
  UNIQUE KEY `description_Key_UNIQUE` (`description_Key`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (2,'customerName2','resources/images/partners/partner1.png','http://www.mobile.bg/index_koli.html','customerDescription2'),(3,'customerName3','','https://www.google.bg/',NULL);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employees` (
  `employeeId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName_Key` varchar(255) DEFAULT NULL,
  `lastName_Key` varchar(255) DEFAULT NULL,
  `jobTitle_Key` varchar(255) DEFAULT NULL,
  `picturePath` varchar(255) DEFAULT NULL,
  `description_Key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`employeeId`),
  UNIQUE KEY `firstName_Key_UNIQUE` (`firstName_Key`),
  UNIQUE KEY `lastName_Key_UNIQUE` (`lastName_Key`),
  UNIQUE KEY `description_Key_UNIQUE` (`description_Key`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'employeeFirstName1','employeeLastName1','employeeJobTitle1','resources/images/man1.jpg','employeeDescription1'),(2,'employeeFirstName2','employeeLastName2','employeeJobTitle2','resources/images/man2.jpg','employeeDescription2'),(3,'employeeFirstName3','employeeLastName3','employeeJobTitle3','','employeeDescription3'),(4,'employeeFirstName4','employeeLastName4','employeeJobTitle4','resources/images/man4.jpg','employeeDescription4');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees_projects`
--

DROP TABLE IF EXISTS `employees_projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employees_projects` (
  `employeeId` int(11) NOT NULL,
  `projectId` int(11) NOT NULL,
  PRIMARY KEY (`employeeId`,`projectId`),
  KEY `ep_projects_fk_idx` (`projectId`),
  CONSTRAINT `ep_employees_fk` FOREIGN KEY (`employeeId`) REFERENCES `employees` (`employeeId`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `ep_projects_fk` FOREIGN KEY (`projectId`) REFERENCES `projects` (`projectId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees_projects`
--

LOCK TABLES `employees_projects` WRITE;
/*!40000 ALTER TABLE `employees_projects` DISABLE KEYS */;
/*!40000 ALTER TABLE `employees_projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `languages`
--

DROP TABLE IF EXISTS `languages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `languages` (
  `languageCode` varchar(2) NOT NULL,
  `languageName` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`languageCode`),
  UNIQUE KEY `Code_UNIQUE` (`languageCode`),
  UNIQUE KEY `languageName_Key_UNIQUE` (`languageName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `languages`
--

LOCK TABLES `languages` WRITE;
/*!40000 ALTER TABLE `languages` DISABLE KEYS */;
INSERT INTO `languages` VALUES ('en','English'),('bg','Български');
/*!40000 ALTER TABLE `languages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messages` (
  `messageId` int(11) NOT NULL AUTO_INCREMENT,
  `content` mediumtext NOT NULL,
  `senderEmail` varchar(45) NOT NULL,
  `repliedByUser` int(11) DEFAULT NULL,
  `senderName` varchar(45) NOT NULL,
  `subject` varchar(100) NOT NULL,
  `senderPhone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`messageId`),
  KEY `messages_users_fk_idx` (`repliedByUser`),
  CONSTRAINT `messages_users_fk` FOREIGN KEY (`repliedByUser`) REFERENCES `users` (`userId`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (5,'test message','nasko@ad.com',NULL,'Nasko','test',''),(32,'jk','test@ass.bg',NULL,'sdsads','www','');
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projects` (
  `projectId` int(11) NOT NULL AUTO_INCREMENT,
  `name_Key` varchar(255) DEFAULT NULL,
  `description_Key` varchar(255) DEFAULT NULL,
  `ownerId` int(11) DEFAULT NULL,
  `imagePath` varchar(255) DEFAULT NULL,
  `projecttypeId` int(11) DEFAULT NULL,
  PRIMARY KEY (`projectId`),
  UNIQUE KEY `name_Key_UNIQUE` (`name_Key`),
  UNIQUE KEY `description_Key_UNIQUE` (`description_Key`),
  KEY `projects_customers_fk_idx` (`ownerId`),
  KEY `projects_projecttypes_fk_idx` (`projecttypeId`),
  CONSTRAINT `projects_customers_fk` FOREIGN KEY (`ownerId`) REFERENCES `customers` (`customerId`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `projects_projecttypes_fk` FOREIGN KEY (`projecttypeId`) REFERENCES `projecttypes` (`projecttypeId`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` VALUES (1,'projectName1','projectDescription1',NULL,'resources/images/portfolio/full/awesomeweb-portfolio-page-tips-detailed-visuals.jpg',1),(2,'projectName2','projectDescription2',2,'resources/images/portfolio/full/admin-panel-portfolio.jpg',1),(3,'projectName3',NULL,3,'resources/images/portfolio/full/key-ring-styles-soft-i.jpg',4);
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projecttypes`
--

DROP TABLE IF EXISTS `projecttypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projecttypes` (
  `projecttypeId` int(11) NOT NULL AUTO_INCREMENT,
  `projecttypeCode` varchar(45) DEFAULT NULL,
  `projecttypeText_Key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`projecttypeId`),
  UNIQUE KEY `projecttypeText_Key_UNIQUE` (`projecttypeText_Key`),
  UNIQUE KEY `projecttypeCode_UNIQUE` (`projecttypeCode`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projecttypes`
--

LOCK TABLES `projecttypes` WRITE;
/*!40000 ALTER TABLE `projecttypes` DISABLE KEYS */;
INSERT INTO `projecttypes` VALUES (1,'website','projectTypeText1'),(4,'key-ring','projectTypeText4');
/*!40000 ALTER TABLE `projecttypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicecategories`
--

DROP TABLE IF EXISTS `servicecategories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servicecategories` (
  `serviceCategoryId` int(11) NOT NULL AUTO_INCREMENT,
  `name_Key` varchar(255) DEFAULT NULL,
  `description_Key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`serviceCategoryId`),
  UNIQUE KEY `name_Key_UNIQUE` (`name_Key`),
  UNIQUE KEY `description_Key_UNIQUE` (`description_Key`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicecategories`
--

LOCK TABLES `servicecategories` WRITE;
/*!40000 ALTER TABLE `servicecategories` DISABLE KEYS */;
INSERT INTO `servicecategories` VALUES (1,'serviceCategoryName1','serviceCategoryDescription1'),(2,'serviceCategoryName2','serviceCategoryDescription2'),(3,'serviceCategoryName3','serviceCategoryDescription3'),(4,'serviceCategoryName4',NULL);
/*!40000 ALTER TABLE `servicecategories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `services`
--

DROP TABLE IF EXISTS `services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `services` (
  `serviceId` int(11) NOT NULL AUTO_INCREMENT,
  `serviceCategoryId` int(11) DEFAULT NULL,
  `name_Key` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `productionTime` int(11) DEFAULT NULL,
  `description_Key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`serviceId`),
  UNIQUE KEY `name_Key_UNIQUE` (`name_Key`),
  UNIQUE KEY `description_Key_UNIQUE` (`description_Key`),
  KEY `services_serviceCategories_fk_idx` (`serviceCategoryId`),
  CONSTRAINT `services_serviceCategories_fk` FOREIGN KEY (`serviceCategoryId`) REFERENCES `servicecategories` (`serviceCategoryId`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `services`
--

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
INSERT INTO `services` VALUES (1,1,'serviceName1',5.00,1,'serviceDescription1'),(2,1,'serviceName2',9.00,1,'serviceDescription2'),(3,1,'serviceName3',10.00,1,'serviceDescription3'),(4,1,'serviceName4',10.00,1,NULL),(5,1,'serviceName5',13.00,2,'serviceDescription5'),(6,1,'serviceName6',16.00,2,NULL),(7,1,'serviceName7',NULL,1,'serviceDescription7'),(8,2,'serviceName8',2.11,NULL,NULL),(9,2,'serviceName9',NULL,NULL,NULL),(10,2,'serviceName10',NULL,NULL,NULL),(11,2,'serviceName11',NULL,NULL,NULL),(12,2,'serviceName12',NULL,NULL,NULL),(13,2,'serviceName13',NULL,NULL,NULL),(14,2,'serviceName14',NULL,NULL,NULL),(15,2,'serviceName15',NULL,NULL,NULL),(16,2,'serviceName16',NULL,NULL,NULL),(17,2,'serviceName17',NULL,NULL,NULL),(18,2,'serviceName18',NULL,NULL,NULL),(19,2,'serviceName19',NULL,NULL,NULL),(20,3,'serviceName20',NULL,NULL,NULL),(21,3,'serviceName21',NULL,NULL,NULL),(22,3,'serviceName22',NULL,NULL,NULL),(23,3,'serviceName23',NULL,NULL,NULL),(24,3,'serviceName24',NULL,NULL,NULL),(25,3,'serviceName25',NULL,NULL,NULL),(26,3,'serviceName26',NULL,NULL,NULL),(27,3,'serviceName27',NULL,NULL,NULL),(28,3,'serviceName28',NULL,NULL,NULL),(29,4,'serviceName29',NULL,NULL,'serviceDescription29'),(30,4,'serviceName30',NULL,NULL,'serviceDescription30'),(31,4,'serviceName31',NULL,NULL,'serviceDescription31'),(32,4,'serviceName32',NULL,NULL,'serviceDescription32'),(33,4,'serviceName33',NULL,NULL,'serviceDescription33'),(34,4,'serviceName34',NULL,NULL,'serviceDescription34'),(35,4,'serviceName35',NULL,NULL,'serviceDescription35');
/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `firstName_Key` varchar(255) DEFAULT NULL,
  `lastName_Key` varchar(255) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `address_Key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `firstName_Key_UNIQUE` (`firstName_Key`),
  UNIQUE KEY `lastName_Key_UNIQUE` (`lastName_Key`),
  UNIQUE KEY `address_Key_UNIQUE` (`address_Key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','admin','21232f297a57a5a743894a0e4a801fc3','userFirstName1','userLastName1',NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'advertisingagency'
--

--
-- Dumping routines for database 'advertisingagency'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-28  0:58:00
