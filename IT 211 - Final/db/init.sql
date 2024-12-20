CREATE DATABASE IF NOT EXISTS gamedb;

CREATE TABLE IF NOT EXISTS gamedb.players (
    name VARCHAR(20) NOT NULL PRIMARY KEY,
    password VARCHAR(30) NOT NULL,
    lvl INT(3) DEFAULT 1,
    hp INT(3) DEFAULT 100,
    currentHP INT(3) DEFAULT 100,
    atk INT(3) DEFAULT 4,
    exp INT(3) DEFAULT 100,
    currentEXP INT(3) DEFAULT 0
);


CREATE TABLE IF NOT EXISTS gamedb.items (
    itemID INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    itemName VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS gamedb.monsters (
    monsterID INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    monsterName VARCHAR(20),
    tier INT(1) NOT NULL,
    lvl INT(3) NOT NULL,
    hp INT(3) NOT NULL,
    atk INT(3) NOT NULL
);

CREATE TABLE IF NOT EXISTS gamedb.droptables (
    monsterID INT NOT NULL,
    itemID INT NOT NULL,
    droprate FLOAT NOT NULL,
    FOREIGN KEY (monsterID) REFERENCES monsters(monsterID),
    FOREIGN KEY (itemID) REFERENCES items(itemID)
);

CREATE TABLE IF NOT EXISTS gamedb.inventory (
    itemID INT NOT NULL,
    playername VARCHAR(20) NOT NULL
);

INSERT INTO gamedb.monsters (monsterName, tier, lvl, hp, atk)
VALUES
    ('Slime', 1, 1, 25, 3),
    ('Grass Sprite', 2, 1, 17, 6),
    ('Sophis Crow', 1, 3, 33, 3),
    ('Shadow Fox', 2, 4, 40, 10),
    ('Mutated Rafflesia', 2, 5, 50, 13);

INSERT INTO gamedb.items (itemName)
VALUES
    ('Slimy Essence'),
    ('Grass Bulb'),
    ('Slick Feather'),
    ('Bloody Fur'),
    ('Shadow Essence'),
    ('Poison Sac'),
    ('Wood'),
    ('Stone');

INSERT INTO gamedb.droptables (monsterID, itemID, droprate)
VALUES
    (1, 1, 0.5),
    (2, 1, 0.15),
    (2, 2, 0.45),
    (3, 3, 0.7),
    (4, 4, 0.5),
    (5, 6, 0.3),
    (5, 1, 0.2);