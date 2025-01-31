
CREATE DATABASE IF NOT EXISTS itemsDB;
DROP DATABASE itemsDB;
CREATE DATABASE itemsDB;
USE itemsDB;

CREATE TABLE IF NOT EXISTS items (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    description VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS recipes (
    result_id INTEGER NOT NULL,
    item_id INTEGER NOT NULL,
    combines_with_id INTEGER NOT NULL,
	FOREIGN KEY (item_id) REFERENCES items (id),
    FOREIGN KEY (combines_with_id) REFERENCES items (id),
    FOREIGN KEY (result_id) REFERENCES items (id)
);


INSERT INTO items (name, description) VALUES
-- 1
('Hammer', 'A really super duper strong hammer'),
-- 2
('Key Half One', 'Half a key, likely broken by the passage of time.\n It\'s the ring of the key.'),
-- 3
('Key Half Two', 'Half a key, likely broken by the passage of time.\n It\'s the teeth of the key.'),
-- 4
('Rubble Chunk', 'A Small solid chunk of rubble, likely collapsed from the wall or ceiling.'),
-- 5
('Stick', 'Sticks and stone combined will break my bones, but words will never hurt me'),
-- 6
('Full Key', 'Used to open doors or chests.'),
-- 7
('Scale', 'Fish Scale, Slimy, Smelly, and Slippery...gross'),
-- 8
('Cloth', 'Suprisingly dry cloth, likely found from previous explorers, or sunken equipment.'),
-- 9
('Scale Armor', 'Durable armor that can take the hit from enemies or traps...still gross'),
-- 10
('Queen Fragment', 'She broke like an egg'),
-- 11
('Chess Queen', 'Okay you the queen... burger queen'),
-- 12
('Wooden Spear', 'Neolithic combat just got a whole lot cooler'),
-- 13
('Fishy Meat', 'Tasty when cooked, unless you are a caveman, it looks like the hunter a little'),
-- 14
('Pie Shaped object', 'Pun intened, the edges are sharp enough to cut wood, not to mention it\'s divot can hold something'),
-- 15
('Pythagoras Shaped object', 'It reads \"High voltage\", wonderful'),
-- 16
('Black Rectangle', 'CENSORED'),
-- 17
('Spear End', 'You forked up the spear!'),
-- 18
('Dish Set', 'An Elegantly Exquisite dish set'),
-- 19
('Improper Meal', 'Using your hands? What are you, some sore of barbarian?'),
-- 20
('Meal', 'A meal fit for a lowerclassman'),
-- 21
('Voltage Sign', 'A shocking solution I know'),
-- 22
('Half Queen', 'When does half queen three release?');

INSERT INTO recipes (item_id, combines_with_id, result_id) VALUES
(2, 3, 6), -- Key Half One + Key Half Two = Full Key
(4, 5, 1), -- Stick + Rubble = Hammer
(7, 8, 9), -- Cloth + Scale = Scale Armor
(10, 10, 22), -- Queen Fragment + Queen Fragment = Queen Half
(10, 22, 11), -- Queen Fragment + Queen Half = Chess Queen
(14, 12, 17), -- Circle + Spear = Spear End + circle + stick //!!!! WORK ON ITEM REWORK IN JAVA WITH THIS ONE OTHERWISE IT WONT WORK !!!!
(15, 16, 21), -- Triangle + Rectangle = Voltage Sign
(14, 17, 18), -- Circle + Spear end = Dish Set
#(14, 13, 19), -- Circle + Meat = Improper Meal
(18, 13, 20); -- Dish Set + Meat = Meal
# (19, 17, 20); -- Improper Meal + Spear end = Meal
