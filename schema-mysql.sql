CREATE TABLE IF NOT EXISTS `url` (
  `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `hits` bigint(20) NOT NULL DEFAULT '0',
  `url` varchar(2000) NOT NULL,
  `short_url` varchar(300) NOT NULL
) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS `user` (
  `id` varchar(50) NOT NULL PRIMARY KEY
) ENGINE=InnoDB;


ALTER TABLE `url`
  ADD UNIQUE KEY `short_url` (`short_url`),
  ADD KEY `fk_url_user` (`user_id`);
  
ALTER TABLE `url`
  ADD CONSTRAINT `fk_url_user` 
  FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE CASCADE;
