CREATE TABLE IF NOT EXISTS `direction` (
  `id`  int PRIMARY KEY,
  `departure` VARCHAR(50) NOT NULL,
  `destination` VARCHAR(50) NOT NULL,
  `price` DECIMAL NOT NULL
  );


INSERT INTO `direction` (id, departure, destination, price) VALUES
(1, 'London', 'France', 20),
(2, 'London', 'Spain', 50),
(3, 'London', 'Italy', 100),
(4, 'France', 'London', 20),
(5, 'France', 'Spain', 20),
(6, 'France', 'Italy', 50),
(7, 'Spain', 'France', 30),
(8, 'Spain', 'London', 50),
(9, 'Spain', 'Italy', 30),
(10, 'Italy', 'London', 100),
(11, 'Italy', 'Spain', 30),
(12, 'Italy', 'France', 50);

CREATE TABLE IF NOT EXISTS `seat` (
  `id`  int PRIMARY KEY,
  `section` CHAR(1) NOT NULL,
  `number` int NOT NULL,
  `location` varchar(20) NOT NULL
  );

INSERT INTO `seat` (`id`, `section`, `number`, `location`) VALUES
(1, 'A', 1, 'UPPER_BED'),
(2, 'A', 4, 'LOWER_BED'),
(3, 'A', 7, 'UPPER_BED'),
(4, 'A', 10, 'LOWER_BED'),
(5, 'A', 13, 'WINDOW'),
(6, 'A', 14, 'MIDDLE'),
(7, 'A', 15, 'AISLE'),
(8, 'A', 16, 'WINDOW'),
(9, 'A', 17, 'MIDDLE'),
(10, 'A', 18, 'AISLE'),
(11, 'A', 19, 'UPPER_BED'),
(12, 'A', 22, 'LOWER_BED'),
(13, 'A', 25, 'UPPER_BED'),
(14, 'A', 28, 'LOWER_BED'),
(15, 'A', 31, 'WINDOW'),
(16, 'A', 32, 'MIDDLE'),
(17, 'A', 33, 'AISLE'),
(18, 'A', 34, 'WINDOW'),
(19, 'A', 35, 'MIDDLE'),
(20, 'A', 36, 'AISLE'),
(21, 'B', 1, 'UPPER_BED'),
(22, 'B', 4, 'LOWER_BED'),
(23, 'B', 7, 'UPPER_BED'),
(24, 'B', 10, 'LOWER_BED'),
(25, 'B', 13, 'WINDOW'),
(26, 'B', 14, 'MIDDLE'),
(27, 'B', 15, 'AISLE'),
(28, 'B', 16, 'WINDOW'),
(29, 'B', 17, 'MIDDLE'),
(30, 'B', 18, 'AISLE'),
(31, 'B', 19, 'UPPER_BED'),
(32, 'B', 22, 'LOWER_BED'),
(33, 'B', 25, 'UPPER_BED'),
(34, 'B', 28, 'LOWER_BED'),
(35, 'B', 31, 'WINDOW'),
(36, 'B', 32, 'MIDDLE'),
(37, 'B', 33, 'AISLE'),
(38, 'B', 34, 'WINDOW'),
(39, 'B', 35, 'MIDDLE'),
(40, 'B', 36, 'AISLE');