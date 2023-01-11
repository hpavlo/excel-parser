--liquibase formatted sql
--changeset <pavlogook>:<create-excel-sheets-table>

create table excel_sheets
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

--rollback DROP TABLE excel_sheets;
