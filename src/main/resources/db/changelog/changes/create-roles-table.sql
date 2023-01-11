--liquibase formatted sql
--changeset <pavlogook>:<create-roles-table>

create table roles
(
    id        bigint auto_increment
        primary key,
    role_name varchar(255) null
);

--rollback DROP TABLE roles;
