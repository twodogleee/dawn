/*
 Navicat Premium Data Transfer

 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Schema         : dawn

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 04/09/2020 16:38:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dawn_resources
-- ----------------------------
DROP TABLE IF EXISTS `dawn_resources`;
CREATE TABLE `dawn_resources`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源ID',
  `resources_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源名',
  `resources_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'dawn-资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dawn_resources
-- ----------------------------
INSERT INTO `dawn_resources` VALUES ('1', '管理端', '/admin');
INSERT INTO `dawn_resources` VALUES ('2', '用户相关', '/user');
INSERT INTO `dawn_resources` VALUES ('3', '测试', '/dawn-auth/test/getId');

-- ----------------------------
-- Table structure for dawn_role
-- ----------------------------
DROP TABLE IF EXISTS `dawn_role`;
CREATE TABLE `dawn_role`  (
  `role_id` int(10) NOT NULL COMMENT '角色ID',
  `role_name` varchar(61) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'dawn-角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dawn_role
-- ----------------------------
INSERT INTO `dawn_role` VALUES (1000, 'admin');
INSERT INTO `dawn_role` VALUES (1001, 'user');

-- ----------------------------
-- Table structure for dawn_role_resources
-- ----------------------------
DROP TABLE IF EXISTS `dawn_role_resources`;
CREATE TABLE `dawn_role_resources`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_id` int(10) NULL DEFAULT NULL COMMENT '角色ID',
  `resources_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'dawn-角色与资源对应表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dawn_role_resources
-- ----------------------------
INSERT INTO `dawn_role_resources` VALUES ('1', 1000, '1');
INSERT INTO `dawn_role_resources` VALUES ('2', 1001, '2');
INSERT INTO `dawn_role_resources` VALUES ('3', 1000, '2');
INSERT INTO `dawn_role_resources` VALUES ('4', 1000, '3');

-- ----------------------------
-- Table structure for dawn_user
-- ----------------------------
DROP TABLE IF EXISTS `dawn_user`;
CREATE TABLE `dawn_user`  (
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户登录名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `tel` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
  `enabled` int(2) NULL DEFAULT NULL COMMENT '启用 1true 0false',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'dawn-用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dawn_user
-- ----------------------------
INSERT INTO `dawn_user` VALUES ('123456', 'admin', '$2a$10$ltNP2vV9nsFy6HIO0VDy0ON5JWYDM2C5cM1IFbOR/igaj0iCgVcbq', '管理员', NULL, 1, NULL, NULL);
INSERT INTO `dawn_user` VALUES ('1301080058296332289', 'andersen', '$2a$10$naIVwrvjTI9PrcS9lfU8m.0wDV20P7sv6niQDQ5Szi951tn/NuYPK', 'Andersen', NULL, 1, '2020-09-02 16:50:34', '2020-09-02 16:50:34');

-- ----------------------------
-- Table structure for dawn_user_role
-- ----------------------------
DROP TABLE IF EXISTS `dawn_user_role`;
CREATE TABLE `dawn_user_role`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `roleUnique`(`user_id`, `role_id`) USING BTREE COMMENT 'userId与roleId成唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'dawn-用户与角色对应表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dawn_user_role
-- ----------------------------
INSERT INTO `dawn_user_role` VALUES ('1', '123456', '1000');
INSERT INTO `dawn_user_role` VALUES ('2', '123456', '1001');
INSERT INTO `dawn_user_role` VALUES ('6c704829eb03c5e91d8d992d137a43c0', '1301080058296332289', '1001');

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用于唯一标识每一个客户端(client)；注册时必须填写(也可以服务端自动生成)，这个字段是必须的，实际应用也有叫app_key',
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端能访问的资源id集合，注册客户端时，根据实际需要可选择资源id，也可以根据不同的额注册流程，赋予对应的额资源id',
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '注册填写或者服务端自动生成，实际应用也有叫app_secret, 必须要有前缀代表加密方式',
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '指定client的权限范围，比如读写权限，比如移动端还是web端权限',
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '可选值 授权码模式:authorization_code,密码模式:password,刷新token: refresh_token, 隐式模式: implicit: 客户端模式: client_credentials',
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端重定向uri，authorization_code和implicit需要该值进行校验，注册时填写',
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '指定用户的权限范围，如果授权的过程需要用户登陆，该字段不生效，implicit和client_credentials需要',
  `access_token_validity` int(11) NULL DEFAULT NULL COMMENT '设置access_token的有效时间(秒),默认(606012,12小时)',
  `refresh_token_validity` int(11) NULL DEFAULT NULL COMMENT '设置refresh_token有效期(秒)，默认(606024*30, 30天)',
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '值必须是json格式',
  `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '默认false,适用于authorization_code模式,设置用户是否自动approval操作,设置true跳过用户确认授权操作页面，直接跳到redirect_uri',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'oauth-客户端信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('dawn-gateway', '', '$2a$10$mYTfIc6YDJ2l5vBTsmnvT.eNqCU.nSuTwaRFX1ndjNVVWttBCGcbS', 'all', 'refresh_token,implicit,client_credentials,password,authorization_code', '', '', NULL, NULL, '{}', '');
INSERT INTO `oauth_client_details` VALUES ('web', '', '$2a$10$7tgak24egxHHl5sQXn0DXe5lfxY9RJAByi0TxXIR3N46nEOh3U0kK', 'all', 'implicit,password,refresh_token', '', '', NULL, NULL, '{}', 'true');

SET FOREIGN_KEY_CHECKS = 1;
