CREATE TABLE  IF NOT EXISTS `user_role` (
    `id`          INTEGER  PRIMARY KEY AUTO_INCREMENT,
    `user_role`   VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS `form_status` (
    `id`           INTEGER  PRIMARY KEY AUTO_INCREMENT,
    `form_status`  VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS `users` (
    `id`            INTEGER  PRIMARY KEY AUTO_INCREMENT,
    `first_name`    VARCHAR(100) NOT NULL,
    `last_name`     VARCHAR(100) NOT NULL,
    `email`         VARCHAR(150) NOT NULL,
    `username`      VARCHAR(50) NOT NULL,
    `password`      VARCHAR(255) NOT NULL,
    `password_hash` VARCHAR(255) NOT NULL,
    `user_role`     INTEGER NOT NULL,
    foreign key (`user_role`) references `user_role`(`id`)
);




CREATE TABLE IF NOT EXISTS `comments` (
    `id`              INTEGER  PRIMARY KEY AUTO_INCREMENT,
    `content`         VARCHAR(200) NOT NULL,
    `author`          INTEGER NOT NULL,
    `likes`           INTEGER,
    `dislikes`           INTEGER,
    foreign key (`author`) references `users`(`id`)
)