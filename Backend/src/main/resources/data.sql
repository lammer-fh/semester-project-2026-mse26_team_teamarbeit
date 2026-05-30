-- ROOM_TYPES
INSERT IGNORE INTO room_types (id, name, description, price_per_night, cover_image_path)
VALUES ('a1000000-0000-0000-0000-000000000001', 'Classic Einzelzimmer',
        'Gemütliches Zimmer mit moderner Ausstattung und ruhiger Atmosphäre.', 89.00, './img/room1.jpg'),
       ('a1000000-0000-0000-0000-000000000002', 'Comfort Doppelzimmer',
        'Helles Doppelzimmer mit komfortablem Bett und stilvollem Ambiente.', 129.00, './img/room2.jpg'),
       ('a1000000-0000-0000-0000-000000000003', 'Deluxe Zimmer',
        'Großzügiges Zimmer mit hochwertigen Materialien und zusätzlichem Komfort.', 159.00, './img/room3.jpg'),
       ('a1000000-0000-0000-0000-000000000004', 'Junior Suite',
        'Elegante Suite mit separatem Wohnbereich für einen entspannten Aufenthalt.', 219.00, './img/room4.jpg'),
       ('a1000000-0000-0000-0000-000000000005', 'Familienzimmer',
        'Geräumiges Zimmer für Familien mit praktischer Ausstattung und viel Platz.', 249.00, './img/room5.jpg'),
       ('a1000000-0000-0000-0000-000000000006', 'Balcony Suite',
        'Extravagante Suite mit besonderem Ausblick über die Stadt.', 250.00, './img/room6.jpg'),
       ('a1000000-0000-0000-0000-000000000007', 'Superior Suite',
        'Luxuriöse Suite mit besonderem Komfort und exklusiver Einrichtung.', 299.00, './img/room7.jpg'),
       ('a1000000-0000-0000-0000-000000000008', 'Test Zimmer',
        'Dieses Zimmer hat kein Bild und keine Ausstattung.', 40.00, null);

-- ROOM_EXTRAS
INSERT IGNORE INTO room_extras (name, icon)
VALUES ('1 Person', 'personOutline'),
       ('2 Personen', 'peopleOutline'),
       ('4 Personen', 'peopleOutline'),
       ('Kostenloses WLAN', 'wifiOutline'),
       ('Schreibtisch', 'briefcaseOutline'),
       ('Queen-Size-Bett', 'bedOutline'),
       ('Bad mit Dusche', 'waterOutline'),
       ('Sitzecke', 'homeOutline'),
       ('Minibar', 'cafeOutline'),
       ('Wohnbereich', 'homeOutline'),
       ('Premium-Ausstattung', 'sparklesOutline'),
       ('Doppelbett & Schlafsofa', 'bedOutline'),
       ('Familienfreundlich', 'happyOutline'),
       ('King-Size-Bett', 'bedOutline'),
       ('Luxusbad', 'sparklesOutline');

-- ROOM_TYPES <-> ROOM_EXTRAS (join table, assumed name: room_type_extras)
INSERT IGNORE INTO room_type_extras (room_type_id, room_extras_name)
VALUES ('a1000000-0000-0000-0000-000000000001', '1 Person'),
       ('a1000000-0000-0000-0000-000000000001', 'Kostenloses WLAN'),
       ('a1000000-0000-0000-0000-000000000001', 'Schreibtisch'),
       ('a1000000-0000-0000-0000-000000000002', '2 Personen'),
       ('a1000000-0000-0000-0000-000000000002', 'Queen-Size-Bett'),
       ('a1000000-0000-0000-0000-000000000002', 'Bad mit Dusche'),
       ('a1000000-0000-0000-0000-000000000003', '2 Personen'),
       ('a1000000-0000-0000-0000-000000000003', 'Sitzecke'),
       ('a1000000-0000-0000-0000-000000000003', 'Minibar'),
       ('a1000000-0000-0000-0000-000000000004', '2 Personen'),
       ('a1000000-0000-0000-0000-000000000004', 'Wohnbereich'),
       ('a1000000-0000-0000-0000-000000000004', 'Premium-Ausstattung'),
       ('a1000000-0000-0000-0000-000000000005', '4 Personen'),
       ('a1000000-0000-0000-0000-000000000005', 'Doppelbett & Schlafsofa'),
       ('a1000000-0000-0000-0000-000000000005', 'Familienfreundlich'),
       ('a1000000-0000-0000-0000-000000000006', '2 Personen'),
       ('a1000000-0000-0000-0000-000000000006', 'King-Size-Bett'),
       ('a1000000-0000-0000-0000-000000000006', 'Luxusbad'),
       ('a1000000-0000-0000-0000-000000000007', '2 Personen'),
       ('a1000000-0000-0000-0000-000000000007', 'Premium-Ausstattung'),
       ('a1000000-0000-0000-0000-000000000007', 'Luxusbad');

-- ROOMS (one physical room per type for now)
INSERT IGNORE INTO rooms (id, room_type_id, room_number)
VALUES ('b1000000-0000-0000-0000-000000000001', 'a1000000-0000-0000-0000-000000000001', '101'),
       ('b1000000-0000-0000-0000-000000000002', 'a1000000-0000-0000-0000-000000000002', '102'),
       ('b1000000-0000-0000-0000-000000000003', 'a1000000-0000-0000-0000-000000000003', '201'),
       ('b1000000-0000-0000-0000-000000000004', 'a1000000-0000-0000-0000-000000000004', '202'),
       ('b1000000-0000-0000-0000-000000000005', 'a1000000-0000-0000-0000-000000000005', '301'),
       ('b1000000-0000-0000-0000-000000000006', 'a1000000-0000-0000-0000-000000000006', '302'),
       ('b1000000-0000-0000-0000-000000000007', 'a1000000-0000-0000-0000-000000000007', '303'),
       ('b1000000-0000-0000-0000-000000000008', 'a1000000-0000-0000-0000-000000000008', '304');

-- IMAGES
INSERT IGNORE INTO room_images (id, image_path, room_type_id)
VALUES ('c1000000-0000-0000-0000-000000000001', './img/room1.jpg', 'a1000000-0000-0000-0000-000000000001'),
       ('c1000000-0000-0000-0000-000000000002', './img/room2.jpg', 'a1000000-0000-0000-0000-000000000002'),
       ('c1000000-0000-0000-0000-000000000003', './img/room3.jpg', 'a1000000-0000-0000-0000-000000000003'),
       ('c1000000-0000-0000-0000-000000000004', './img/room4.jpg', 'a1000000-0000-0000-0000-000000000004'),
       ('c1000000-0000-0000-0000-000000000005', './img/room5.jpg', 'a1000000-0000-0000-0000-000000000005'),
       ('c1000000-0000-0000-0000-000000000006', './img/room6.jpg', 'a1000000-0000-0000-0000-000000000006'),
       ('c1000000-0000-0000-0000-000000000007', './img/room7.jpg', 'a1000000-0000-0000-0000-000000000007'),
       ('c1000000-0000-0000-0000-000000000008', './img/room8.jpg', 'a1000000-0000-0000-0000-000000000001'),
       ('c1000000-0000-0000-0000-000000000009', './img/room9.jpg', 'a1000000-0000-0000-0000-000000000002'),
       ('c1000000-0000-0000-0000-000000000010', './img/room10.jpg', 'a1000000-0000-0000-0000-000000000003'),
       ('c1000000-0000-0000-0000-000000000011', './img/room11.jpg', 'a1000000-0000-0000-0000-000000000004'),
       ('c1000000-0000-0000-0000-000000000012', './img/room12.jpg', 'a1000000-0000-0000-0000-000000000005'),
       ('c1000000-0000-0000-0000-000000000013', './img/room13.jpg', 'a1000000-0000-0000-0000-000000000006'),
       ('c1000000-0000-0000-0000-000000000014', './img/room14.jpg', 'a1000000-0000-0000-0000-000000000007');