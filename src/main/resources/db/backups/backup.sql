-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: electro
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `electro`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `electro` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `electro`;

--
-- Table structure for table `cars`
--

DROP TABLE IF EXISTS `cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cars` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `vin_code` varchar(30) NOT NULL,
  `owner_id` bigint DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `model` varchar(50) NOT NULL,
  `mileage` int NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `type_connector` varchar(20) NOT NULL,
  `percentage_of_charge` double NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `vin_code` (`vin_code`),
  KEY `fk_user_cars` (`owner_id`),
  CONSTRAINT `fk_user_cars` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cars`
--

LOCK TABLES `cars` WRITE;
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
INSERT INTO `cars` VALUES (1,'3VWLL7AJ9BM053541',2,'Nissan','Leaf',50000,50.12345,110.12345,'J-123',50,'2022-07-03 07:19:59'),(2,'2G4WB52K4T1422518',NULL,'Tesla','Model 3',100000,55.12345,115.12345,'J-178',15,'2022-07-03 07:20:54'),(3,'3B3ES47C6WT211725',2,'Tesla','Model X',100000,55.12345,115.12345,'J-177',35,'2022-07-03 07:21:29'),(4,'1ZVHT85H465102319',NULL,'BMW','i3',50000,50.12345,110.12345,'J-123',18,'2022-07-03 12:42:31');
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `charger_users`
--

DROP TABLE IF EXISTS `charger_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `charger_users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `company` varchar(100) NOT NULL,
  `is_not_block` bit(1) NOT NULL,
  `is_verification` bit(1) NOT NULL,
  `role` varchar(20) NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `charger_users`
--

LOCK TABLES `charger_users` WRITE;
/*!40000 ALTER TABLE `charger_users` DISABLE KEYS */;
INSERT INTO `charger_users` VALUES (1,'charger.electro@nure.ua','$2a$10$doMdFmRDOMxNdgseK2lL4.wxqJ6tNvB.YpgQryS5t1W7jm588ctV2','Electro',_binary '',_binary '','CHARGER','2022-07-02 20:10:23');
/*!40000 ALTER TABLE `charger_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chargers`
--

DROP TABLE IF EXISTS `chargers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chargers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `country` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `street` varchar(200) NOT NULL,
  `zip_code` int NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `owner_id` bigint NOT NULL,
  `code` varchar(50) NOT NULL,
  `is_charging` bit(1) NOT NULL,
  `is_broken` bit(1) NOT NULL,
  `is_fast` bit(1) NOT NULL,
  `is_pay` bit(1) NOT NULL,
  `price_of_per_hour` float NOT NULL,
  `type_connector` varchar(20) NOT NULL,
  `time_from` varchar(10) NOT NULL,
  `time_to` varchar(10) NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `fk_charger_user_chargers` (`owner_id`),
  CONSTRAINT `fk_charger_user_chargers` FOREIGN KEY (`owner_id`) REFERENCES `charger_users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chargers`
--

LOCK TABLES `chargers` WRITE;
/*!40000 ALTER TABLE `chargers` DISABLE KEYS */;
INSERT INTO `chargers` VALUES (1,'Україна','Харків','Вул. Перемоги 5а',61100,50.12345,110.12345,1,'2C3CCACG5CH278240',_binary '\0',_binary '\0',_binary '',_binary '',10,'J-123','10','20','2022-07-03 07:50:52'),(2,'Україна','Харків','Вул. Перемоги 5а',61100,55.12345,115.12345,1,'1G8ZF52801Z328015',_binary '\0',_binary '\0',_binary '\0',_binary '\0',0,'J-177','10','20','2022-07-03 07:53:34');
/*!40000 ALTER TABLE `chargers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complaints_users_chargers`
--

DROP TABLE IF EXISTS `complaints_users_chargers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `complaints_users_chargers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `charger_id` bigint NOT NULL,
  `description` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_complaints_chargers_users` (`charger_id`),
  KEY `fk_complaints_users_chargers` (`user_id`),
  CONSTRAINT `fk_complaints_chargers_users` FOREIGN KEY (`charger_id`) REFERENCES `chargers` (`id`),
  CONSTRAINT `fk_complaints_users_chargers` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaints_users_chargers`
--

LOCK TABLES `complaints_users_chargers` WRITE;
/*!40000 ALTER TABLE `complaints_users_chargers` DISABLE KEYS */;
INSERT INTO `complaints_users_chargers` VALUES (2,2,1,'Тест скарга на зарядну станцію','2022-07-03 09:24:10');
/*!40000 ALTER TABLE `complaints_users_chargers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complaints_users_stations`
--

DROP TABLE IF EXISTS `complaints_users_stations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `complaints_users_stations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `station_id` bigint NOT NULL,
  `description` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_complaints_stations_users` (`station_id`),
  KEY `fk_complaints_users_stations` (`user_id`),
  CONSTRAINT `fk_complaints_stations_users` FOREIGN KEY (`station_id`) REFERENCES `stations` (`id`),
  CONSTRAINT `fk_complaints_users_stations` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaints_users_stations`
--

LOCK TABLES `complaints_users_stations` WRITE;
/*!40000 ALTER TABLE `complaints_users_stations` DISABLE KEYS */;
INSERT INTO `complaints_users_stations` VALUES (1,2,1,'Тест скарга на СТО','2022-07-03 09:23:26');
/*!40000 ALTER TABLE `complaints_users_stations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangelog`
--

LOCK TABLES `databasechangelog` WRITE;
/*!40000 ALTER TABLE `databasechangelog` DISABLE KEYS */;
INSERT INTO `databasechangelog` VALUES ('create-user-table','Andrii Yahniukov','db/changelog/changeset/2022-07-02-2245-create-user-table.yaml','2022-07-02 22:59:15',1,'EXECUTED','8:a2e5f2f5d15f69d8ba28219e5f570f93','createTable tableName=users','',NULL,'4.9.0',NULL,NULL,'6791955814'),('create-charger-user-table','Andrii Yahniukov','db/changelog/changeset/2022-07-02-2304-create-charger-user-table.yaml','2022-07-02 23:09:49',2,'EXECUTED','8:b9ab358ca20092e4fac5b7fddbbf7b3f','createTable tableName=charger_users','',NULL,'4.9.0',NULL,NULL,'6792589248'),('create-station-user-table','Andrii Yahniukov','db/changelog/changeset/2022-07-02-2312-create-station-user-table.yaml','2022-07-02 23:19:25',3,'EXECUTED','8:703960656ddad812d1ed79b5b91ea1dd','createTable tableName=station_users','',NULL,'4.9.0',NULL,NULL,'6793165570'),('create-car-table','Andrii Yahniukov','db/changelog/changeset/2022-07-03-0837-create-car-table.yaml','2022-07-03 10:13:02',4,'EXECUTED','8:9e6a3e2c0022f6aca9e0ea25c5f3323b','createTable tableName=cars','',NULL,'4.9.0',NULL,NULL,'6832382144'),('create-charger-table','Andrii Yahniukov','db/changelog/changeset/2022-07-03-1025-create-charger-table.yaml','2022-07-03 10:47:55',5,'EXECUTED','8:e28fa2d05e4d85cd5fc681b318108131','createTable tableName=chargers','',NULL,'4.9.0',NULL,NULL,'6834474905'),('create-station-table','Andrii Yahniukov','db/changelog/changeset/2022-07-03-1058-create-station-table.yaml','2022-07-03 11:51:17',6,'EXECUTED','8:347e2fa0bfea73dcfea8d43e4933a789','createTable tableName=stations','',NULL,'4.9.0',NULL,NULL,'6838277889'),('create-complaint-user-charger-table','Andrii Yahniukov','db/changelog/changeset/2022-07-03-1157-create-complaint-user-charger-table.yaml','2022-07-03 12:06:25',7,'EXECUTED','8:08bdccb44c5b5cc34f22def52914d8fe','createTable tableName=complaints_users_chargers','',NULL,'4.9.0',NULL,NULL,'6839185451'),('create-complaint-user-station-table','Andrii Yahniukov','db/changelog/changeset/2022-07-03-1218-create-complaint-user-station-table.yaml','2022-07-03 12:21:10',8,'EXECUTED','8:e0a796a890495e2b4fe0570d1a173001','createTable tableName=complaints_users_stations','',NULL,'4.9.0',NULL,NULL,'6840070644');
/*!40000 ALTER TABLE `databasechangelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangeloglock` (
  `ID` int NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` VALUES (1,_binary '\0',NULL,NULL);
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `station_users`
--

DROP TABLE IF EXISTS `station_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `station_users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `company` varchar(100) NOT NULL,
  `is_not_block` bit(1) NOT NULL,
  `is_verification` bit(1) NOT NULL,
  `role` varchar(20) NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `station_users`
--

LOCK TABLES `station_users` WRITE;
/*!40000 ALTER TABLE `station_users` DISABLE KEYS */;
INSERT INTO `station_users` VALUES (1,'station.electro@nure.ua','$2a$10$x0t7xmgtfoMY1Cm0vGpUXOHrxyNfVejM7/0HBD641lFXwricbaCPa','Electro',_binary '',_binary '','STATION','2022-07-02 20:20:03');
/*!40000 ALTER TABLE `station_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stations`
--

DROP TABLE IF EXISTS `stations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `country` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `street` varchar(200) NOT NULL,
  `zip_code` int NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `owner_id` bigint NOT NULL,
  `name` varchar(50) NOT NULL,
  `car_name` varchar(50) NOT NULL,
  `car_model` varchar(50) NOT NULL,
  `all_place` int NOT NULL,
  `free_place` int NOT NULL,
  `middle_price_for_per_hour` float NOT NULL,
  `time_from` varchar(10) NOT NULL,
  `time_to` varchar(10) NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `fk_station_user_stations` (`owner_id`),
  CONSTRAINT `fk_station_user_stations` FOREIGN KEY (`owner_id`) REFERENCES `station_users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stations`
--

LOCK TABLES `stations` WRITE;
/*!40000 ALTER TABLE `stations` DISABLE KEYS */;
INSERT INTO `stations` VALUES (1,'Україна','Харків','Вул. Перемоги 5а',61100,50.12345,110.12345,1,'Electro B-1','Nissan','Leaf',10,5,100,'10','20','2022-07-03 08:53:34'),(2,'Україна','Львів','Вул. Шевченка 10',61000,55.12345,115.12345,1,'Electro B-2','Tesla','Model 3',5,0,50,'10','20','2022-07-03 08:54:44');
/*!40000 ALTER TABLE `stations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `is_not_block` bit(1) NOT NULL,
  `is_verification` bit(1) NOT NULL,
  `role` varchar(20) NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin@nure.ua','$2a$10$xjA70YOKmbkLGIMN42Xi2.CpsgXZaUOftWjCdkDsiNS/yGG4ae9Sq','Admin','Admin',_binary '',_binary '','ADMIN','2022-07-02 20:00:08'),(2,'andrii@nure.ua','$2a$10$yThZfCtImidslUXD5noHAOUHK5uCxZaChVBNexMQYXIm9xmpAZNl.','Андрій','Ягнюков',_binary '',_binary '','USER','2022-07-03 09:07:25'),(3,'moderator@nure.ua','$2a$10$SHihDFBgprDUEyvqqeuvC.qYa/ea4tww.AdwapxnMraiGJHQIOqhW','Олександр','Пахоменков',_binary '',_binary '','MODERATOR','2022-07-03 09:14:02'),(4,'user@nure.ua','$2a$10$c/6Q2obGy2ZYjerD78rMnOahSGra8ZD97OgVKkMdA/jUmb.PtMXPi','user','user',_binary '',_binary '','USER','2022-07-03 19:09:46');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-03 22:09:58
