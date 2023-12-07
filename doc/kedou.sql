/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : kedou

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 07/12/2023 13:06:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_user` bigint NULL DEFAULT NULL COMMENT '发评论用户ID',
  `video_id` bigint NULL DEFAULT NULL COMMENT '视频ID',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '内容',
  `likes` bigint NULL DEFAULT 0 COMMENT '点赞数',
  `dislikes` bigint NULL DEFAULT 0 COMMENT '点踩数',
  `is_delete` int NULL DEFAULT 0 COMMENT '0未删除 1删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_comment_user`(`create_user` ASC) USING BTREE,
  INDEX `fk_comment_video`(`video_id` ASC) USING BTREE,
  CONSTRAINT `fk_comment_user` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_comment_video` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for likes
-- ----------------------------
DROP TABLE IF EXISTS `likes`;
CREATE TABLE `likes`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_user` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `video_id` bigint NULL DEFAULT NULL COMMENT '视频ID',
  `is_delete` int NULL DEFAULT 0,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_likes_user`(`create_user` ASC) USING BTREE,
  INDEX `fk_likes_video`(`video_id` ASC) USING BTREE,
  CONSTRAINT `fk_likes_user` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_likes_video` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `session` bigint NULL DEFAULT NULL COMMENT '会话ID',
  `create_user` bigint NULL DEFAULT NULL COMMENT '发送人',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '消息内容',
  `video_id` bigint NULL DEFAULT NULL COMMENT '分享视频id 为空则是普通消息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_message_sender`(`create_user` ASC) USING BTREE,
  INDEX `fk_message_video`(`video_id` ASC) USING BTREE,
  INDEX `session`(`session` ASC) USING BTREE,
  CONSTRAINT `fk_message_sender` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_message_session` FOREIGN KEY (`session`) REFERENCES `session` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_message_video` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for relation
-- ----------------------------
DROP TABLE IF EXISTS `relation`;
CREATE TABLE `relation`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_user` bigint NULL DEFAULT NULL COMMENT '关注者',
  `follow_id` bigint NULL DEFAULT NULL COMMENT '被关注者',
  `is_delete` int NULL DEFAULT 0,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_relation_user`(`create_user` ASC) USING BTREE,
  INDEX `fk_relation_follow`(`follow_id` ASC) USING BTREE,
  CONSTRAINT `fk_relation_follow` FOREIGN KEY (`follow_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_relation_user` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for session
-- ----------------------------
DROP TABLE IF EXISTS `session`;
CREATE TABLE `session`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_a` bigint NULL DEFAULT NULL COMMENT '参与会话用户A的ID，小ID在前',
  `user_b` bigint NULL DEFAULT NULL COMMENT '参与会话用户B的ID',
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_session_userA`(`user_a` ASC) USING BTREE,
  INDEX `fk_session_userB`(`user_b` ASC) USING BTREE,
  CONSTRAINT `fk_session_userA` FOREIGN KEY (`user_a`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_session_userB` FOREIGN KEY (`user_b`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for star
-- ----------------------------
DROP TABLE IF EXISTS `star`;
CREATE TABLE `star`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_user` bigint NULL DEFAULT NULL,
  `video_id` bigint NULL DEFAULT NULL,
  `is_delete` int NULL DEFAULT 0,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_star_user`(`create_user` ASC) USING BTREE,
  INDEX `fk_star_video`(`video_id` ASC) USING BTREE,
  CONSTRAINT `fk_star_user` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_star_video` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `username` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户账号',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `profile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `likes` bigint NULL DEFAULT 0 COMMENT '获赞',
  `friends` bigint NULL DEFAULT 0 COMMENT '朋友',
  `following` bigint NULL DEFAULT 0 COMMENT '关注',
  `follower` bigint NULL DEFAULT 0 COMMENT '粉丝',
  `bio` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '简介',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sex` int NULL DEFAULT NULL COMMENT '男0 女1',
  `birthday` date NULL DEFAULT NULL,
  `school` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT 1 COMMENT '账号状态 0封禁1正常 2信息待完善',
  `is_delete` int NULL DEFAULT 0 COMMENT '账号是否删除 0正常 1销号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '注册时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `show_likes` int UNSIGNED NULL DEFAULT 1 COMMENT '是否展示喜欢列表 0不展示 1展示',
  `show_stars` int NULL DEFAULT 1 COMMENT '是否展示收藏列表 0不展示 1展示',
  `show_follower` int NULL DEFAULT 1 COMMENT '是否展示粉丝列表 0不展示 1展示',
  `show_following` int NULL DEFAULT 1 COMMENT '是否展示关注列表 0不展示 1展示',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '视频ID',
  `create_user` bigint NULL DEFAULT NULL COMMENT '作者',
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '视频地址',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面地址',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `likes` bigint NULL DEFAULT 0 COMMENT '喜欢',
  `comments` bigint NULL DEFAULT 0 COMMENT '评论',
  `stars` bigint NULL DEFAULT 0 COMMENT '收藏',
  `shares` bigint(20) UNSIGNED ZEROFILL NULL DEFAULT 00000000000000000000 COMMENT '分享',
  `label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签名列表（以空格分隔）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` int NULL DEFAULT 0 COMMENT '视频状态 0待审核 1审核未通过 2已发布 3封禁 ',
  `is_delete` int NULL DEFAULT 0 COMMENT '视频删除状态 0未删除 1删除',
  `open` int NULL DEFAULT 1 COMMENT '公开状态 0私密 1公开',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_video_user`(`create_user` ASC) USING BTREE,
  CONSTRAINT `fk_video_user` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
