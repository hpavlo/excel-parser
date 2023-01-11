--liquibase formatted sql
--changeset <pavlogook>:<create-excel-rows-cells-table>

create table excel_rows_cells
(
    excel_row_id bigint not null,
    cells_id     bigint not null,
    constraint FK8ojt8ul8xp07cn22fxm78eam1
        foreign key (excel_row_id) references excel_rows (id),
    constraint FKlsok1sjvbmnlwydb5jtuducde
        foreign key (cells_id) references excel_cells (id)
);

--rollback DROP TABLE excel_rows_cells;
