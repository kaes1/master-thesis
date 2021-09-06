CREATE TYPE Address_Type AS OBJECT (
	country		 VARCHAR(50),
	city		 VARCHAR(100),
	zip_code 	 VARCHAR(20),
	street		 VARCHAR(100),
	house_number VARCHAR(20),
	MEMBER FUNCTION get_complete_address RETURN VARCHAR
);
/
CREATE OR REPLACE TYPE BODY Address_Type AS 
	MEMBER FUNCTION get_complete_address RETURN VARCHAR IS
	BEGIN
		RETURN street || ' ' || house_number || '\n' || zip_code || ' ' || city || ', ' || country;
	END;
END;
/
CREATE TYPE Customer_Type AS OBJECT (
	customer_id	INTEGER,
	first_name 	VARCHAR(100),
	last_name 	VARCHAR(100),
	email		VARCHAR(100),
	address		Address_Type
);
/
CREATE TABLE Customer OF Customer_Type (
	PRIMARY KEY (customer_id)
) OBJECT IDENTIFIER IS PRIMARY KEY;
/
CREATE TYPE Review_Type AS OBJECT (
	score 		INTEGER,
	content 	VARCHAR(400),
	author		VARCHAR(50),
	created		TIMESTAMP
);
/
CREATE TYPE Reviews_Type AS TABLE OF Review_Type;
/
CREATE OR REPLACE TYPE Product_Type AS OBJECT (
	product_id	INTEGER,
	brand		VARCHAR(100),
	name		VARCHAR(100),
	price		DECIMAL(9,2),
	reviews 	Reviews_Type,
	MEMBER FUNCTION total_cost (how_many IN INTEGER) RETURN DECIMAL
) NOT FINAL;
/
CREATE OR REPLACE TYPE BODY Product_Type AS 
	MEMBER FUNCTION total_cost (how_many IN INTEGER) RETURN DECIMAL IS
	BEGIN
		RETURN how_many * price;
	END;
END;
/
CREATE TABLE Product OF Product_Type (
	PRIMARY KEY (product_id)
) OBJECT IDENTIFIER IS PRIMARY KEY 
  NESTED TABLE reviews STORE AS Reviews;
/
CREATE TYPE Monitor_Type UNDER Product_Type (
	refresh_rate 	INTEGER,
	speakers		SMALLINT
);
/
CREATE TYPE Headset_Type UNDER Product_Type (
	sensitivity 		INTEGER,
	impedance		 	INTEGER,
	noise_cancelling	SMALLINT
);
/
CREATE TYPE Mouse_Type UNDER Product_Type (
	dpi 	INTEGER,
	sensor  VARCHAR(20)
);
/
CREATE TYPE Purchase_Type AS OBJECT (
	purchase_id INTEGER,
	customer 	REF Customer_Type,
	product 	REF Product_Type,
	count 		INTEGER,
	status		VARCHAR(20),
	ordered		TIMESTAMP
);
/
CREATE TABLE Purchase OF Purchase_Type (
	product SCOPE IS Product,
	customer SCOPE IS Customer,
	PRIMARY KEY (purchase_id)
) OBJECT IDENTIFIER IS PRIMARY KEY;
/