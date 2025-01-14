
CREATE DATABASE IF NOT EXISTS itemsDB;
DROP DATABASE itemsDB;
CREATE DATABASE itemsDB;
USE itemsDB;

CREATE TABLE IF NOT EXISTS items (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    description VARCHAR(200),
    texture VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS recipes (
    result_id INTEGER NOT NULL PRIMARY KEY,
    item_id INTEGER NOT NULL,
    combines_with_id INTEGER NOT NULL,
    uses INTEGER DEFAULT 1 NOT NULL,
    idkWhatThisIsforOliver INTEGER DEFAULT 0 NOT NULL,
    FOREIGN KEY (item_id) REFERENCES Items (id),
    FOREIGN KEY (combines_with_id) REFERENCES Items (id),
    FOREIGN KEY (result_id) REFERENCES Items (id)
);


INSERT INTO items (name, description, texture) VALUES
-- 1
('Drift Wood', 'A weak bit of driftwood, fairly dry, somehow.', 'drift_wood'),
-- 2
('Key Half One', 'Half a key, likely broken by the passage of time.\n It\'s the ring of the key.', 'key_half_one'),
-- 3
('Key Half Two', 'Half a key, likely broken by the passage of time.\n It\'s the teeth of the key.', 'key_half_two'),
-- 4
('Rubble Chunk', 'A solid chunk of rubble, likely collapsed from the wall or ceiling.', 'rubble'),
-- 5
('Eye', 'A peculiar, eerie eyeball that seems to stare back at you.', 'eye'),
-- 6
('Full Key', 'Used to open doors or chests.', 'key'),
-- 7
('Makeshift Sword', 'A crude sword, not very sharp.', 'makeshift_sword'),
-- 8
('Scale', 'Fish Scale, Slimy, Smelly, and Slippery...gross', "fish_scale"),
-- 9
('Cloth', 'Suprisingly dry cloth, likely found from previous explorers, or sunken equipment.', 'cloth'),
-- 10
('Scale Armor', 'Durable armor that can take the hit from enemies or traps...still gross', 'scale_armor'),
-- 11
('Bronze Device', 'A strange apparatus made of bronze. It contains multiple magnifying glasses, as well as 3 dials in the base, with different characters.', 'bronze_device'),
-- 12
('Stone Tablet', 'A very degraded tablet made of stone, covered in grime and very unintelligable, aside from a few characters on it', 'stone_tablet'),
-- 13
('Bronze Key', 'The tablet is inserted into the apparatus, showing the same characters as the dials, and opening a hatch in the bottom with a bronze key.', 'bronze_key'),
-- 14
('Raw Meat', 'Tasty when cooked, unless you are a caveman.', 'meat'),
-- 15
('', '', ''),
-- 16
('', '', ''),
-- 17
('', '', '');

INSERT INTO recipes (item_id, combines_with_id, result_id, uses, idkWhatThisIsforOliver) VALUES
(2, 3, 6, 1, 0), -- Key Half One + Key Half Two = Full Key
(1, 4, 7, 2, 0), -- Drift Wood + Eye = Makeshift Sword
(8, 9, 10, 30, 0), -- Scale + Cloth = Scale Armor
(11, 12, 13, 1, 0); -- Bronze Device + Stone Tablet = Bronze Key
