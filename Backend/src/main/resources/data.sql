-- ROOM_TYPES
-- TODO: Replace with self-hosted images
INSERT INTO room_types (id, name, description, price_per_night, cover_image_path) VALUES
                                                                                      ('a1000000-0000-0000-0000-000000000001', 'Classic Einzelzimmer', 'Gemütliches Zimmer mit moderner Ausstattung und ruhiger Atmosphäre.', 89.00, 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?auto=format&fit=crop&w=900&q=80'),
                                                                                      ('a1000000-0000-0000-0000-000000000002', 'Comfort Doppelzimmer', 'Helles Doppelzimmer mit komfortablem Bett und stilvollem Ambiente.', 129.00, 'https://images.unsplash.com/photo-1611892440504-42a792e24d32?auto=format&fit=crop&w=900&q=80'),
                                                                                      ('a1000000-0000-0000-0000-000000000003', 'Deluxe Zimmer', 'Großzügiges Zimmer mit hochwertigen Materialien und zusätzlichem Komfort.', 159.00, 'https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=900&q=80'),
                                                                                      ('a1000000-0000-0000-0000-000000000004', 'Junior Suite', 'Elegante Suite mit separatem Wohnbereich für einen entspannten Aufenthalt.', 219.00, 'https://images.unsplash.com/photo-1591088398332-8a7791972843?auto=format&fit=crop&w=900&q=80'),
                                                                                      ('a1000000-0000-0000-0000-000000000005', 'Familienzimmer', 'Geräumiges Zimmer für Familien mit praktischer Ausstattung und viel Platz.', 249.00, 'https://images.unsplash.com/photo-1566665797739-1674de7a421a?auto=format&fit=crop&w=900&q=80'),
                                                                                      ('a1000000-0000-0000-0000-000000000006', 'Superior Suite', 'Luxuriöse Suite mit besonderem Komfort und exklusiver Einrichtung.', 299.00, 'https://images.unsplash.com/photo-1578683010236-d716f9a3f461?auto=format&fit=crop&w=900&q=80');

-- ROOM_EXTRAS
INSERT INTO room_extras (name) VALUES
                                   ('1 Person'),
                                   ('2 Personen'),
                                   ('4 Personen'),
                                   ('Kostenloses WLAN'),
                                   ('Schreibtisch'),
                                   ('Queen-Size-Bett'),
                                   ('Bad mit Dusche'),
                                   ('Sitzecke'),
                                   ('Minibar'),
                                   ('Wohnbereich'),
                                   ('Premium-Ausstattung'),
                                   ('Doppelbett & Schlafsofa'),
                                   ('Familienfreundlich'),
                                   ('King-Size-Bett'),
                                   ('Luxusbad');

-- ROOM_TYPES <-> ROOM_EXTRAS (join table, assumed name: room_type_extras)
INSERT INTO room_type_extras (room_type_id, room_extras_name) VALUES
                                                                  ('a1000000-0000-0000-0000-000000000001', '1 Person'),
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
                                                                  ('a1000000-0000-0000-0000-000000000006', 'Luxusbad');

-- ROOMS (one physical room per type for now)
INSERT INTO rooms (id, room_type_id, room_number) VALUES
                                                      ('b1000000-0000-0000-0000-000000000001', 'a1000000-0000-0000-0000-000000000001', '101'),
                                                      ('b1000000-0000-0000-0000-000000000002', 'a1000000-0000-0000-0000-000000000002', '102'),
                                                      ('b1000000-0000-0000-0000-000000000003', 'a1000000-0000-0000-0000-000000000003', '201'),
                                                      ('b1000000-0000-0000-0000-000000000004', 'a1000000-0000-0000-0000-000000000004', '202'),
                                                      ('b1000000-0000-0000-0000-000000000005', 'a1000000-0000-0000-0000-000000000005', '301'),
                                                      ('b1000000-0000-0000-0000-000000000006', 'a1000000-0000-0000-0000-000000000006', '302');

-- IMAGES
-- TODO: Replace with self-hosted images
INSERT INTO room_images (id, image_path, room_type_id) VALUES
                                                           ('c1000000-0000-0000-0000-000000000001', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?auto=format&fit=crop&w=900&q=80', 'a1000000-0000-0000-0000-000000000001'),
                                                           ('c1000000-0000-0000-0000-000000000002', 'https://images.unsplash.com/photo-1611892440504-42a792e24d32?auto=format&fit=crop&w=900&q=80', 'a1000000-0000-0000-0000-000000000002'),
                                                           ('c1000000-0000-0000-0000-000000000003', 'https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=900&q=80', 'a1000000-0000-0000-0000-000000000003'),
                                                           ('c1000000-0000-0000-0000-000000000004', 'https://images.unsplash.com/photo-1591088398332-8a7791972843?auto=format&fit=crop&w=900&q=80', 'a1000000-0000-0000-0000-000000000004'),
                                                           ('c1000000-0000-0000-0000-000000000005', 'https://images.unsplash.com/photo-1566665797739-1674de7a421a?auto=format&fit=crop&w=900&q=80', 'a1000000-0000-0000-0000-000000000005'),
                                                           ('c1000000-0000-0000-0000-000000000006', 'https://images.unsplash.com/photo-1578683010236-d716f9a3f461?auto=format&fit=crop&w=900&q=80', 'a1000000-0000-0000-0000-000000000006');