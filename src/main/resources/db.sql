DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product`(
	id int PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	price double
);