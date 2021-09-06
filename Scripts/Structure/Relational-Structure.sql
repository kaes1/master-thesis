CREATE TABLE Product (
    product_id INT NOT NULL,
	brand VARCHAR(100),
	name VARCHAR(100),
	price DECIMAL(9,2),
    PRIMARY KEY (product_id)
);

CREATE TABLE Review (
    review_id INT NOT NULL,
	score INT,
    content VARCHAR(400),
    author VARCHAR(50),
    created TIMESTAMP,
	product_id INT NOT NULL,
    PRIMARY KEY (review_id),
 	FOREIGN KEY (product_id) REFERENCES Product
);

CREATE TABLE Monitor (
    product_id INT NOT NULL,
	refresh_rate INT,
	speakers SMALLINT,
    PRIMARY KEY (product_id),
 	FOREIGN KEY (product_id) REFERENCES Product
);

CREATE TABLE Mouse (
    product_id INT NOT NULL,
	dpi INT,
	sensor VARCHAR(20),
    PRIMARY KEY (product_id),
 	FOREIGN KEY (product_id) REFERENCES Product
);

CREATE TABLE Headset (
    product_id INT NOT NULL,
	sensitivity INT,
	impedance INT,
	noise_cancelling SMALLINT,
    PRIMARY KEY (product_id),
 	FOREIGN KEY (product_id) REFERENCES Product
);

CREATE TABLE Customer (
    customer_id INT NOT NULL,
	first_name VARCHAR(100),
	last_name VARCHAR(100),
	email VARCHAR(100),
	country VARCHAR(50),
	city VARCHAR(100),
	zip_code VARCHAR(20),
	street VARCHAR(100),
	house_number VARCHAR(20),
    PRIMARY KEY (customer_id)
);

CREATE TABLE Purchase (
    purchase_id INT NOT NULL,
	customer_id INT NOT NULL,
	product_id INT NOT NULL,
	count INT,
	status VARCHAR(20),
	ordered TIMESTAMP,
    PRIMARY KEY (purchase_id),
	FOREIGN KEY (customer_id) REFERENCES Customer,
	FOREIGN KEY (product_id) REFERENCES Product
);