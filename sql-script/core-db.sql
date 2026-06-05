
CREATE DATABASE `core` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci */;

-- core.nba_team definition

CREATE TABLE `nba_team` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `team_city` varchar(100) NOT NULL,
                            `team_name` varchar(100) NOT NULL,
                            `team_side` varchar(100) NOT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `nba_team_unique` (`team_name`,`team_city`,`team_side`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- s_core.nba_team_stats definition

CREATE TABLE `nba_team_stats` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                  `team_id` bigint(20) NOT NULL,
                                  `winRate` decimal(5,4) DEFAULT 0.5000,
                                  `offensive_rate` decimal(5,4) DEFAULT 0.5000,
                                  `defensive_rate` decimal(5,4) DEFAULT 0.5000,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;