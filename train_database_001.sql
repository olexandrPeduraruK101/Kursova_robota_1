-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: train_database_001
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `routes`
--

DROP TABLE IF EXISTS `routes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `routes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `departure_station_id` int NOT NULL,
  `arrival_station_id` int NOT NULL,
  `distance` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `departure_station_id` (`departure_station_id`),
  KEY `arrival_station_id` (`arrival_station_id`),
  CONSTRAINT `routes_ibfk_1` FOREIGN KEY (`departure_station_id`) REFERENCES `staishon` (`id`),
  CONSTRAINT `routes_ibfk_2` FOREIGN KEY (`arrival_station_id`) REFERENCES `staishon` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `routes`
--

LOCK TABLES `routes` WRITE;
/*!40000 ALTER TABLE `routes` DISABLE KEYS */;
INSERT INTO `routes` VALUES (1,6,5,530),(2,5,2,267),(3,3,1,280),(4,1,3,280);
/*!40000 ALTER TABLE `routes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seats_buy`
--

DROP TABLE IF EXISTS `seats_buy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seats_buy` (
  `id` int NOT NULL AUTO_INCREMENT,
  `train_id` int NOT NULL,
  `seat_number` int NOT NULL,
  `booked` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `train_id` (`train_id`),
  CONSTRAINT `seats_buy_ibfk_1` FOREIGN KEY (`train_id`) REFERENCES `train` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seats_buy`
--

LOCK TABLES `seats_buy` WRITE;
/*!40000 ALTER TABLE `seats_buy` DISABLE KEYS */;
INSERT INTO `seats_buy` VALUES (1,1,1,1),(2,1,2,0),(3,1,3,0),(4,1,4,0),(5,1,5,0);
/*!40000 ALTER TABLE `seats_buy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staishon`
--

DROP TABLE IF EXISTS `staishon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staishon` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name_staishon` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name_staishon` (`name_staishon`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staishon`
--

LOCK TABLES `staishon` WRITE;
/*!40000 ALTER TABLE `staishon` DISABLE KEYS */;
INSERT INTO `staishon` VALUES (4,'м.Вінниця'),(1,'м.Житомир'),(6,'м.Київ'),(2,'м.Львів'),(3,'м.Тернопіль'),(5,'м.Чернівці');
/*!40000 ALTER TABLE `staishon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickets` (
  `id` int NOT NULL AUTO_INCREMENT,
  `train_id` int NOT NULL,
  `number_train` int NOT NULL,
  `seat_id` int NOT NULL,
  `seat_number` int NOT NULL,
  `departure_station_id` int NOT NULL,
  `arrival_station_id` int NOT NULL,
  `departure_date` date NOT NULL,
  `departure_time` time NOT NULL,
  `arrival_date` date NOT NULL,
  `arrival_time` time NOT NULL,
  `login` varchar(100) NOT NULL,
  `firstname` varchar(100) NOT NULL,
  `lastname` varchar(100) NOT NULL,
  `price` varchar(50) NOT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `train_id` (`train_id`),
  KEY `departure_station_id` (`departure_station_id`),
  KEY `arrival_station_id` (`arrival_station_id`),
  KEY `seat_id` (`seat_id`),
  CONSTRAINT `tickets_ibfk_1` FOREIGN KEY (`train_id`) REFERENCES `train` (`id`),
  CONSTRAINT `tickets_ibfk_2` FOREIGN KEY (`departure_station_id`) REFERENCES `staishon` (`id`),
  CONSTRAINT `tickets_ibfk_3` FOREIGN KEY (`arrival_station_id`) REFERENCES `staishon` (`id`),
  CONSTRAINT `tickets_ibfk_4` FOREIGN KEY (`seat_id`) REFERENCES `seats_buy` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (1,1,15,1,1,5,2,'2023-05-01','10:00:00','2023-05-01','15:30:00','Leo','GGoe','Dwoe','1300.00','done');
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `train`
--

DROP TABLE IF EXISTS `train`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `train` (
  `id` int NOT NULL AUTO_INCREMENT,
  `train_number` int NOT NULL,
  `departure_station_id` int NOT NULL,
  `arrival_station_id` int NOT NULL,
  `distance` float NOT NULL,
  `total_seats` int NOT NULL,
  `booked_seats` int NOT NULL,
  `departure_date` date NOT NULL,
  `departure_time` time NOT NULL,
  `arrival_date` date NOT NULL,
  `arrival_time` time NOT NULL,
  `train_status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `departure_station_id` (`departure_station_id`),
  KEY `arrival_station_id` (`arrival_station_id`),
  CONSTRAINT `train_ibfk_1` FOREIGN KEY (`departure_station_id`) REFERENCES `staishon` (`id`),
  CONSTRAINT `train_ibfk_2` FOREIGN KEY (`arrival_station_id`) REFERENCES `staishon` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `train`
--

LOCK TABLES `train` WRITE;
/*!40000 ALTER TABLE `train` DISABLE KEYS */;
INSERT INTO `train` VALUES (1,15,5,2,267,5,1,'2023-05-01','10:00:00','2023-05-01','15:30:00','done');
/*!40000 ALTER TABLE `train` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `Login` varchar(20) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL,
  `FatherName` varchar(50) NOT NULL,
  `numberphone` varchar(10) NOT NULL,
  `status` varchar(50) DEFAULT 'passenger',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_login` (`Login`),
  UNIQUE KEY `unique_phone_number` (`numberphone`),
  CONSTRAINT `min_length_constraint` CHECK ((length(`Login`) >= 3)),
  CONSTRAINT `min_length_constraint_fathername` CHECK ((length(`FatherName`) >= 2)),
  CONSTRAINT `min_length_constraint_firstname` CHECK ((length(`FirstName`) >= 2)),
  CONSTRAINT `min_length_constraint_lastname` CHECK ((length(`LastName`) >= 2)),
  CONSTRAINT `min_length_constraint_numberphone` CHECK ((length(`numberphone`) = 10)),
  CONSTRAINT `min_length_constraint_passsword` CHECK ((length(`Password`) >= 3))
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'San','123','John','Doe','Smith','1234567890','admin'),(4,'Leo','321','GGoe','Dwoe','Smawith','1231231231','passenger'),(8,'eee','1223','dd','ff','ww','1212121212','passenger'),(11,'qqe','321','hh','rr','ee','1111111112','passenger');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'train_database_001'
--

--
-- Dumping routines for database 'train_database_001'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-26 10:57:58
