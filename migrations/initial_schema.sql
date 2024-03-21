--liquibase formatted sql

--changeset dmitriykosolobov:1
--comment: Create Chats table
create table Chats (
    id bigserial primary key,
    tg_chat_id bigint not null unique
);

--changeset dmitriykosolobov:2
--comment: Create Links table
create table Links (
    id bigserial primary key,
    url varchar(255) not null unique,
    last_check_time timestamp with time zone default now() not null
);

--changeset dmitriykosolobov:3
--comment: Create ChatsLinks table
create table ChatsLinks (
    id bigserial primary key,
    chat_id bigint not null references Chats (id) on delete cascade,
    link_id bigint not null references Links (id),
    unique (chat_id,link_id)
);
