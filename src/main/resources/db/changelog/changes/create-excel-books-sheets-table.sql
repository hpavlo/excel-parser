--liquibase formatted sql
--changeset <pavlogook>:<create-excel-books-sheets-table>

create table if not exists excel_books_sheets
(
    excel_book_id bigint not null,
    sheets_id     bigint not null,
    constraint UK_nu5m1hldec3fvfm5u3ualhu1f
        unique (sheets_id),
    constraint FKbjetvklvcaa4mtewh944fqnmr
        foreign key (sheets_id) references excel_sheets (id),
    constraint FKrsv83ek0twq06b2c632c5gmyv
        foreign key (excel_book_id) references excel_books (id)
);

--rollback DROP TABLE excel_books_sheets;
