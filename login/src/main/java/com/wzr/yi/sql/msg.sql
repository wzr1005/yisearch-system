CREATE TABLE `message` (
       `msgId` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息id，⾃增长',
       `msgType` int(11) DEFAULT NULL COMMENT '类型 1：⽂字；47：emoji；43：⾳频；436207665：红包；49：⽂件；48：位置；3：图⽚',
       `isSend` int(11) DEFAULT NULL COMMENT '是否是⾃⼰发送 0：不是；1：是',
       `createTime` datetime COMMENT '消息发送时间',
       `content` text COMMENT '消息格式【发信⼈id:内容】',
       `talker` varchar(55) DEFAULT NULL COMMENT '聊天对象。群聊，则是群id（xxx@chatroom）；⼀对⼀，聊天对象的唯⼀标识。',
       PRIMARY KEY (`msgId`),
       KEY `index_chat_id` (`talker`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4