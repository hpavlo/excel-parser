--liquibase formatted sql
--changeset <pavlogook>:<create-excel-sheets-rows-table>

create table excel_sheets_rows
(
    excel_sheet_id bigint not null,
    rows_id        bigint not null,
    constraint UK_3owqwl3xqektvoh6hkk3n3ysv
        unique (rows_id),
    constraint FKd8i6429ap3pb7yudettvb4tmu
        foreign key (excel_sheet_id) references excel_sheets (id),
    constraint FKn5e1s08hrw2vgom6i3ylue87t
        foreign key (rows_id) references excel_rows (id)
);

--rollback DROP TABLE excel_sheets_rows;
