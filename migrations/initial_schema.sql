create table Chats (
    id bigserial primary key,
    chat_id bigint not null
);

create table Links (
    id bigserial primary key,
    url varchar(255) not null
);

create table Subscriptions (
    id bigserial primary key,
    chat_id bigint references Chats,
    link_id bigint references Links
);
