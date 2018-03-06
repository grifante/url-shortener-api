CREATE TABLE  `user` (
  `id` varchar(50) NOT NULL PRIMARY KEY
);

CREATE TABLE `url` (
  `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL ,
  `hits` bigint(20) NOT NULL DEFAULT '0',
  `url` varchar(2000) NOT NULL,
  `short_url` varchar(300) NOT NULL
);

ALTER TABLE `url`
  ADD UNIQUE KEY `short_url` (`short_url`);

ALTER TABLE `url`
  ADD FOREIGN KEY (`user_id`)
  REFERENCES `user`(`id`)
  ON DELETE CASCADE;  