CREATE DATABASE IF NOT EXISTS `s_core`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_uca1400_ai_ci;

USE `s_core`;

CREATE TABLE `nba_team` (
                            `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                            `team_city` VARCHAR(100) NOT NULL,
                            `team_name` VARCHAR(100) NOT NULL,
                            `conference_side` VARCHAR(100) NOT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `nba_team_unique` (`team_name`, `team_city`, `conference_side`)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE `nba_team_stats` (
                                  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                                  `team_id` BIGINT(20) NOT NULL,
                                  `win_rate` DECIMAL(5,4) DEFAULT 0.5000,
                                  `offensive_rate` DECIMAL(5,4) DEFAULT 0.5000,
                                  `defensive_rate` DECIMAL(5,4) DEFAULT 0.5000,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_uca1400_ai_ci;