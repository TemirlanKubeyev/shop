insert into categories (name)
values ('процессоры'),
       ('мониторы');

insert into options (category_id, option)
values (1,'Производитель'),
       (1,'Количество ядер'),
       (1,'Сокет'),
       (2,'Производитель'),
       (2,'Диагональ'),
       (2,'Матрица'),
       (2,'Разрешение');

insert into values (product_id, option_id, value)
values (1,1,'Intel'),
       (1,2,'8'),
       (1,3,'1250'),
       (2,4,'Samsung'),
       (2,5,'21.5'),
       (2,6,'AH-IPS'),
       (2,7,'2560*1440');

insert into products (category_id, name, price, photo)
values (1,'Процессор Intel Core i5 12400F OEM',51905, 'photos/1722626898555_intelcorei5.jpg'),
       (2,'Смартфон Apple iPhone 15 Pro Max 256Gb синий',604390, 'photos/1722626806616_iphone.jpg');

insert into users (first_name, last_name, email, password, role)
values ('Темирлан', 'Кубеев', 'temir@gmail.com', '$2a$10$Wgd.PCOUIkf3EU1gPK8HKO/zAiOQAFSm57QM.KpIf7KW1QMbznpMi', 0), -- 1
       ('Руслан', 'Кубеев', 'kub@gmail.com', '$2a$10$BUXN8yaZQBqTEDI0MBMiSOe0HZQz1Mx6HvB0L8P901brXwJyppfke', 1); -- 2

insert into orders (status, order_date, address, user_id)
values (1, '2023-12-10', 'downtown street', 1),
       (0, '2023-12-10', 'downtown street', 1);

insert into order_products (order_id, product_id, quantity_products)
values (1, 1, 1),
       (2, 1, 1);

insert into basket (user_id, product_id, quantity)
values (1, 1, 1),
       (2, 1, 1),
       (1, 2, 1);

insert into reviews (text, score, user_id, product_id, published)
values ('not bad product', 3, 1, 2, false),
       ('good product', 5, 2, 1, false);

