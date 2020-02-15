create table hibernate_sequence
(
    next_val bigint null
);

create table board_games
(
    board_game_id bigint not null
        primary key,
    age int null,
    count_players int null,
    description varchar(255) null,
    filename varchar(255) null,
    price double not null,
    title varchar(255) not null
);

create table users
(
    user_id bigint not null
        primary key,
    password varchar(255) null,
    role varchar(255) null,
    username varchar(255) null
);

create table carts
(
    cart_id bigint not null
        primary key,
    total_cost double null,
    total_count_items int null,
    customer_id bigint not null,
    constraint carts_users_user_id_fk2
        foreign key (customer_id) references users (user_id)
            on update cascade on delete cascade
);

create table cart_items
(
    cart_item_id bigint not null
        primary key,
    quantity int not null,
    board_game_id bigint null,
    cart_id bigint not null,
    constraint cart_items_board_games_board_game_id_fk
        foreign key (board_game_id) references board_games (board_game_id)
            on update cascade on delete cascade,
    constraint cart_items_carts_cart_id_fk
        foreign key (cart_id) references carts (cart_id)
            on update cascade on delete cascade
);

create table orders
(
    order_id bigint not null
        primary key,
    customer_email varchar(255) not null,
    customer_name varchar(255) not null,
    customer_phone varchar(7) not null,
    date_created datetime not null,
    quantity int not null,
    status varchar(255) not null,
    board_game_id bigint null,
    customer_id bigint not null,
    constraint orders_board_games_board_game_id_fk
        foreign key (board_game_id) references board_games (board_game_id)
            on update cascade on delete cascade,
    constraint orders_users_user_id_fk
        foreign key (customer_id) references users (user_id)
            on update cascade on delete cascade
);

insert into hibernate_sequence (next_val) values (2) ,(2), (2), (2), (2);

insert into users (user_id, password, role, username) values
    (1, '$2a$10$Voaa7YMtnsN9FBuhPu4qEeux1nHxiOujvHQdsQ7kFNh2s2LT.BAhW','ROLE_ADMIN','admin2019');




