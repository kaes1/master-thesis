CREATE TYPE Address_Type AS (
	country		 VARCHAR(50),
	city		 VARCHAR(100),
	zip_code 	 VARCHAR(20),
	street		 VARCHAR(100),
	house_number VARCHAR(20)
) MODE DB2SQL
METHOD get_complete_address () RETURNS VARCHAR(300) LANGUAGE SQL DETERMINISTIC;

CREATE METHOD get_complete_address () RETURNS VARCHAR (300) FOR Address_Type
RETURN SELF..street || ' ' || SELF..house_number || '\n' || SELF..zip_code || ' ' || SELF..city || ', ' || SELF..country;

CREATE TYPE Customer_Type AS (
	first_name 	VARCHAR(100),
	last_name 	VARCHAR(100),
	email		VARCHAR(100),
	address		Address_Type
) REF USING INTEGER MODE DB2SQL;

CREATE TABLE Customer OF Customer_Type (REF IS customer_id USER GENERATED);

CREATE TYPE Product_Type AS (
	brand		VARCHAR(100),
	name		VARCHAR(100),
	price		DECIMAL(9,2)
) NOT FINAL REF USING INTEGER MODE DB2SQL
METHOD total_cost(how_many INTEGER) RETURNS Decimal(12,2) LANGUAGE SQL DETERMINISTIC;

CREATE METHOD total_cost(how_many INTEGER) RETURNS Decimal(12,2) FOR Product_Type
RETURN how_many * SELF..price;

CREATE TYPE Monitor_Type UNDER Product_Type AS (
	refresh_rate 	INTEGER,
	speakers		SMALLINT
) MODE DB2SQL;

CREATE TYPE Headset_Type UNDER Product_Type AS (
	sensitivity 		INTEGER,
	impedance		 	INTEGER,
	noise_cancelling	SMALLINT
) MODE DB2SQL;

CREATE TYPE Mouse_Type UNDER Product_Type AS (
	dpi 	INTEGER,
	sensor  VARCHAR(20)
) MODE DB2SQL;

CREATE TABLE Product OF Product_Type (REF IS product_id USER GENERATED);
CREATE TABLE Monitor OF Monitor_Type UNDER Product INHERIT SELECT PRIVILEGES;
CREATE TABLE Headset OF Headset_Type UNDER Product INHERIT SELECT PRIVILEGES;
CREATE TABLE Mouse OF Mouse_Type UNDER Product INHERIT SELECT PRIVILEGES;

CREATE TYPE Review_Type AS (
	score 		INTEGER,
	content 	VARCHAR(400),
	author		VARCHAR(50),
	created		TIMESTAMP,
	product 	REF(Product_Type)
) REF USING INTEGER MODE DB2SQL;

CREATE TABLE Review OF Review_Type (
	REF IS review_id USER GENERATED,
	product WITH OPTIONS SCOPE Product
);

CREATE TYPE Purchase_Type AS (
	customer 	REF(Customer_Type),
	product 	REF(Product_Type),
	count 		INTEGER,
	status		VARCHAR(20),
	ordered		TIMESTAMP
) REF USING INTEGER MODE DB2SQL;

CREATE TABLE Purchase OF Purchase_Type (
	REF IS purchase_id USER GENERATED,
	customer WITH OPTIONS SCOPE Customer,
	product WITH OPTIONS SCOPE Product
);