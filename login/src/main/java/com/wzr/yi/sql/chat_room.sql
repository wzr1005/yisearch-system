CREATE TABLE `chat_room` (
  `chat_room_name` varchar(30) CHARACTERSET utf8mb4 NOTNULL,
  `member_list` text CHARACTERSET utf8mb4 COMMENT '群组成员id列表，分号分割。a53255001;nan1242;jiabailo002',
  `display_name_list` text CHARACTER SET utf8mb4 COMMENT '群成员昵称列表【中⽂顿号分割】海、⼆、⽼僧、刘伟、齐彬、⽑、Echo、曹',
  `room_owner` varchar(45) CHARACTER SET utf8mb4 DEFAULTNULL COMMENT '群主id',
  `self_display_name` varchar(45) CHARACTER SET utf8mb4 DEFAULTNULL COMMENT '⾃⼰在群⾥的⾃定义群昵称',
  `chat_room_nick` varchar(45) CHARACTER SET utf8mb4 DEFAULTNULL COMMENT '群昵称，没有⾃定义群昵称则从display_name中截取20个字符作为群昵称。',
PRIMARYKEY (`chat_room_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='群组';