--liquibase formatted sql
--changeset <pavlogook>:<create-excel-rows-table>

create table excel_rows
(
    id              bigint auto_increment
        primary key,
    last_cell_index int null,
    row_index       int null
);

--rollback DROP TABLE excel_rows;
