
CREATE TABLE `t_user`
(
    `id`         binary(16)   NOT NULL,
    `email`      varchar(255) NOT NULL,
    `first_name` varchar(255)          DEFAULT NULL,
    `last_name`  varchar(255)          DEFAULT NULL,
    `password`   varchar(255) NOT NULL,
    `phone`      varchar(255)          DEFAULT NULL,
    `role`       enum ('ADMIN','USER') DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_i6qjjoe560mee5ajdg7v1o6mi` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `t_task`
(
    `id`                 binary(16)  NOT NULL,
    `created_time`       datetime(6) NOT NULL,
    `deadline`           date                                 DEFAULT NULL,
    `description`        varchar(255)                         DEFAULT NULL,
    `last_modified_time` datetime(6)                          DEFAULT NULL,
    `task_stats`         enum ('Closed','Completed','Opened') DEFAULT NULL,
    `title`              varchar(255)                         DEFAULT NULL,
    `user_id`            binary(16)  NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_k7wsvv5n8a66gsr1mndqv1h6l` (`user_id`), -- Fixed key name (lowercase)
    CONSTRAINT `FK_k7wsvv5n8a66gsr1mndqv1h6l` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `assigned_task_user`
(
    `task_id` binary(16) NOT NULL,
    `user_id` binary(16) NOT NULL,
    PRIMARY KEY (`task_id`, `user_id`),
    KEY `FK_t9yukvsx3tavtwmixiiw451em` (`user_id`),
    CONSTRAINT `FK_6p9spv31gslbs8tu6oe6sc4ji` FOREIGN KEY (`task_id`) REFERENCES `t_task` (`id`) ON DELETE CASCADE,
    CONSTRAINT `FK_t9yukvsx3tavtwmixiiw451em` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;