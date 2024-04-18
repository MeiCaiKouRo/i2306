
-- 对应课程里面的数据库名为： test1


DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名称',
  `telephone` varchar(13) NOT NULL DEFAULT '' COMMENT '手机号',
  `mail` varchar(20) NOT NULL DEFAULT '' COMMENT '邮箱',
  `password` varchar(40) NOT NULL DEFAULT '' COMMENT '加密后的密码',
  `dept_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户所在部门的id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，1：正常，0：冻结状态，2：删除',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次更新者的ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


DROP TABLE IF EXISTS `train_city`;
CREATE TABLE `train_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

INSERT INTO `train_city` VALUES (1, '北京');
INSERT INTO `train_city` VALUES (2, '唐山');
INSERT INTO `train_city` VALUES (3, '锦州');
INSERT INTO `train_city` VALUES (4, '盘锦');
INSERT INTO `train_city` VALUES (5, '鲅鱼圈');
INSERT INTO `train_city` VALUES (6, '大连');
INSERT INTO `train_city` VALUES (7, '瓦房店');
INSERT INTO `train_city` VALUES (8, '普兰店');
INSERT INTO `train_city` VALUES (9, '沈阳');
INSERT INTO `train_city` VALUES (10, 'Test');


DROP TABLE IF EXISTS `train_number`;
CREATE TABLE `train_number` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '车次',
  `from_station_id` int(11) NOT NULL DEFAULT '0' COMMENT '始发站',
  `from_city_id` int(11) NOT NULL DEFAULT '0' COMMENT '始发城市',
  `to_station_id` int(11) NOT NULL DEFAULT '0' COMMENT '终点站',
  `to_city_id` int(11) NOT NULL DEFAULT '0' COMMENT '终点城市',
  `train_type` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '座位类型，CRH2,CRH5',
  `type` smallint(6) NOT NULL DEFAULT '0' COMMENT '类型，1：高铁，2：动车，3：特快，4：普快',
  `seat_num` int(11) NOT NULL DEFAULT '0' COMMENT '车厢数量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


INSERT INTO `train_number` VALUES (1, 'D386', 1, 1, 11, 6, 'CRH2', 1, 1221);
INSERT INTO `train_number` VALUES (2, 'D388', 1, 1, 12, 6, 'CRH2', 1, 1220);
INSERT INTO `train_number` VALUES (3, 'D387', 1, 1, 12, 6, 'CRH5', 2, 1241);
INSERT INTO `train_number` VALUES (5, 'D389', 1, 1, 12, 6, 'CRH2', 1, 1220);
INSERT INTO `train_number` VALUES (6, 'D123', 1, 1, 12, 6, 'CRH5', 1, 1244);
INSERT INTO `train_number` VALUES (7, 'D234', 1, 1, 13, 9, 'CRH2', 1, 1220);
INSERT INTO `train_number` VALUES (8, 'Test', 1, 1, 11, 6, 'CRH5', 2, 1241);



DROP TABLE IF EXISTS `train_number_detail`;
CREATE TABLE `train_number_detail` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `train_number_id` int(11) NOT NULL DEFAULT '0' COMMENT '车次',
  `from_station_id` int(11) NOT NULL DEFAULT '0' COMMENT '上一站',
  `from_city_id` int(11) NOT NULL DEFAULT '0' COMMENT '上一站所在的城市',
  `to_station_id` int(11) NOT NULL COMMENT '到站',
  `to_city_id` int(11) NOT NULL DEFAULT '0' COMMENT '到站所在的城市',
  `station_index` int(11) NOT NULL DEFAULT '0' COMMENT '在整个车次中的顺序',
  `relative_minute` int(11) NOT NULL DEFAULT '0' COMMENT '相对出发时间的分钟数',
  `wait_minute` int(11) NOT NULL DEFAULT '0' COMMENT '到此站等待时间',
  `money` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '价格',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_numberid_index` (`train_number_id`,`station_index`) USING BTREE,
  UNIQUE KEY `uniq_numberid_from` (`train_number_id`,`from_station_id`) USING BTREE,
  UNIQUE KEY `uniq_numberid_to` (`train_number_id`,`to_station_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=236 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


INSERT INTO `train_number_detail` VALUES (227, 1, 1, 1, 5, 2, 0, 120, 3, '0:50,1:20,2:10');
INSERT INTO `train_number_detail` VALUES (228, 1, 5, 2, 7, 3, 1, 120, 3, '0:50,1:20,2:10');
INSERT INTO `train_number_detail` VALUES (229, 1, 7, 3, 10, 5, 2, 120, 3, '0:50,1:20,2:10');
INSERT INTO `train_number_detail` VALUES (230, 1, 10, 5, 11, 6, 3, 60, 7, '0:50,1:20,2:10');
INSERT INTO `train_number_detail` VALUES (234, 8, 1, 1, 6, 3, 0, 120, 5, '0:150,1:120,2:110');
INSERT INTO `train_number_detail` VALUES (235, 8, 6, 3, 11, 6, 1, 120, 5, '0:140,1:110,2:100');


DROP TABLE IF EXISTS `train_seat`;
CREATE TABLE `train_seat` (
  `id` bigint(21) NOT NULL AUTO_INCREMENT,
  `ticket` varchar(60) COLLATE utf8mb4_bin NOT NULL COMMENT '日期，yyyyMMdd',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '登录用户id',
  `traveller_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '绑定的乘客id',
  `train_number_id` int(11) NOT NULL DEFAULT '0' COMMENT '车次',
  `carriage_number` int(11) NOT NULL COMMENT '车厢',
  `row_number` int(11) NOT NULL DEFAULT '0' COMMENT '排',
  `seat_number` int(11) NOT NULL DEFAULT '0' COMMENT 'A~F',
  `seat_level` int(11) NOT NULL DEFAULT '2' COMMENT '座位等级，0：特等座，1：一等座，2：二等座，3：无座',
  `train_start` datetime NOT NULL COMMENT '发车时间',
  `train_end` datetime NOT NULL COMMENT '到达时间',
  `money` int(11) NOT NULL DEFAULT '0' COMMENT '价格',
  `show_number` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '展示',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态，0:初始，1：已放票，2：占有票等待支付，3：已支付，4：不外放',
  `from_station_id` int(11) NOT NULL DEFAULT '0',
  `to_station_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_trainnumberid_ticket` (`train_number_id`,`ticket`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


DROP TABLE IF EXISTS `train_station`;
CREATE TABLE `train_station` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `city_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`) USING BTREE,
  KEY `idx_cityid` (`city_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


INSERT INTO `train_station` VALUES (1, '北京站', 1);
INSERT INTO `train_station` VALUES (2, '北京北站', 1);
INSERT INTO `train_station` VALUES (3, '北京南站', 1);
INSERT INTO `train_station` VALUES (4, '北京西站', 1);
INSERT INTO `train_station` VALUES (5, '唐山站', 2);
INSERT INTO `train_station` VALUES (6, '锦州南站', 3);
INSERT INTO `train_station` VALUES (7, '锦州站', 3);
INSERT INTO `train_station` VALUES (8, '盘锦站', 4);
INSERT INTO `train_station` VALUES (9, '盘锦北站', 4);
INSERT INTO `train_station` VALUES (10, '鲅鱼圈站', 5);
INSERT INTO `train_station` VALUES (11, '大连站', 6);
INSERT INTO `train_station` VALUES (12, '大连北站', 6);
INSERT INTO `train_station` VALUES (13, '沈阳北站', 9);
INSERT INTO `train_station` VALUES (14, 'TestStation', 10);

DROP TABLE IF EXISTS `train_traveller`;
CREATE TABLE `train_traveller` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '名字',
  `adult_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '成人标识，0:成人，1:儿童',
  `sex` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别，0:未知，1:男，2:女',
  `id_type` smallint(6) NOT NULL COMMENT '证件类型，0:未知，1：身份证，2：护照',
  `id_number` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '证件号码',
  `contact` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '联系方式',
  `address` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '联系地址',
  `email` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


INSERT INTO `train_traveller` VALUES (1, '测试成人', 0, 1, 1, '210283198801035535', '123456789', 'test test test', 'test@test.com');
INSERT INTO `train_traveller` VALUES (2, '测试儿童', 1, 1, 1, '210283198801031234', '123456789', 'test test test', 'test@t.com');


DROP TABLE IF EXISTS `train_user`;
CREATE TABLE `train_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '姓名',
  `password` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '密码',
  `telephone` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '手机号',
  `mail` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '邮箱',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态，0:申请中，1：审核通过，2：禁止登陆',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_name` (`name`) USING BTREE,
  UNIQUE KEY `idx_telephone` (`telephone`) USING BTREE,
  UNIQUE KEY `idx_mail` (`mail`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

INSERT INTO `train_user` VALUES (1, 'test', '25D55AD283AA400AF464C76D713C07AD', '123456789', 'test@test.com', 1);


DROP TABLE IF EXISTS `train_user_traveller`;
CREATE TABLE `train_user_traveller` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL DEFAULT '0',
  `traveller_id` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_userid` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

INSERT INTO `train_user_traveller` VALUES (1, 1, 1);
INSERT INTO `train_user_traveller` VALUES (2, 1, 2);

