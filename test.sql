
CREATE DATABASE db03;
CREATE DATABASE db04;
USE db03;
CREATE TABLE `book`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `version` tinyint(1) DEFAULT NULL,
  `book_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `add_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


INSERT INTO `book` VALUES (1, NULL, '算法导论', 1, '2021-06-01 09:55:09');
INSERT INTO `book` VALUES (2, NULL, '代码审计', 1, '2021-06-23 10:41:25');

USE db04;

CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `version` tinyint(1) DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `add_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `user` VALUES (1, NULL, 'lanweihong', 1, '2020-01-29 09:55:44');
INSERT INTO `user` VALUES (2, NULL, 'zhangsan', 1, '2020-01-30 10:41:00');