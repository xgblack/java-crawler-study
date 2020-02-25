/*
 Navicat Premium Data Transfer

 Source Server         : 本机MySQL
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : crawlerstu

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 16/02/2020 19:01:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for job_info
-- ----------------------------
DROP TABLE IF EXISTS `job_info`;
CREATE TABLE `job_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `company_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `company_addr` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司联系方式',
  `company_info` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '公司信息',
  `job_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职位名称',
  `job_addr` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作地点',
  `job_info` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '职位信息',
  `salary_min` int(10) NULL DEFAULT NULL COMMENT '薪资范围，最小',
  `salary_max` int(10) NULL DEFAULT NULL COMMENT '薪资范围，最大',
  `url` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '招聘信息详情页',
  `time` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职位最近发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '招聘信息' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
