create sequence person_seq start with 1000;

create domain person_id as bigint;

create table person
(
    id            person_id not null default nextval('person_seq') primary key,
    version       bigint    not null,
    first_name    varchar   not null,
    last_name     varchar   not null,
    email         varchar   not null,
    phone         varchar   not null,
    date_of_birth date      not null,
    occupation    varchar   not null,
    role          varchar   not null,
    important     bool      not null default false
);