CREATE TABLE IF NOT EXISTS `customers` (
     `customer_id` INT AUTO_INCREMENT,
     `username` VARCHAR(45) NOT NULL,
     `password` VARCHAR(45) NOT NULL,
     `firstName` VARCHAR(45) NULL,
     `lastName` VARCHAR(45) NULL,
     `phoneNumber` VARCHAR(45) NULL,
     `orderHistory` BLOB NULL,
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

INSERT into `customers` (`customer_id`, `username`, `password`, `firstName`, `lastName`, `phoneNumber`)
values (1, 'user', 'user', 'nichita', 'stanescu', '0736535210');

INSERT INTO `employees` (`employee_id`, `username`, `password`, `firstName`, `lastName`, `salary`, `ranking`, `phoneNumber`)
VALUES (1, 'admin', 'admin', 'pizza', 'king', 1080.99, 'king', '079586986');
