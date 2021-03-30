ALTER TABLE mb_member
    MODIFY email varchar(100) not null,
    MODIFY password varchar(50) not null,
    MODIFY join_ymdt datetime not null;
