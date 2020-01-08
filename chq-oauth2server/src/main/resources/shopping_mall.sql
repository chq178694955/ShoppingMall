/*
Navicat MySQL Data Transfer

Source Server         : local_mysql
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : shopping_mall

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2019-12-25 18:50:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for oauth2_client
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_client`;
CREATE TABLE `oauth2_client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_name` varchar(100) DEFAULT NULL COMMENT '客戶端名稱',
  `client_id` varchar(100) DEFAULT NULL COMMENT '客戶端ID',
  `client_secret` varchar(100) DEFAULT NULL COMMENT '客户端安全key',
  PRIMARY KEY (`id`),
  KEY `idx_oauth2_client_client_id` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth2_client
-- ----------------------------
INSERT INTO `oauth2_client` VALUES ('1', 'oauth-client', 'c1ebe466-1cdc-4bd3-ab69-77c3561b9dee', 'd8346ea2-6017-43ed-ad68-19c0f971738b');
INSERT INTO `oauth2_client` VALUES ('2', 'kekexiaocheng', 'Igupf8ca', '3180d4fdc4733638bdc4296ef6cf47ed859dbe56');
INSERT INTO `oauth2_client` VALUES ('3', 'wasion', 'Eotyf0d4', '84ce88b2d9b974c41aa649df204f36e71fadaa7c');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `dict_sn` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `class_no` int(4) NOT NULL COMMENT '分类',
  `dict_no` int(4) NOT NULL COMMENT '字典值',
  `dict_desc` varchar(120) NOT NULL COMMENT '字典描述',
  `disp_order` int(4) DEFAULT '0' COMMENT '字典排序',
  `parent_sn` varchar(255) NOT NULL COMMENT '父ID',
  `use_flag` varchar(255) DEFAULT '1' COMMENT '是否使用 0-未使用 1-使用',
  PRIMARY KEY (`dict_sn`)
) ENGINE=InnoDB AUTO_INCREMENT=313 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('0', '-1', '1', '所有字典大类', '1', '-1', '1');
INSERT INTO `sys_dict` VALUES ('100', '100', '-1', '用户状态', '0', '0', '1');
INSERT INTO `sys_dict` VALUES ('101', '100', '0', '正常', '1', '100', '1');
INSERT INTO `sys_dict` VALUES ('102', '100', '1', '锁定', '2', '100', '1');
INSERT INTO `sys_dict` VALUES ('300', '300', '-1', '资源类型', '0', '0', '1');
INSERT INTO `sys_dict` VALUES ('301', '300', '0', '菜单', '1', '300', '1');
INSERT INTO `sys_dict` VALUES ('302', '300', '1', 'TAB页', '2', '300', '1');
INSERT INTO `sys_dict` VALUES ('303', '300', '2', '按钮', '3', '300', '1');
INSERT INTO `sys_dict` VALUES ('310', '310', '-1', '是否使用', '0', '0', '1');
INSERT INTO `sys_dict` VALUES ('311', '311', '0', '停用', '1', '310', '1');
INSERT INTO `sys_dict` VALUES ('312', '312', '1', '使用', '2', '310', '1');

-- ----------------------------
-- Table structure for sys_resources
-- ----------------------------
DROP TABLE IF EXISTS `sys_resources`;
CREATE TABLE `sys_resources` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL DEFAULT '' COMMENT '资源名称-唯一',
  `url` varchar(255) DEFAULT NULL,
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '资源类型 0-菜单 1-tab页 2-按钮',
  `pid` bigint(11) NOT NULL,
  `permission` varchar(60) DEFAULT NULL COMMENT '权限标志（格式：模块_操作）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_resources_name_unique_idx` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_resources
-- ----------------------------
INSERT INTO `sys_resources` VALUES ('1', '系统管理', '#', '0', '-1', '');
INSERT INTO `sys_resources` VALUES ('2', '用户管理', 'sys/user/toMain', '0', '1', '');
INSERT INTO `sys_resources` VALUES ('3', '角色管理', 'sys/role/toMain', '0', '1', '');
INSERT INTO `sys_resources` VALUES ('4', '资源管理', 'sys/resource/toMain', '0', '1', '');
INSERT INTO `sys_resources` VALUES ('5', '新增', '#', '2', '2', 'user_add');
INSERT INTO `sys_resources` VALUES ('6', '修改', '#', '2', '2', 'user_update');
INSERT INTO `sys_resources` VALUES ('7', '删除', '#', '2', '2', 'user_delete');
INSERT INTO `sys_resources` VALUES ('8', '查询', '#', '2', '2', 'user_query');
INSERT INTO `sys_resources` VALUES ('9', '第三方', '#', '0', '-1', '');
INSERT INTO `sys_resources` VALUES ('10', '客户端管理', 'sys/client/toMain', '0', '9', '');
INSERT INTO `sys_resources` VALUES ('15', '字典管理', 'sys/dict/toMain', '0', '1', null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员');
INSERT INTO `sys_role` VALUES ('2', '小区管理员');
INSERT INTO `sys_role` VALUES ('3', '威胜科技园');

-- ----------------------------
-- Table structure for sys_role_resources
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resources`;
CREATE TABLE `sys_role_resources` (
  `role_id` bigint(11) NOT NULL,
  `res_id` bigint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role_resources
-- ----------------------------
INSERT INTO `sys_role_resources` VALUES ('1', '1');
INSERT INTO `sys_role_resources` VALUES ('1', '2');
INSERT INTO `sys_role_resources` VALUES ('1', '3');
INSERT INTO `sys_role_resources` VALUES ('1', '4');
INSERT INTO `sys_role_resources` VALUES ('1', '5');
INSERT INTO `sys_role_resources` VALUES ('1', '6');
INSERT INTO `sys_role_resources` VALUES ('1', '7');
INSERT INTO `sys_role_resources` VALUES ('1', '8');
INSERT INTO `sys_role_resources` VALUES ('1', '9');
INSERT INTO `sys_role_resources` VALUES ('1', '10');
INSERT INTO `sys_role_resources` VALUES ('1', '15');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT '' COMMENT '用户名',
  `password` varchar(200) DEFAULT NULL COMMENT '登录密码',
  `name` varchar(60) DEFAULT NULL COMMENT '用户真实姓名',
  `id_card_num` varchar(60) DEFAULT NULL COMMENT '用户身份证号',
  `state` int(1) DEFAULT '0' COMMENT '用户状态：0:正常状态,1：用户被锁定',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `id_card_num` (`id_card_num`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '曹浩权', '43052119880510003X', '1');
INSERT INTO `sys_user` VALUES ('5', 'caohaoquan', 'e10adc3949ba59abbe56e057f20f883e', '曹', '3213123312', '0');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `uid` bigint(11) NOT NULL,
  `rid` bigint(11) NOT NULL,
  PRIMARY KEY (`uid`,`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
