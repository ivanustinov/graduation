DELETE
FROM VOTES;
DELETE
FROM USERS;
DELETE
FROM DISHES_IN_MENUE;
DELETE
FROM DISHES;
DELETE
FROM RESTAURANTS;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password, role, enabled)
VALUES ('User1', 'user1@yandex.ru', '{noop}password', 'ROLE_USER', true),
       ('User2', 'user2@yandex.ru', '{noop}password', 'ROLE_USER', true),
       ('Admin', 'admin@gmail.com', '{noop}admin', 'ROLE_ADMIN', true);


INSERT INTO RESTAURANTS (NAME, ADDRESS)
VALUES ('Вилка-Ложка', 'Кузнецова 2'),
       ('Компот', 'Старых Большевиков 3');

INSERT INTO DISHES (NAME, PRICE, ACTUAL)
VALUES ('Блинчики', 5000, true),
       ('Курица с картофелем', 20000, true),
       ('Сибас', 25000, true),
       ('Пюре', 10000, true),
       ('Солянка', 20000, true),
       ('Овощи на гриле', 18000, true),
       ('Макароны с котлетой', 25000, true),
       ('Суп Харчо', 20000, false),
       ('Компот', 4000, true),
       ('Суп Харчо', 25000, true);


INSERT INTO DISHES_IN_MENUE (DATE, DISH_ID, RESTAURANT_ID)
VALUES ('2019-09-11', 100005, 100003),
       ('2019-09-11', 100006, 100003),
       ('2019-09-11', 100007, 100003),
       ('2019-09-11', 100008, 100004),
       ('2019-09-11', 100009, 100004),
       ('2019-09-11', 100010, 100004),
       ('2019-09-12', 100011, 100003),
       ('2019-09-12', 100013, 100003),
       ('2019-09-12', 100014, 100003);



INSERT INTO VOTES (DATE, USER_ID, RESTAURANT_ID)
VALUES ('2019-09-11', 100000, 100004),
       ('2019-09-11', 100001, 100004),
       ('2019-09-12', 100000, 100003),
       ('2019-09-12', 100001, 100003),
       ('2019-09-12', 100002, 100003);
