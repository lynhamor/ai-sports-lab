CREATE DATABASE IF NOT EXISTS `core`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
USE core;

DROP TABLE IF EXISTS `game`;
CREATE TABLE `game` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `game_name` varchar(100) NOT NULL,
                        `game_category` varchar(100) NOT NULL,
                        `is_enable` tinyint(1) DEFAULT 1,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game` DISABLE KEYS */;
INSERT INTO `game` VALUES (1,'nba','SIMULATION',1);
/*!40000 ALTER TABLE `game` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `game_rule`;
CREATE TABLE `game_rule` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `game_id` bigint(20) NOT NULL,
                             `rule_key` varchar(100) NOT NULL,
                             `rule_value` longtext NOT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

LOCK TABLES `game_rule` WRITE;
/*!40000 ALTER TABLE `game_rule` DISABLE KEYS */;
INSERT INTO `game_rule` VALUES (1,1,'TOTAL_PACE','100'),(2,1,'REGULAR_MAX_CONS_PLAY','3'),(3,1,'PRESZN_MAX_CONS_PLAY','2'),(4,1,'GAME_EVENT','PRESEASON'),(5,1,'GAME_SEASON','SUMMER'),(6,1,'YEAR','2026');
/*!40000 ALTER TABLE `game_rule` ENABLE KEYS */;
UNLOCK TABLES;

CREATE TABLE callback_outbox (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 aggregate_id BIGINT NOT NULL,
                                 event_type VARCHAR(100),
                                 payload JSON,
                                 status VARCHAR(20) DEFAULT 'PENDING',
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);