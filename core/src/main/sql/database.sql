DROP DATABASE items;
CREATE DATABASE IF NOT EXISTS items;
USE items;

CREATE TABLE IF NOT EXISTS Items (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    description VARCHAR(200),
    texture VARCHAR(20)
);

DROP TABLE IF EXISTS Recipes;

CREATE TABLE IF NOT EXISTS Recipes (
    recipe_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    item_id INTEGER NOT NULL,
    combines_with_id INTEGER NOT NULL,
    result_id INTEGER NOT NULL,
    uses INTEGER DEFAULT 1 NOT NULL,
    drops INTEGER DEFAULT 0 NOT NULL,
    FOREIGN KEY (item_id) REFERENCES Items (id),
    FOREIGN KEY (combines_with_id) REFERENCES Items (id),
    FOREIGN KEY (result_id) REFERENCES Items (id)
);


INSERT INTO Items (name, description, texture) VALUES
-- 1
('Drift Wood', 'A weak bit of driftwood, fairly dry, somehow.', 'drift_wood'),
-- 2
('Key Half', 'Half a key, likely broken by the passage of time.', 'key_half'),
-- 3
('Rubble Chunk', 'A solid chunk of rubble, likely collapsed from the wall or ceiling.', 'rubble'),
-- 4
('Eye', 'A peculiar, eerie eyeball that seems to stare back at you.', 'eye'),
-- 5
('Full Key', 'Used to open doors or chests.', 'key'),
-- 6
('Makeshift Sword', 'A crude sword, not very sharp.', 'makeshift_sword'),
-- 7
('Scale', 'Fish Scale, Slimy, Smelly, and Slippery...gross', "fish_scale"),
-- 8
('Cloth', 'Suprisingly dry cloth, likely found from previous explorers, or sunken equipment.', 'cloth'),
-- 9
('Scale Armor', 'Durable armor that can take the hit from enemies or traps...still gross', 'scale_armor'),
-- 10
('Bronze Device', 'A strange apparatus made of bronze. It contains multiple magnifying glasses, as well as 3 dials in the base, with different characters.', 'bronze_device'),
-- 11
('Stone Tablet', 'A very degraded tablet made of stone, covered in grime and very unintelligable, aside from a few characters on it', 'stone_tablet'),
-- 12
('Bronze Key', 'The tablet is inserted into the apparatus, showing the same characters as the dials, and opening a hatch in the bottom with a bronze key.', 'bronze_key'),
-- 13
('Raw Meat', 'Slimy, Mushy Meat. Tasty when cooked, unless you are a caveman.', 'raw_meat'),
-- 14
('Flint', 'Mineral, not very useful, aside from using to start fires', 'flint'),
-- 15
('Unlit Torch', 'Useful for lighting up corridors, and for starting fires.', 'unlit_torch'),
-- 16
('Cooked Meat', 'Seared "Steak". Can give you a second wind.', 'cooked_meat'),
-- 17
('Cooked Eye', 'Something is very, very wrong with you.', 'cooked_eye'),
-- 18
('Lit Torch', 'Lit up for a few minutes, usable to cook things or burn things.', 'lit_torch');

INSERT INTO Recipes (item_id, combines_with_id, result_id, uses, drops) VALUES
(2, 2, 5, 1, 0),  -- Key Half + Key Half = Full Key
(1, 4, 6, 20, 0), -- Drift Wood + Eye = Makeshift Sword
(14, 4, 17, 2, 0), -- Flint + Eye = Cooked Eye
(18, 4, 17, 2, 1), -- Lit Torch + Eye = Cooked Eye
(7, 8, 9, 30, 0), -- Scale + Cloth = Scale Armor
(10, 11, 12, 1, 0), -- Bronze Device + Stone Tablet = Bronze Key
(18, 13, 16, 10, 0), -- Lit Torch + Raw Meat = Cooked Meat
(1, 14, 15, 1, 0), -- Drift Wood + Flint = Unlit Torch
(14, 15, 18, 5, 0); -- Flint + Unlit Torch = Lit Torch





SELECT * FROM ITEMS;
SELECT * FROM RECIPES;
SELECT
    r.recipe_id AS RecipeID,
    i1.name AS Item,
    i2.name AS Combines,
    i3.name AS Result
FROM
    Recipes r
JOIN
    Items i1 ON r.item_id = i1.id
JOIN
    Items i2 ON r.combines_with_id = i2.id
JOIN
    Items i3 ON r.result_id = i3.id;
