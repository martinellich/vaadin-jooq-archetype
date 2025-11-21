create domain username as varchar(100);

create table "user"
(
    username        username     not null primary key,
    first_name      varchar(100) not null,
    last_name       varchar(100) not null,
    hashed_password varchar      not null,
    picture         bytea
);

create table user_role
(
    username username     not null,
    role     varchar(100) not null,

    primary key (username, role)
);