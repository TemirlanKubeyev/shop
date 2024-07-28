drop table if exists categories, options, values, products, users, orders, basket, reviews, order_products;

create table categories
(
    id   serial8,
    name varchar,
    primary key (id)
);

create table options
(
    id          serial8,
    option      varchar,
    category_id int8,
    primary key (id),
    foreign key (category_id) references categories (id)
);

alter table options
alter column option set not null;

create table values
(
    id         serial8,
    value      varchar,
    product_id int8,
    option_id  int8,
    primary key (id),
    foreign key (option_id) references options (id)
);

create table products
(
    id          serial8,
    name        varchar,
    price       int8,
    category_id int8,
    primary key (id),
    foreign key (category_id) references categories (id)
);

create table users
(
    id         serial8,
    first_name varchar,
    last_name  varchar,
    email      varchar,
    password   varchar,
    photoPath  varchar,
    primary key (id)
);

alter table users
add column role int2;

create table orders
(
    id                serial8,
    status            int2,
    order_date        date,
    address           varchar,
    user_id           int8,
    primary key (id),
    foreign key (user_id) references users (id)
);

create table order_products
(
    id serial8,
    order_id int8,
    product_id int8,
    quantity_products int8,
    primary key (id),
    foreign key (order_id) references orders (id),
    foreign key (product_id) references products (id)
);

create table basket
(
    id         serial8,
    user_id    int8,
    product_id int8,
    quantity int8,
    primary key (id),
    foreign key (user_id) references users (id),
    foreign key (product_id) references products (id)
);

create table reviews
(
    id      serial8,
    text    varchar,
    score  int4 check (score BETWEEN 1 and 5),
    published bool,
    user_id int8,
    product_id int8,
    primary key (id),
    foreign key (user_id) references users (id),
    foreign key (product_id) references products (id)
);

