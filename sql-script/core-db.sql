CREATE DATABASE IF NOT EXISTS `core`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

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
                                  UNIQUE KEY `uk_team_stats_team_id` (`team_id`),
                                  CONSTRAINT fk_team_stats_team
                                      FOREIGN KEY (`team_id`) REFERENCES nba_team(`id`)
                                          ON DELETE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `game` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `game_name` varchar(100) NOT NULL,
                        `game_category` varchar(100) NOT NULL,
                        `is_enable` tinyint(1) DEFAULT 1,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `game_rule` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `game_id` bigint(20) NOT NULL,
                             `rule_key` varchar(100) NOT NULL,
                             `rule_value` longtext NOT NULL,
                             PRIMARY KEY (`id`),
                             CONSTRAINT fk_game_rule_game
                                 FOREIGN KEY (`game_id`) REFERENCES game(`id`)
                                     ON DELETE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

CREATE TABLE callback_outbox (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 aggregate_id BIGINT NOT NULL,
                                 event_type VARCHAR(100),
                                 payload JSON,
                                 status VARCHAR(20) DEFAULT 'PENDING',
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);