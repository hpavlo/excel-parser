--liquibase formatted sql
--changeset <pavlogook>:<add-user-role-to-roles-table>

insert into roles (role_name) values ('USER');

--rollback delete from roles where role_name = 'USER';
