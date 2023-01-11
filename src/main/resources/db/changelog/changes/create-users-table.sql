--liquibase formatted sql
--changeset <pavlogook>:<create-users-table>

create table users
(
    id       bigint auto_increment
        primary key,
    password varchar(255) null,
    username varchar(255) not null,
    constraint UK_r43af9ap4edm43mmtq01oddj6
        unique (username)
);

--rollback DROP TABLE users;
