CREATE DATABASE IF NOT EXISTS items;
USE items;

CREATE TABLE IF NOT EXISTS Items (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    description TEXT,
    texture TEXT
);

CREATE TABLE IF NOT EXISTS Recipes (
    result_id INTEGER NOT NULL PRIMARY KEY,
    item_id INTEGER NOT NULL,
    combines_with_id INTEGER NOT NULL,
    uses INTEGER DEFAULT 1 NOT NULL
    return INTEGER DEFAULT 0 NOT NULL,
    FOREIGN KEY (item_id) REFERENCES Items (id),
    FOREIGN KEY (combines_with_id) REFERENCES Items (id),
    FOREIGN KEY (result_id) REFERENCES Items (id)
);


INSERT INTO Items (name, description, texture) VALUES
--1
('Drift Wood', 'A weak bit of driftwood, fairly dry, somehow.', 'drift_wood'),
--2
('Key Half', 'Half a key, likely broken by the passage of time.', 'drift_wood'),
--3
('Rubble Chunk', 'A solid chunk of rubble, likely collapsed from the wall or ceiling.', 'rubble'),
--4
('Eye', 'A peculiar, eerie eyeball that seems to stare back at you.', 'eye'),
--5
('Full Key', 'Used to open doors or chests.', 'key'),
--6
('Makeshift Sword', 'A crude sword, not very sharp.', 'makeshift_sword'),
--7
('Scale', 'Fish Scale, Slimy, Smelly, and Slippery...gross', "fish_scale"),
--8
('Cloth', 'Suprisingly dry cloth, likely found from previous explorers, or sunken equipment.', 'cloth')
--9
('Scale Armor', 'Durable armor that can take the hit from enemies or traps...still gross', 'scale_armor'),
--10
('Bronze Device', 'A strange apparatus made of bronze. It contains multiple magnifying glasses, as well as 3 dials in the base, with different characters.', 'bronze_device'),
--11
('Stone Tablet', 'A very degraded tablet made of stone, covered in grime and very unintelligable, aside from a few characters on it', 'stone_tablet'),
--12
('Bronze Key', 'The tablet is inserted into the apparatus, showing the same characters as the dials, and opening a hatch in the bottom with a bronze key.', 'bronze_key'),
--13
('', '', ''),
--14
('', '', ''),
--15
('', '', ''),
--16
('', '', ''),

INSERT INTO Recipes (item_id, combines_with_id, result_id, uses, return) VALUES
(2, 2, 5, 1, 0), -- Key Half + Key Half = Full Key
(1, 3, 6, 2, 0), -- Drift Wood + Eye = Makeshift Sword
(7, 8, 9, 30, 0) -- Scale + Cloth = Scale Armor
(10, 11, 12, 1, 0) -- Bronze Device + Stone Tablet = Bronze Key
(, , , , )
(, , , , )
--
