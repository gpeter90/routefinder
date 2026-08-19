INSERT INTO shipping.events (event_name) VALUES
    ('ORDER_PLACED'),
    ('PICKED_UP'),
    ('IN_TRANSIT'),
    ('OUT_FOR_DELIVERY'),
    ('DELIVERED'),
    ('RETURNED_TO_SENDER'),
    ('FAILED_DELIVERY_ATTEMPT');

INSERT INTO shipping.clients (client_name, address, zipcode, city, phone, email) VALUES
    ('Kovács János', 'Kossuth utca 12.', '1051', 'Budapest', '+36301234567', 'kovacs.janos@example.com'),
    ('Szabó Anna', 'Petőfi tér 3.', '6720', 'Szeged', '+36209876543', 'szabo.anna@example.com'),
    ('Tóth Béla', 'Rákóczi út 45.', '3530', 'Miskolc', '+36701112233', 'toth.bela@example.com'),
    ('Nagy Eszter', 'Deák Ferenc utca 8.', '9021', 'Győr', '+36304445566', 'nagy.eszter@example.com'),
    ('Horváth Péter', 'Bartók Béla út 22.', '7621', 'Pécs', '+36207778899', 'horvath.peter@example.com');

INSERT INTO shipping.parcels (sender_id, receiver_id, parcel_no) VALUES
    (1, 2, 'PKG-2026-000001'),
    (3, 4, 'PKG-2026-000002'),
    (2, 5, 'PKG-2026-000003'),
    (5, 1, 'PKG-2026-000004'),
    (4, 3, 'PKG-2026-000005');

INSERT INTO shipping.trackings (parcel_id, event_date, event_id) VALUES
    (1, '2026-08-01 09:00:00', 1),
    (1, '2026-08-01 14:30:00', 2),
    (1, '2026-08-02 08:00:00', 3),
    (1, '2026-08-03 07:15:00', 4),
    (1, '2026-08-03 11:45:00', 5),
    (2, '2026-08-02 10:00:00', 1),
    (2, '2026-08-02 16:00:00', 2),
    (2, '2026-08-03 09:30:00', 3),
    (3, '2026-08-03 11:00:00', 1),
    (3, '2026-08-04 08:45:00', 2),
    (3, '2026-08-05 10:00:00', 3),
    (3, '2026-08-05 15:30:00', 4),
    (3, '2026-08-05 18:00:00', 6),
    (4, '2026-08-04 09:00:00', 1),
    (4, '2026-08-04 13:00:00', 2),
    (4, '2026-08-05 07:00:00', 3),
    (4, '2026-08-06 08:00:00', 4),
    (4, '2026-08-06 10:30:00', 7),
    (5, '2026-08-05 10:00:00', 1),
    (5, '2026-08-05 15:00:00', 2);

INSERT INTO airline.cities (city_name, country, continent) VALUES
    ('Budapest', 'HU', 'Europe'),
    ('London', 'GB', 'Europe'),
    ('Paris', 'FR', 'Europe'),
    ('Berlin', 'DE', 'Europe'),
    ('Rome', 'IT', 'Europe'),
    ('New York', 'US', 'Americas'),
    ('Los Angeles', 'US', 'Americas'),
    ('Toronto', 'CA', 'Americas'),
    ('São Paulo', 'BR', 'Americas'),
    ('Mexico City', 'MX', 'Americas');

INSERT INTO airline.routes (departure_city_id, arrival_city_id, distance_km, cost_eur) VALUES
    (1, 4, 689, 120),
    (1, 5, 810, 150),
    (4, 2, 932, 140),
    (4, 3, 878, 130),
    (2, 3, 344, 90),
    (2, 6, 5570, 450),
    (3, 5, 1105, 160),
    (5, 9, 9170, 680),
    (6, 8, 550, 110),
    (6, 7, 3944, 320),
    (6, 9, 7680, 580),
    (7, 10, 2491, 280),
    (8, 2, 5720, 460),
    (9, 10, 7050, 520),
    (10, 6, 3364, 300);
