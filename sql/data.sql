insert into categories (name)
values ('процессоры'),
       ('мониторы');

insert into options (category_id, option)
values (4,'Производитель'),
       (4,'Количество ядер'),
       (4,'Сокет'),
       (5,'Производитель'),
       (5,'Диагональ'),
       (5,'Матрица'),
       (5,'Разрешение');

insert into values (product_id, option_id, value)
values (21,15,'Intel'),
       (21,16,'8'),
       (21,17,'1250'),
       (20,18,'Samsung'),
       (20,19,'21.5'),
       (20,20,'AH-IPS'),
       (20,21,'2560*1440');

insert into products (category_id, name, price)
values (1,'Intel Core I9 9900',1200),
       (2,'Samsung SU556270',1500);

insert into users (first_name, last_name, email, password)
values ('Max', 'Holloway', 'max@gmail.com', '123456789'),
       ('Alex', 'Volkanovski', 'alex@gmail.com', '123456');

insert into orders (status, order_date, address, user_id, product_id, quantity_products)
values (1, '2023-12-10', 'downtown street', 1,1,2),
       (0, '2023-12-10', 'downtown street', 1,1,2),
       (1, '2023-12-10', 'downtown street', 1,1,2),
       (0, '2023-12-10', 'downtown street', 1,1,2);

insert into order_products (order_id, product_id)
values (1, 1),
       (2, 1),
       (3, 2);

insert into basket (user_id, product_id)
values (1, 1),
       (2, 1),
       (1, 2);

insert into reviews (text, score, user_id, product_id)
values ('not bad product', 3, 1, 2),
       ('good product', 5, 1, 2),
       ('good product', 5, 2, 2),
       ('bad product', 1, 2, 1);

