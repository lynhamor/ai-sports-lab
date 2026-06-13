CREATE DATABASE IF NOT EXISTS `game_nba`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE game_nba;

CREATE TABLE `team` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `team_city` varchar(100) NOT NULL,
                        `team_code` varchar(10) NOT NULL,
                        `team_name` varchar(100) NOT NULL,
                        `conference` enum('EAST','WEST') NOT NULL,
                        `division` enum('ATLANTIC','CENTRAL','SOUTHEAST','NORTHWEST','PACIFIC','SOUTHWEST') NOT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `nba_team_unique` (`team_name`,`team_city`,`conference`),
                        KEY `team_division_IDX` (`division`,`conference`) USING BTREE,
                        KEY `team_team_code_IDX` (`team_code`) USING BTREE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `team_stats` (
                                  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                                  `team_id` BIGINT(20) NOT NULL,
                                  `win_rate` DECIMAL(5,4) DEFAULT 0.5000,
                                  `offensive_rate` DECIMAL(5,4) DEFAULT 0.5000,
                                  `defensive_rate` DECIMAL(5,4) DEFAULT 0.5000,
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `uk_team_stats_team_id` (`team_id`)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

-- game_nba.`match` definition
CREATE TABLE `match_event` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `season` enum('SPRING','SUMMER','AUTUMN','WINTER') NOT NULL,
                         `season_year` smallint(6) NOT NULL,
                         `event` enum('PRESEASON','REGULAR','PLAYOFFS') NOT NULL DEFAULT 'REGULAR',
                         `game_idex` int(11) NOT NULL,
                         `home_team` bigint(20) NOT NULL,
                         `away_team` bigint(20) NOT NULL,
                         `scheduled_at` datetime(3) DEFAULT NULL,
                         `game_status` enum('UPCOMING','ACTIVE','FINISHED','CANCELLED') NOT NULL,
                         `started_at` datetime(3) DEFAULT NULL,
                         `ended_at` datetime(3) DEFAULT NULL,
                         `seed` longtext NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `match_unique` (`season`,`season_year`,`event`,`game_idex`,`home_team`,`away_team`),
                         KEY `nba_schedule_game_status_IDX` (`game_status`,`season`,`event`,`game_idex`,`home_team`,`scheduled_at`,`away_team`) USING BTREE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_uca1400_ai_ci;

-- game_nba.season_standing definition

CREATE TABLE `season_standing` (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                   `season` enum('SPRING','SUMMER','AUTUMN','WINTER') NOT NULL,
                                   `season_year` smallint(6) NOT NULL,
                                   `team_id` bigint(20) NOT NULL,
                                   `total_match` int(11) DEFAULT 0,
                                   `total_win` int(11) DEFAULT 0,
                                   `total_lose` int(11) NOT NULL DEFAULT 0,
                                   `consecutive_win` int(11) DEFAULT 0,
                                   `consecutive_lose` int(11) DEFAULT 0,
                                   `win_rate` decimal(5,4) DEFAULT 0.0000,
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `season_standing_unique` (`season`,`season_year`,`team_id`),
                                   KEY `season_standing_team_id_IDX` (`team_id`) USING BTREE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;