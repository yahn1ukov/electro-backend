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
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `mileage` int DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `percentage_of_charge` double DEFAULT NULL,
  `type_connector` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `vin_code` varchar(255) DEFAULT NULL,
  `owner_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_77037r4tvg5cxp25n6w29u43s` (`vin_code`),
  KEY `FKm5ibu05fg8g81fo6491puswuu` (`owner_id`),
  CONSTRAINT `FKm5ibu05fg8g81fo6491puswuu` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cars`
--

LOCK TABLES `cars` WRITE;
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `charger_users`
--

DROP TABLE IF EXISTS `charger_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `charger_users` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_block` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `is_verification` bit(1) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `web_site` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_h5eqe6ld9n8tu4akqacn9hvl2` (`email`),
  UNIQUE KEY `UK_ojg5ndc97h2if7vcl38e048fx` (`company`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `charger_users`
--

LOCK TABLES `charger_users` WRITE;
/*!40000 ALTER TABLE `charger_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `charger_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chargers`
--

DROP TABLE IF EXISTS `chargers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chargers` (
  `id` bigint NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `time_from` varchar(255) DEFAULT NULL,
  `time_to` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `zip_code` int DEFAULT NULL,
  `is_broken` bit(1) DEFAULT NULL,
  `is_charging` bit(1) DEFAULT NULL,
  `is_fast` bit(1) DEFAULT NULL,
  `is_pay` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price_of_per_hour` float DEFAULT NULL,
  `type_connector` varchar(255) DEFAULT NULL,
  `owner_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_e77d6dnsln5u50mjep94h9odb` (`name`),
  KEY `FKsm871vas0li35g4vsv32b0lt1` (`owner_id`),
  CONSTRAINT `FKsm871vas0li35g4vsv32b0lt1` FOREIGN KEY (`owner_id`) REFERENCES `charger_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chargers`
--

LOCK TABLES `chargers` WRITE;
/*!40000 ALTER TABLE `chargers` DISABLE KEYS */;
/*!40000 ALTER TABLE `chargers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complaints_user_charger`
--

DROP TABLE IF EXISTS `complaints_user_charger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `complaints_user_charger` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `charger_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhxd51ntd83lscbmwnf5y7hila` (`user_id`),
  KEY `FK3vu8n6ogkt3uv9ycu6kby5n1g` (`charger_id`),
  CONSTRAINT `FK3vu8n6ogkt3uv9ycu6kby5n1g` FOREIGN KEY (`charger_id`) REFERENCES `chargers` (`id`),
  CONSTRAINT `FKhxd51ntd83lscbmwnf5y7hila` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaints_user_charger`
--

LOCK TABLES `complaints_user_charger` WRITE;
/*!40000 ALTER TABLE `complaints_user_charger` DISABLE KEYS */;
/*!40000 ALTER TABLE `complaints_user_charger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complaints_user_station`
--

DROP TABLE IF EXISTS `complaints_user_station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `complaints_user_station` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `station_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk8va6gxbieuvie8c4kig5hcs9` (`user_id`),
  KEY `FKbeaspiauf0r8qo5bjox2bw58p` (`station_id`),
  CONSTRAINT `FKbeaspiauf0r8qo5bjox2bw58p` FOREIGN KEY (`station_id`) REFERENCES `stations` (`id`),
  CONSTRAINT `FKk8va6gxbieuvie8c4kig5hcs9` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaints_user_station`
--

LOCK TABLES `complaints_user_station` WRITE;
/*!40000 ALTER TABLE `complaints_user_station` DISABLE KEYS */;
/*!40000 ALTER TABLE `complaints_user_station` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (2);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `station_users`
--

DROP TABLE IF EXISTS `station_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `station_users` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_block` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `is_verification` bit(1) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `web_site` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9n71bpptqbh1tlw5splp0gl5b` (`email`),
  UNIQUE KEY `UK_kjt1m52pm2a1aacmyapydci81` (`company`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `station_users`
--

LOCK TABLES `station_users` WRITE;
/*!40000 ALTER TABLE `station_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `station_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stations`
--

DROP TABLE IF EXISTS `stations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stations` (
  `id` bigint NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `time_from` varchar(255) DEFAULT NULL,
  `time_to` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `zip_code` int DEFAULT NULL,
  `all_place` int DEFAULT NULL,
  `car_model` varchar(255) DEFAULT NULL,
  `car_name` varchar(255) DEFAULT NULL,
  `free_place` int DEFAULT NULL,
  `middle_price_for_per_hour` float DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `owner_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_f6787k9crm2wetdsqyqc8xwt5` (`name`),
  KEY `FKgsavqy1g1ki7wqwhtjglqx7a5` (`owner_id`),
  CONSTRAINT `FKgsavqy1g1ki7wqwhtjglqx7a5` FOREIGN KEY (`owner_id`) REFERENCES `station_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stations`
--

LOCK TABLES `stations` WRITE;
/*!40000 ALTER TABLE `stations` DISABLE KEYS */;
/*!40000 ALTER TABLE `stations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_block` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `is_verification` bit(1) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2022-06-10 16:24:12.840000','admin@nure.ua',_binary '\0','$2a$10$dJefEsylp3VZBztk7Pz5aO5zZlDgDum8OG.Am2TyhHHEgfmND.bfS','2022-06-10 16:24:12.840000',NULL,_binary '',NULL,'ADMIN');
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

-- Dump completed on 2022-06-10 16:24:38
