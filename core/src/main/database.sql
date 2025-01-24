
CREATE DATABASE IF NOT EXISTS itemsDB;
DROP DATABASE itemsDB;
CREATE DATABASE itemsDB;
USE itemsDB;

CREATE TABLE IF NOT EXISTS items (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    description VARCHAR(200),
    texture VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS recipes (
    result_id INTEGER NOT NULL,
    item_id INTEGER NOT NULL,
    combines_with_id INTEGER NOT NULL,
	FOREIGN KEY (item_id) REFERENCES Items (id),
    FOREIGN KEY (combines_with_id) REFERENCES Items (id),
    FOREIGN KEY (result_id) REFERENCES Items (id)
);


INSERT INTO items (name, description, texture) VALUES
-- 1
('Hammer', 'A really super duper strong hammer', 'hammer'),
-- 2
('Key Half One', 'Half a key, likely broken by the passage of time.\n It\'s the ring of the key.', 'key_half_one'),
-- 3
('Key Half Two', 'Half a key, likely broken by the passage of time.\n It\'s the teeth of the key.', 'key_half_two'),
-- 4
('Rubble Chunk', 'A Small solid chunk of rubble, likely collapsed from the wall or ceiling.', 'rubble'),
-- 5
('Stick', 'Sticks and stone combined will break my bones, but words will never hurt me', 'stick'),
-- 6
('Full Key', 'Used to open doors or chests.', 'key'),
-- 7
('Scale', 'Fish Scale, Slimy, Smelly, and Slippery...gross', "fish_scale"),
-- 8
('Cloth', 'Suprisingly dry cloth, likely found from previous explorers, or sunken equipment.', 'cloth'),
-- 9
('Scale Armor', 'Durable armor that can take the hit from enemies or traps...still gross', 'scale_armor'),
-- 10
('Queen Fragment', 'She broke like an egg', 'queen_fragment'),
-- 11
('Chess Queen', 'Okay you the queen... burger queen', 'chess_queen'),
-- 12
('Wooden Spear', 'Neolithic combat just got a whole lot cooler', 'spear'),
-- 13
('Fishy Meat', 'Tasty when cooked, unless you are a caveman, it looks like the hunter a little', 'meat'),
-- 14
('Pie Shaped object', 'Pun intened, the edges are sharp enough to cut wood, not to mention it\'s divot can hold something', 'circle_object'),
-- 15
('Pythagoras Shaped object', 'It reads \"High voltage\", wonderful', 'triangle_object'),
-- 16
('Black Rectangle', 'CENSORED', 'rectangle_object'),
-- 17
('Spear End', 'You forked up the spear!', 'fork'),
-- 18
('Dish Set', 'An Elegantly Exquisite dish set', 'dish_set'),
-- 19
('Improper Meal', 'Using your hands? What are you, some sore of barbarian?', 'improper_meal'),
-- 20
('Meal', 'A meal fit for a lowerclassman', 'meal'),
-- 21
('Voltage Sign', 'A shocking solution I know', 'volt_sign'),
-- 22
('Half Queen', 'When does half queen three release?', 'queen_half');

INSERT INTO recipes (item_id, combines_with_id, result_id) VALUES
(2, 3, 6), -- Key Half One + Key Half Two = Full Key
(4, 5, 1), -- Stick + Rubble = Hammer
(7, 8, 9), -- Cloth + Scale = Scale Armor
(10, 10, 22), -- Queen Fragment + Queen Fragment = Queen Half
(10, 21, 11), -- Queen Fragment + Queen Half = Chess Queen
(14, 12, 17), -- Circle + Spear = Spear End + circle + stick //!!!! WORK ON ITEM REWORK IN JAVA WITH THIS ONE OTHERWISE IT WONT WORK !!!!
(15, 16, 21), -- Triangle + Rectangle = Voltage Sign
(14, 17, 18), -- Circle + Spear end = Dish Set
(14, 13, 19), -- Circle + Meat = Improper Meal
(18, 13, 20), -- Dish Set + Meat = Meal
(19, 17, 20); -- Improper Meal + Spear end = Meal
