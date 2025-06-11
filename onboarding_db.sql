-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: students_onboarding
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `item_progress`
--

DROP TABLE IF EXISTS `item_progress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_progress` (
  `progress_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `item_id` int NOT NULL,
  `is_completed` tinyint(1) DEFAULT '0',
  `completed_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`progress_id`),
  KEY `user_id` (`user_id`),
  KEY `item_id` (`item_id`),
  CONSTRAINT `item_progress_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `item_progress_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `module_item` (`item_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_progress`
--

LOCK TABLES `item_progress` WRITE;
/*!40000 ALTER TABLE `item_progress` DISABLE KEYS */;
INSERT INTO `item_progress` VALUES (1,2,1,1,'2025-06-09 20:58:52'),(2,2,2,1,'2025-06-09 20:58:53'),(3,2,3,1,'2025-06-09 20:59:01'),(4,2,4,1,'2025-06-09 21:00:20'),(5,3,1,1,'2025-06-10 01:26:15'),(6,3,2,1,'2025-06-10 01:44:13'),(7,3,3,1,'2025-06-10 03:19:06'),(8,3,4,0,'2025-05-29 01:07:07'),(9,3,5,1,'2025-06-10 20:52:10'),(10,3,6,1,'2025-06-10 20:58:16'),(11,11,1,1,'2025-06-10 22:05:28'),(12,11,2,1,'2025-06-10 22:17:10'),(13,12,7,1,'2025-06-10 22:20:30'),(14,12,3,1,'2025-06-10 22:20:43'),(15,12,6,1,'2025-06-10 22:20:46'),(16,12,5,1,'2025-06-10 22:20:58'),(17,12,2,1,'2025-06-10 22:21:06'),(18,12,1,1,'2025-06-10 22:21:06');
/*!40000 ALTER TABLE `item_progress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `sender_id` int NOT NULL,
  `receiver_id` int NOT NULL,
  `content` text NOT NULL,
  `sent_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `is_read` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`message_id`),
  KEY `sender_id` (`sender_id`),
  KEY `receiver_id` (`receiver_id`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `message_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,3,4,'Привееет!','2025-05-29 02:39:38',0),(2,4,3,'Здаров!','2025-05-29 02:40:38',0),(3,2,4,'Пока','2025-06-09 18:28:24',0);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module`
--

DROP TABLE IF EXISTS `module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `module` (
  `module_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL,
  `total_items` int DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`module_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module`
--

LOCK TABLES `module` WRITE;
/*!40000 ALTER TABLE `module` DISABLE KEYS */;
INSERT INTO `module` VALUES (1,'Ориентируемся в пространств','Здесь вы узнаете, как не потеряться в нашем университете',NULL,2,'2025-05-29 00:09:57','2025-05-29 01:09:23'),(2,'Учебные материалы','Что нужно? Где найти? Все узнаете здесь',NULL,2,'2025-05-29 00:11:13','2025-05-29 01:09:23'),(3,'Список Преподавателей','Здесь находявыатся все преподаватели','',0,'2025-06-10 20:45:00','2025-06-10 20:45:00'),(4,'Супер модуль','Здесь есть все','',0,'2025-06-10 22:19:10','2025-06-10 22:19:10');
/*!40000 ALTER TABLE `module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module_item`
--

DROP TABLE IF EXISTS `module_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `module_item` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `module_id` int NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `content` text NOT NULL,
  `points` int DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`item_id`),
  KEY `module_id` (`module_id`),
  CONSTRAINT `module_item_ibfk_1` FOREIGN KEY (`module_id`) REFERENCES `module` (`module_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module_item`
--

LOCK TABLES `module_item` WRITE;
/*!40000 ALTER TABLE `module_item` DISABLE KEYS */;
INSERT INTO `module_item` VALUES (1,1,'Поиск аудитории',NULL,'Номер ауитории состоит из 4 цифр. Первая значит корпус, вторая - этаж, и последние две - номер аудитории',10,'2025-05-29 00:12:44','2025-05-29 00:12:44'),(2,1,'Расположение корпусов',NULL,'Корпуса находятся тут и там.',10,'2025-05-29 00:13:15','2025-05-29 00:13:15'),(3,2,'Материалы по программированию',NULL,'учебники по программированию',10,'2025-05-29 00:14:03','2025-06-09 19:31:07'),(4,2,'Учебный материал по сетям',NULL,'Все учебники, необходимые по сетям',10,'2025-05-29 00:14:42','2025-06-09 19:31:07'),(5,3,'Кафедра информатики','Здесь преподаватели с самой крутой кафедры','Вот такой, и вот такой, а еще такой и такой. Вот столько, да',20,'2025-06-10 20:50:19','2025-06-10 20:50:19'),(6,2,'СУПЕР МАТЕРИАЛ','123','АБВГДЕЁЖЗ. И так далее.\nFor almost any person, there is nothing more important in the world than their family. I love my family too. Today I would like to tell you about them.\n\nMy family is quite big. It consists of my mother, father, my three siblings and our cat Bob. Well, most people would say that a pet is not a family member but no one in our family would agree with that. We all love Bob and consider him a family member.\n\nMy mother’s name is Anna, she is a teacher. She has been working in our local school for a long time. My mom teaches History and Social Studies, the subjects that I really love. She loves reading very much, and her favorite book is “A Street Cat Named Bob” by James Bowen. If you are familiar with the book, you can guess why she named our ginger cat Bob.\n\nMy father’s name is Igor. He is a little older than mom and he used to be a police officer. He retired at a quite young age and has been running his own business ever since. He has a small coffee shop and a candy store. To be honest, I don’t know much about his business, but he says it is doing well.\n\nAs for my siblings, I have a sister, her name is Maria, and she is the oldest one, and two brothers – Viktor and Boris. By the way, I am the youngest child in the family but I am totally happy with that role. My sister Maria is an engineer, she lives in another town but visits us at least once a month. My brothers are still students. Viktor studies history, following in our mother’s footsteps, Boris is going to be a software developer. He is a big fan of videogames, and his dream is to develop his own game.\n\nIn conclusion, I would like to say that I love my family and for me there is nothing more important than them. I think, they all have the same opinion. Even Bob.',100,'2025-06-10 20:56:42','2025-06-10 20:56:42'),(7,4,'Найди все','Тут правда есть все','Все. Все. Все. Все.',65,'2025-06-10 22:19:45','2025-06-10 22:19:45');
/*!40000 ALTER TABLE `module_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `study_group`
--

DROP TABLE IF EXISTS `study_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `study_group` (
  `group_id` int NOT NULL AUTO_INCREMENT,
  `group_name` varchar(50) NOT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `study_group`
--

LOCK TABLES `study_group` WRITE;
/*!40000 ALTER TABLE `study_group` DISABLE KEYS */;
INSERT INTO `study_group` VALUES (1,'АУБП-22'),(2,'АТП-21'),(3,'ИНФ-20'),(4,'АБВ-24'),(5,'ЭКГ-25'),(6,'БТР-23');
/*!40000 ALTER TABLE `study_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task` (
  `task_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `task_type` enum('module_completion','points_earned','others') NOT NULL,
  `target_value` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,'САМЫЙ ОПЫТНЫЙ','Заработать 30 очков','points_earned',30,'2025-06-09 19:21:34'),(2,'САМЫЙ МoДУЛЬНЫЙ','Пройти 2 модуля','module_completion',2,'2025-06-09 19:22:09'),(3,'MEGATRON','ЗАРАБОТАТЬ РЕСПЕКТ','others',1000,'2025-06-10 21:06:04'),(4,'Пройди 3 модуля','пройти 3 модуля','module_completion',3,'2025-06-10 22:20:08');
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_progress`
--

DROP TABLE IF EXISTS `task_progress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_progress` (
  `task_progress_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `task_id` int NOT NULL,
  `current_value` int DEFAULT '0',
  `is_completed` tinyint(1) DEFAULT '0',
  `completed_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`task_progress_id`),
  KEY `user_id` (`user_id`),
  KEY `task_id` (`task_id`),
  CONSTRAINT `task_progress_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `task_progress_ibfk_2` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_progress`
--

LOCK TABLES `task_progress` WRITE;
/*!40000 ALTER TABLE `task_progress` DISABLE KEYS */;
INSERT INTO `task_progress` VALUES (1,2,1,40,1,'2025-06-09 20:59:01'),(2,2,2,2,1,'2025-06-09 21:00:20'),(3,3,1,150,1,'2025-06-10 03:19:06'),(4,3,2,2,1,'2025-06-10 20:52:10'),(5,8,1,0,0,NULL),(6,8,2,0,0,NULL),(7,9,1,0,0,NULL),(8,9,2,0,0,NULL),(9,9,3,0,0,NULL),(10,10,1,0,0,NULL),(11,10,2,0,0,NULL),(12,10,3,0,0,NULL),(13,3,3,0,0,NULL),(14,11,1,20,0,NULL),(15,11,2,1,0,NULL),(16,11,3,0,0,NULL),(17,12,1,215,1,'2025-06-10 22:20:30'),(18,12,2,3,1,'2025-06-10 22:20:58'),(19,12,3,0,0,NULL),(20,12,4,3,1,'2025-06-10 22:21:06');
/*!40000 ALTER TABLE `task_progress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `role` varchar(50) NOT NULL DEFAULT 'student',
  `group_id` int DEFAULT NULL,
  `firstname` varchar(100) DEFAULT NULL,
  `lastname` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  KEY `role_id` (`role`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `user_ibfk_2` FOREIGN KEY (`group_id`) REFERENCES `study_group` (`group_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','admin@mail.ru','$2a$10$h1QNDCNeiiA6IpVBd7/mf.wVEXf5PdjKTndhjAZWrOPvm4YaHXyRm','admin',NULL,NULL,NULL),(2,'user','user@mail.ru','$2a$10$CYz8L7KwfAI3sh5ZF/vZHO9aQyPxDLcyOwzxZRLxNbCXwQ/AcfTeS','student',1,'Вася','Пупкин'),(3,'user2','user2@mail.ru','$2a$10$1SeNPjUnfM.zMrJ7QgDVvOEUiv486CW4SogigVBUuHLpSXRqxjUTC','student',2,'Петя','Петров'),(4,'curator1','curator1@mail.ru','$2a$10$Pv4GbIyVuFeOyLZReuiDyeIzz3Mg7ZTNR9hzesevc1N9Sy2yVKA4G','curator',1,'Валя','Степанов'),(5,'curator2','curator2@mail.ru','$2a$10$gRvOTli/VcBZ0FSkxsvdk.urRcFurU8kBG/30mQFZmh5qdiipZLyW','curator',2,'Андрей','Гайдулян'),(8,'pipa','pipa@m.ru','$2a$10$B3kijs1VNm841h1wG1jcwu/uEA6N7BBHjtUxmYoAcJqrHdGf.t0i.','student',3,'Ярослав','Решетников'),(9,'123','as@as.ru','$2a$10$GiJNxed2OuFpbjImjz90Fe1dUDQZYymeq7gBZplZ159L4CzeMxxma','student',NULL,NULL,NULL),(10,'abc','abc@abc.ru','$2a$10$yYDCd2pF2pswisD/99vWZ.bWRXJOo1ZEXjjNwB5tuGzjQGUUJm1te','student',3,'Абвгд','Абвгдов'),(11,'aboba','aboba@mail.ru','$2a$10$ns2CGOEfbOQ9t5ELeURe9OsG/FGs3Jh8xQIjemUJ.Inu0Crm/kMY2','student',5,'Абоба','Абобов'),(12,'privet','privet@privet.ru','$2a$10$0vxjrbvlGMsbTR0TNMinBO5UeLVj6cIG.RfkpofMEM2IwjF4YxvaO','student',4,'Привет','Приветов');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_level`
--

DROP TABLE IF EXISTS `user_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_level` (
  `user_level_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `level` int DEFAULT '0',
  `total_points` int DEFAULT '0',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_level_id`),
  UNIQUE KEY `user_id` (`user_id`),
  CONSTRAINT `user_level_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_level`
--

LOCK TABLES `user_level` WRITE;
/*!40000 ALTER TABLE `user_level` DISABLE KEYS */;
INSERT INTO `user_level` VALUES (1,3,2,150,'2025-06-10 20:58:16'),(2,2,1,80,'2025-06-09 21:00:20'),(3,8,1,0,'2025-06-10 20:25:18'),(4,9,1,0,'2025-06-10 21:17:31'),(5,10,1,0,'2025-06-10 21:26:23'),(6,11,1,20,'2025-06-10 22:17:10'),(7,12,3,215,'2025-06-10 22:21:06');
/*!40000 ALTER TABLE `user_level` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-11  8:00:59
