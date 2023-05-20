CREATE TABLE IF NOT EXISTS `customers` (
     `customer_id` INT AUTO_INCREMENT,
     `username` VARCHAR(45) NOT NULL,
     `password` VARCHAR(45) NOT NULL,
     `firstName` VARCHAR(45) NULL,
     `lastName` VARCHAR(45) NULL,
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

CREATE TABLE IF NOT EXISTS `addresses` (
    `address_id` INT AUTO_INCREMENT,
    `customer_id` INT,
    `city` VARCHAR(45) NOT NULL,
    `street_address` VARCHAR(200) NOT NULL,
    PRIMARY KEY (`address_id`),
    FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`)
);

CREATE TABLE IF NOT EXISTS `orders` (
    `order_id` INT AUTO_INCREMENT,
    `customer_id` INT,
    `totalCost` FLOAT,
    `orderStatus` VARCHAR(45) NOT NULL,
    `orderTime` DATE NOT NULL,
    `expectedTime` DATE NOT NULL,
    PRIMARY KEY (`order_id`),
    FOREIGN KEY (`customer_id`) REFERENCES `customers`(`customer_id`)
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
    `tomato` DOUBLE,
    `mozzarella` DOUBLE,
    `mushroom` DOUBLE,
    `pepperoni` DOUBLE,
    `onion` DOUBLE,
    `green pepper` DOUBLE,
    `black olives` DOUBLE,
    `ham` DOUBLE,
    `pineapple` DOUBLE,
    `bacon` DOUBLE,
    PRIMARY KEY (`order_id`, `pizza_id`),
    FOREIGN KEY (`order_id`) REFERENCES `orders`(`order_id`),
    FOREIGN KEY (`pizza_id`) REFERENCES `pizzas`(`pizza_id`)
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
      `ice` INT,
      `lemon` INT,
      `mint` INT,
      `ginger` INT,
      `sugar` DOUBLE,
      `lime` INT,
      PRIMARY KEY (`order_id`, `drink_id`),
      FOREIGN KEY (`order_id`) REFERENCES `orders`(`order_id`),
      FOREIGN KEY (`drink_id`) REFERENCES `drinks`(`drink_id`)
);

INSERT IGNORE into `customers` (`customer_id`, `username`, `password`, `firstName`, `lastName`, `phoneNumber`)
values (1, 'user', 'user', 'nichita', 'stanescu', '0736535210');

INSERT IGNORE INTO `employees` (`employee_id`, `username`, `password`, `firstName`, `lastName`, `salary`, `ranking`, `phoneNumber`)
VALUES (1, 'admin', 'admin', 'pizza', 'king', 1080.99, 'king', '079586986');







