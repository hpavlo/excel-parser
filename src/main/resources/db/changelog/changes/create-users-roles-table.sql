--liquibase formatted sql
--changeset <pavlogook>:<create-users-roles-table>

create table users_roles
(
    user_id  bigint not null,
    roles_id bigint not null,
    primary key (user_id, roles_id),
    constraint FK2o0jvgh89lemvvo17cbqvdxaa
        foreign key (user_id) references users (id),
    constraint FKa62j07k5mhgifpp955h37ponj
        foreign key (roles_id) references roles (id)
);

--rollback DROP TABLE users_roles;
