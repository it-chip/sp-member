-- auto-generated definition
DROP TABLE IF EXISTS `mb_member`;
CREATE TABLE mb_member
(
    `member_no`                  int(11)    NOT NULL AUTO_INCREMENT,
    `email`                        varchar(100)         NULL,
    `password`                     varchar(200)         NULL,
    `nickname`                     varchar(45)          NULL,
    `join_ymdt`                    datetime             NULL,
    PRIMARY KEY (`member_no`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '회원 정보';

