ALTER TABLE mb_member
    ADD member_type char(8) not null,
    ADD join_route char(8) not null,
    ADD last_login_ymdt datetime null,
    ADD login_count int(11) not null;
