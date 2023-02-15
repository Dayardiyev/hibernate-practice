create table categories
(
    id   serial8,
    name varchar not null,
    primary key (id)
);

create table options
(
    id          serial8,
    category_id int8    not null,
    name        varchar not null,
    primary key (id),
    foreign key (category_id) references categories (id),
    unique (category_id, name)
);

create table products
(
    id          serial8,
    category_id int8    not null,
    name        varchar not null,
    price       int4    not null,
    primary key (id),
    foreign key (category_id) references categories (id),
    unique (name)
);
create table values
(
    id         serial8,
    product_id int8    not null,
    option_id  int8    not null,
    value      varchar not null,
    primary key (id),
    foreign key (product_id) references products (id),
    foreign key (option_id) references options (id),
    unique (product_id, option_id)
);

create table users
(
    id         serial8,
    role       int4    not null,
    login      varchar not null,
    password   varchar not null,
    name       varchar not null,
    lastname   varchar not null,
    created_at timestamp,
    primary key (id),
    unique (login)
);

create table orders
(
    id         serial8,
    user_id    int8    not null,
    status     int4    not null,
    address    varchar not null,
    created_at timestamp,
    primary key (id),
    foreign key (user_id) references users (id)
);

create table order_product
(
    id            serial8,
    product_id    int8 not null,
    order_id      int8 not null,
    product_count int4 not null,
    primary key (id),
    foreign key (product_id) references products (id),
    foreign key (order_id) references orders (id)
);

create table reviews
(
    id           serial8,
    user_id      int8 not null,
    product_id   int8 not null,
    is_published bool,
    rating       int4,
    text         text,
    created_at   timestamp,
    primary key (id),
    foreign key (user_id) references users (id),
    foreign key (product_id) references products (id)
);

