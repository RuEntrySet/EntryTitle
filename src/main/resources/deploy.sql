CREATE TABLE IF NOT EXISTS `kaiftitles` (`id` VARCHAR(200) NOT NULL, `user` VARCHAR(50) NOT NULL,
`title` TEXT, `state` INT(10) NOT NULL, PRIMARY KEY (`id`, `user`, `state`)) ENGINE = InnoDB;