CREATE TYPE Address_Type AS (
	country		 VARCHAR(50),
	city		 VARCHAR(100),
	zip_code 	 VARCHAR(20),
	street		 VARCHAR(100),
	house_number VARCHAR(20)
);

CREATE TYPE Customer_Type AS (
	customer_id	INTEGER,
	first_name 	VARCHAR(100),
	last_name 	VARCHAR(100),
	email		VARCHAR(100),
	address		Address_Type
);

CREATE TABLE Customer OF Customer_Type (
	PRIMARY KEY (customer_id)
);

CREATE TYPE Review_Type AS (
	score 		INTEGER,
	content 	VARCHAR(400),
	author		VARCHAR(50),
	created		TIMESTAMP
);

CREATE TYPE Product_Type AS (
	product_id	INTEGER,
	brand		VARCHAR(100),
	name		VARCHAR(100),
	price		DECIMAL(9,2),
	reviews 	Review_Type[]
);

CREATE TABLE Product OF Product_Type (
	reviews 	 DEFAULT '{}',
	PRIMARY KEY (product_id)
);

CREATE TABLE Monitor (
	refresh_rate 	INTEGER,
	speakers		SMALLINT,
	PRIMARY KEY (product_id)
) INHERITS (Product);

CREATE TABLE Headset (
	sensitivity 		INTEGER,
	impedance		 	INTEGER,
	noise_cancelling	SMALLINT,
	PRIMARY KEY (product_id)
) INHERITS (Product);

CREATE TABLE Mouse (
	dpi 	INTEGER,
	sensor  VARCHAR(20),
	PRIMARY KEY (product_id)
) INHERITS (Product);

CREATE TYPE Purchase_Type AS (
	purchase_id	INTEGER,
	customer_id INTEGER,
	product_id 	INTEGER,
	count 		INTEGER,
	status		VARCHAR(20),
	ordered		TIMESTAMP
);

CREATE TABLE Purchase OF Purchase_Type (
	PRIMARY KEY (purchase_id),
	FOREIGN KEY (customer_id) REFERENCES Customer
);

CREATE OR REPLACE FUNCTION get_complete_address(Address_Type) RETURNS VARCHAR(300) 
AS $$ SELECT ($1).street || ' ' || ($1).house_number || '\n' || ($1).zip_code || ' ' || ($1).city || ', ' || ($1).country $$
LANGUAGE sql;

CREATE OR REPLACE FUNCTION total_cost(Product_Type, integer) RETURNS DECIMAL
AS $$ SELECT ($1).price * $2 $$
LANGUAGE sql;