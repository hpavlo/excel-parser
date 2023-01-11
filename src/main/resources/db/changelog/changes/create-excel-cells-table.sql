--liquibase formatted sql
--changeset <pavlogook>:<create-excel-cells-table>

create table excel_cells
(
    id           bigint auto_increment
        primary key,
    column_index int          null,
    data         varchar(255) null,
    type         int          null
);

--rollback DROP TABLE excel_cells;
