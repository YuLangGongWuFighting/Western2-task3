/*
 Navicat Premium Data Transfer

 Source Server         : western2
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : western2_3

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 18/01/2022 18:09:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for basicInformation
-- ----------------------------
DROP TABLE IF EXISTS `basicInformation`;
CREATE TABLE `basicInformation` (
  `name` varchar(60) NOT NULL,
  `iD` varchar(60) NOT NULL,
  `lat` decimal(10,0) NOT NULL,
  `lon` decimal(10,0) NOT NULL,
  PRIMARY KEY (`iD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of basicInformation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for weatherInformation
-- ----------------------------
DROP TABLE IF EXISTS `weatherInformation`;
CREATE TABLE `weatherInformation` (
  `iD` varchar(60) NOT NULL,
  `fxDate` date NOT NULL,
  `tempMax` decimal(10,0) NOT NULL,
  `tempMin` decimal(10,0) NOT NULL,
  `textDay` varchar(60) NOT NULL,
  PRIMARY KEY (`iD`,`fxDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of weatherInformation
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
