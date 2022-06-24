CREATE TABLE `recontact` (
    `userId` varchar(45) NOT NULL COMMENT '联系⼈用户id',
  `username` varchar(45) NOT NULL COMMENT '联系⼈唯⼀标识',
  `nickname` varchar(45) DEFAULT NULL COMMENT '联系⼈昵称',
  `remark` varchar(45) DEFAULT NULL COMMENT '备注名称',
  `avatar` varchar(200) DEFAULT NULL COMMENT '联系⼈头像地址',
  `is_friend` varchar(45) DEFAULT NULL COMMENT '是否是好友。1,3 好友 4；群⾥⾮好友',
PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='联系⼈，⾮好友';