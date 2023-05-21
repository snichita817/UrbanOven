CREATE TABLE IF NOT EXISTS `customers` (
     `customer_id` INT AUTO_INCREMENT,
     `username` VARCHAR(45) NOT NULL,
     `password` VARCHAR(45) NOT NULL,
     `firstName` VARCHAR(45) NULL,
     `lastName` VARCHAR(45) NULL,
     `address` VARCHAR(200) NULL,
     `phoneNumber` VARCHAR(45) NULL,
     PRIMARY KEY (`customer_id`)
);

CREATE TABLE IF NOT EXISTS `employees` (
    `employee_id` INT AUTO_INCREMENT,
    `username` VARCHAR(45) NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    `firstName` VARCHAR(45) NOT NULL,
    `lastName` VARCHAR(45) NOT NULL,
    `salary` FLOAT NOT NULL,
    `ranking` VARCHAR(45) NOT NULL,
     `phoneNumber` VARCHAR(45) NOT NULL,
     PRIMARY KEY (`employee_id`)
);

CREATE TABLE IF NOT EXISTS `orders` (
    `order_id` INT AUTO_INCREMENT,
    `customer_id` INT,
    `totalCost` DOUBLE,
    `orderStatus` VARCHAR(45) NOT NULL,
    `orderTime` TIMESTAMP NOT NULL,
    `expectedTime` TIMESTAMP NOT NULL,
    PRIMARY KEY (`order_id`),
    FOREIGN KEY (`customer_id`) REFERENCES `customers`(`customer_id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `pizzas` (
    `pizza_id` INT AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `price` DOUBLE NOT NULL,
    `size` INT NOT NULL,
    PRIMARY KEY (`pizza_id`)
);

CREATE TABLE IF NOT EXISTS `order_pizzas` (
    `order_id` INT,
    `pizza_id` INT,
    `P1` DOUBLE,
    `P2` DOUBLE,
    `P3` DOUBLE,
    `P4` DOUBLE,
    `P5` DOUBLE,
    `P6` DOUBLE,
    `P7` DOUBLE,
    `P8` DOUBLE,
    `P9` DOUBLE,
    `P10` DOUBLE,
    `size` INT,
    FOREIGN KEY (`order_id`) REFERENCES `orders`(`order_id`) ON DELETE CASCADE,
    FOREIGN KEY (`pizza_id`) REFERENCES `pizzas`(`pizza_id`)
);

CREATE TABLE IF NOT EXISTS `toppings` (
    `topping_id` VARCHAR(45),
    `name` VARCHAR(45),
    `price` DOUBLE,
    PRIMARY KEY (`topping_id`)
);

CREATE TABLE IF NOT EXISTS `drinks` (
    `drink_id` INT AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `price` DOUBLE NOT NULL,
    `size` INT NOT NULL,
    `withAlcohol` BOOL NOT NULL,
    PRIMARY KEY (`drink_id`)
);

CREATE TABLE IF NOT EXISTS `order_drinks` (
      `order_id` INT,
      `drink_id` INT,
      `D1` INT,
      `D2` INT,
      `D3` INT,
      `D4` INT,
      `D5` INT,
      `D6` INT,
      `size` INT,
      FOREIGN KEY (`order_id`) REFERENCES `orders`(`order_id`) ON DELETE CASCADE,
      FOREIGN KEY (`drink_id`) REFERENCES `drinks`(`drink_id`)
);

INSERT IGNORE into `customers` (`customer_id`, `username`, `password`, `firstName`, `lastName`, `address`, `phoneNumber`)
values (1, 'user', 'user', 'nichita', 'stanescu', 'str. Borsa nr 21', '0736535210');

INSERT IGNORE INTO `employees` (`employee_id`, `username`, `password`, `firstName`, `lastName`, `salary`, `ranking`, `phoneNumber`)
VALUES (1, 'admin', 'admin', 'pizza', 'king', 1080.99, 'king', '079586986');







