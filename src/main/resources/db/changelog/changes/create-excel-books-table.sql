--liquibase formatted sql
--changeset <pavlogook>:<create-excel-books-table>

create table excel_books
(
    id                bigint auto_increment
        primary key,
    name              varchar(255) null,
    date_time_history datetime(6)  null
);

--rollback DROP TABLE excel_books;
