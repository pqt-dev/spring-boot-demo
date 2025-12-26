-- MySQL dump 10.13  Distrib 8.0.43, for macos15 (arm64)
--
-- Host: localhost    Database: java_spring_boot_dev
-- ------------------------------------------------------
-- Server version	9.4.0

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
-- Table structure for table `authors`
--

DROP TABLE IF EXISTS `authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authors` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `job` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `google_id` varchar(255) DEFAULT NULL,
  `password` varchar(500) NOT NULL,
  `avatar` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `google_id_UNIQUE` (`google_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authors`
--

LOCK TABLES `authors` WRITE;
/*!40000 ALTER TABLE `authors` DISABLE KEYS */;
INSERT INTO `authors` VALUES (1,'John Smith','123 Main St, New York','Software Engineer','+1-202-555-0001',NULL,NULL,'',NULL),(2,'Alice Johnson','45 Broadway, New York','Teacher','+1-202-555-0002',NULL,NULL,'',NULL),(3,'Michael Brown','78 Market St, San Francisco','Doctor','+1-202-555-0003',NULL,NULL,'',NULL),(4,'Emma Davis','12 Sunset Blvd, Los Angeles','Nurse','+1-202-555-0004',NULL,NULL,'',NULL),(5,'Daniel Wilson','90 Ocean Dr, Miami','Lawyer','+1-202-555-0005',NULL,NULL,'',NULL),(6,'Sophia Miller','34 Pine St, Boston','Accountant','+1-202-555-0006',NULL,NULL,'',NULL),(7,'James Anderson','56 Lakeview Rd, Chicago','Designer','+1-202-555-0007',NULL,NULL,'',NULL),(8,'Olivia Taylor','67 Hill St, Seattle','Photographer','+1-202-555-0008',NULL,NULL,'',NULL),(9,'William Thomas','89 River Rd, Denver','Architect','+1-202-555-0009',NULL,NULL,'',NULL),(10,'Isabella Moore','22 King St, Houston','Student','+1-202-555-0010',NULL,NULL,'',NULL),(11,'Ethan Martin','11 Queen St, Austin','Engineer','+1-202-555-0011',NULL,NULL,'',NULL),(12,'Mia Jackson','88 Green Rd, Dallas','Nurse','+1-202-555-0012',NULL,NULL,'',NULL),(13,'Alexander White','77 Elm St, Atlanta','Pilot','+1-202-555-0013',NULL,NULL,'',NULL),(14,'Charlotte Harris','66 Maple Ave, Phoenix','Chef','+1-202-555-0014',NULL,NULL,'',NULL),(15,'Benjamin Clark','55 Oak Dr, Portland','Driver','+1-202-555-0015',NULL,NULL,'',NULL),(16,'Amelia Lewis','44 Cedar Ln, Orlando','Artist','+1-202-555-0016',NULL,NULL,'',NULL),(17,'Henry Young','33 Birch Rd, Detroit','Dentist','+1-202-555-0017',NULL,NULL,'',NULL),(18,'Emily Hall','22 Walnut St, Philadelphia','Writer','+1-202-555-0018',NULL,NULL,'',NULL),(19,'Joseph Allen','99 Cherry Blvd, San Diego','Actor','+1-202-555-0019',NULL,NULL,'',NULL),(20,'Harper King','12 Vine St, Las Vegas','Singer','+1-202-555-0020',NULL,NULL,'',NULL),(21,'David Scott','21 Aspen St, Minneapolis','Barber','+1-202-555-0021',NULL,NULL,'',NULL),(22,'Abigail Green','65 Willow Rd, Denver','Receptionist','+1-202-555-0022',NULL,NULL,'',NULL),(23,'Christopher Adams','14 Poplar St, Nashville','Cashier','+1-202-555-0023',NULL,NULL,'',NULL),(24,'Ella Baker','72 Dogwood Ln, Boston','Florist','+1-202-555-0024',NULL,NULL,'',NULL),(25,'Matthew Nelson','82 Redwood Rd, Seattle','Farmer','+1-202-555-0025',NULL,NULL,'',NULL),(26,'Scarlett Hill','91 Magnolia Ave, Miami','Musician','+1-202-555-0026',NULL,NULL,'',NULL),(27,'Samuel Ramirez','13 Palm Dr, Tampa','Journalist','+1-202-555-0027',NULL,NULL,'',NULL),(28,'Victoria Campbell','41 Cypress St, Houston','Editor','+1-202-555-0028',NULL,NULL,'',NULL),(29,'Andrew Mitchell','28 Fir Rd, Chicago','Mechanic','+1-202-555-0029',NULL,NULL,'',NULL),(30,'Grace Perez','38 Spruce St, Austin','Pharmacist','+1-202-555-0030',NULL,NULL,'',NULL),(31,'Logan Roberts','99 Ash St, San Francisco','Scientist','+1-202-555-0031',NULL,NULL,'',NULL),(32,'Chloe Turner','12 Birchwood Ln, Los Angeles','Biologist','+1-202-555-0032',NULL,NULL,'',NULL),(33,'Joshua Phillips','17 Highland Rd, New York','Researcher','+1-202-555-0033',NULL,NULL,'',NULL),(34,'Zoe Parker','26 Riverbend St, Dallas','Librarian','+1-202-555-0034',NULL,NULL,'',NULL),(35,'Mason Evans','31 Westwood Ave, Chicago','Police Officer','+1-202-555-0035',NULL,NULL,'',NULL),(36,'Lily Edwards','46 Northgate Rd, Denver','Soldier','+1-202-555-0036',NULL,NULL,'',NULL),(37,'Jacob Collins','58 Eastwood Rd, San Jose','Security Guard','+1-202-555-0037',NULL,NULL,'',NULL),(38,'Aria Stewart','19 Brookside Dr, Orlando','Tailor','+1-202-555-0038',NULL,NULL,'',NULL),(39,'Lucas Sanchez','64 Meadow Ln, Phoenix','Carpenter','+1-202-555-0039',NULL,NULL,'',NULL),(40,'Avery Morris','23 Valley Rd, Boston','Painter','+1-202-555-0040',NULL,NULL,'',NULL),(41,'Jack Rogers','77 Oceanview Blvd, Miami','Chef','+1-202-555-0041',NULL,NULL,'',NULL),(42,'Evelyn Reed','88 Sunshine St, San Diego','Teacher','+1-202-555-0042',NULL,NULL,'',NULL),(43,'Sebastian Cook','99 Rainy St, Seattle','Engineer','+1-202-555-0043',NULL,NULL,'',NULL),(44,'Hannah Morgan','11 Cloud St, Denver','Doctor','+1-202-555-0044',NULL,NULL,'',NULL),(45,'Owen Bell','22 Thunder Rd, Austin','Lawyer','+1-202-555-0045',NULL,NULL,'',NULL),(46,'Madison Murphy','33 Stormy Ave, Dallas','Designer','+1-202-555-0046',NULL,NULL,'',NULL),(47,'Dylan Bailey','44 Breeze St, Portland','Driver','+1-202-555-0047',NULL,NULL,'',NULL),(48,'Ella Rivera','55 Skyline Rd, San Jose','Artist','+1-202-555-0048',NULL,NULL,'',NULL),(49,'Nathan Cooper','66 Sunset St, Las Vegas','Photographer','+1-202-555-0049',NULL,NULL,'',NULL),(50,'Sofia Richardson','77 Star Rd, Los Angeles','Student','+1-202-555-0050',NULL,NULL,'',NULL),(53,'Dev NTA22222','Dananian2222','Developer','0322111222',NULL,NULL,'',NULL),(56,'NTA Trongpq',NULL,'Guest',NULL,'trongpq.nta@gmail.com','116165709432000254908','$2a$10$uU47leyeJIaeMmGi.0wmT.oUa8ll2KIt5Gk7mTiiNC2Vlrzo3GXee','/uploads/1763969950183_photo3.jpg'),(57,'Testing','Danang','Dev','0938383838','trongpq.nta2@gmail.com',NULL,'$2a$10$uU47leyeJIaeMmGi.0wmT.oUa8ll2KIt5Gk7mTiiNC2Vlrzo3GXee','/uploads/1763027496986_noti.png');
/*!40000 ALTER TABLE `authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authors_photos`
--

DROP TABLE IF EXISTS `authors_photos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authors_photos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `author_id` bigint NOT NULL,
  `photo_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `uploaded_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `author_photos_ibfk_1` (`author_id`),
  CONSTRAINT `authors_photos_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `authors` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authors_photos`
--

LOCK TABLES `authors_photos` WRITE;
/*!40000 ALTER TABLE `authors_photos` DISABLE KEYS */;
INSERT INTO `authors_photos` VALUES (132,56,'/uploads/1763977705079_photo3.jpg','2025-11-24 16:48:25'),(133,56,'/uploads/1763977705084_photo2.png','2025-11-24 16:48:25'),(134,56,'/uploads/1763977705086_boxed-bg.jpg','2025-11-24 16:48:25'),(135,56,'/uploads/1764040962682_pngtree-man-using-laptop-png-image_12780624.png','2025-11-25 10:22:43'),(136,56,'/uploads/1764040990044_sample_1280×853.jpeg','2025-11-25 10:23:10');
/*!40000 ALTER TABLE `authors_photos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Education updated','Education decs updated','2025-09-08 03:15:27'),(2,'Entertaiment','Entertaiment desc','2025-09-08 03:15:27');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories_posts`
--

DROP TABLE IF EXISTS `categories_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories_posts` (
  `category_id` bigint NOT NULL,
  `posts_id` bigint NOT NULL,
  KEY `FKa8ect62uv6nuhy5xgrp4j9oqn` (`posts_id`),
  KEY `FK9juf11r7w0xqrwrfig4q1gqi5` (`category_id`),
  CONSTRAINT `FK9juf11r7w0xqrwrfig4q1gqi5` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `FKa8ect62uv6nuhy5xgrp4j9oqn` FOREIGN KEY (`posts_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories_posts`
--

LOCK TABLES `categories_posts` WRITE;
/*!40000 ALTER TABLE `categories_posts` DISABLE KEYS */;
/*!40000 ALTER TABLE `categories_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `author_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user` (`author_id`),
  CONSTRAINT `fk_user` FOREIGN KEY (`author_id`) REFERENCES `authors` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,'Post 1','Content 1',3),(7,'Post B','Content B',35),(8,'Create post teating title','Create post teating content',41);
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts_categories`
--

DROP TABLE IF EXISTS `posts_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts_categories` (
  `category_id` bigint NOT NULL,
  `post_id` bigint NOT NULL,
  KEY `FKmb0mart0q43x8cgtxlcjrav0h` (`category_id`),
  KEY `FKlhcehr18f04qdenj715f0518i` (`post_id`),
  CONSTRAINT `FKlhcehr18f04qdenj715f0518i` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
  CONSTRAINT `FKmb0mart0q43x8cgtxlcjrav0h` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts_categories`
--

LOCK TABLES `posts_categories` WRITE;
/*!40000 ALTER TABLE `posts_categories` DISABLE KEYS */;
INSERT INTO `posts_categories` VALUES (1,8),(2,8);
/*!40000 ALTER TABLE `posts_categories` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-25 15:53:18
