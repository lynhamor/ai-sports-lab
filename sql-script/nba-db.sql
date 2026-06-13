CREATE DATABASE IF NOT EXISTS `game_nba`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE game_nba;

CREATE TABLE `nba_team` (
                            `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                            `team_city` VARCHAR(100) NOT NULL,
                            `team_name` VARCHAR(100) NOT NULL,
                            `conference_side` VARCHAR(100) NOT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `nba_team_unique` (`team_name`, `team_city`, `conference_side`)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `nba_team_stats` (
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

-- core.nba_schedule definition

CREATE TABLE `nba_schedule` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `season_id` bigint(20) NOT NULL,
                                `event` enum('REGULAR','PLAYOFFS') NOT NULL DEFAULT 'REGULAR',
                                `game_idex` int(11) NOT NULL,
                                `home_team` bigint(20) NOT NULL,
                                `away_team` bigint(20) NOT NULL,
                                `scheduled_at` datetime(3) DEFAULT NULL,
                                `game_status` enum('SCHEDULED','READY','RUNNING','FINISHED','CANCELLED') NOT NULL,
                                `started_at` datetime(3) DEFAULT NULL,
                                `ended_at` datetime(3) DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `nba_schedule_unique` (`season_id`,`game_idex`,`home_team`,`away_team`),
                                KEY `nba_schedule_game_status_IDX` (`game_status`,`season_id`,`event`,`game_idex`,`home_team`,`scheduled_at`,`away_team`) USING BTREE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_uca1400_ai_ci;