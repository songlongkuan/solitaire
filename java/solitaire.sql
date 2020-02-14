/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云服务器
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : 127.0.0.1:3306
 Source Schema         : solitaire

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 14/02/2020 11:48:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for solitaire_post
-- ----------------------------
DROP TABLE IF EXISTS `solitaire_post`;
CREATE TABLE `solitaire_post` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `mid` bigint NOT NULL DEFAULT '0',
  `if_del` tinyint(1) NOT NULL DEFAULT '0',
  `version` int NOT NULL DEFAULT '0',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `post_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '帖子描述',
  `post_close_pernum` int NOT NULL DEFAULT '0' COMMENT '截止人数',
  `post_close_time` datetime NOT NULL COMMENT '截止时间',
  `post_close_join` tinyint(1) NOT NULL DEFAULT '0' COMMENT '帖子 截止加入  0未截止  1已截止',
  `post_num_join` int NOT NULL DEFAULT '0' COMMENT '帖子加入人数',
  `post_create_umid` bigint NOT NULL DEFAULT '0' COMMENT '帖子创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mid` (`mid`) USING BTREE,
  KEY `if_del` (`if_del`) USING BTREE,
  KEY `create_umid` (`post_create_umid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='接龙帖子';

-- ----------------------------
-- Table structure for solitaire_post_in
-- ----------------------------
DROP TABLE IF EXISTS `solitaire_post_in`;
CREATE TABLE `solitaire_post_in` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `mid` bigint NOT NULL DEFAULT '0',
  `if_del` tinyint(1) NOT NULL DEFAULT '0',
  `version` int NOT NULL DEFAULT '0',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `post_mid` bigint NOT NULL DEFAULT '0' COMMENT '接龙 帖子唯一ID',
  `post_in_umid` bigint NOT NULL DEFAULT '0' COMMENT '加入帖子的用户ID',
  `post_in_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '加入接龙提交的内容',
  `post_order` int NOT NULL DEFAULT '0' COMMENT '当前用户所在接龙的排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mid` (`mid`) USING BTREE,
  KEY `umid` (`post_in_umid`) USING BTREE,
  KEY `post_mid` (`post_mid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for solitaire_user
-- ----------------------------
DROP TABLE IF EXISTS `solitaire_user`;
CREATE TABLE `solitaire_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `mid` bigint NOT NULL DEFAULT '0',
  `if_del` tinyint(1) NOT NULL DEFAULT '0',
  `version` int NOT NULL DEFAULT '0',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `nike_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `wechat_openid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `wechat_union_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `wechat_accesstoken` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `wechat_session_key` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `wechat_avatar` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '微信用户头像',
  `jwt_accesstoken` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'jwt access token',
  `jwt_salt` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'jwt 哈希盐',
  `wechat_city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '微信 -> 城市',
  `wechat_province` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '微信-> 省份',
  `wechat_gender` tinyint NOT NULL DEFAULT '0' COMMENT '微信-> 性别',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mid` (`mid`) USING BTREE,
  UNIQUE KEY `openid` (`wechat_openid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

SET FOREIGN_KEY_CHECKS = 1;
