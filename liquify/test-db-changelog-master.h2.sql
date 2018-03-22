--liquibase formatted sql

--changeset YangChenming:1
CREATE TABLE EDMI_PROVIDER (
	id 				INT AUTO_INCREMENT PRIMARY KEY,
	name 			VARCHAR(255) NOT NULL,
	external_id		VARCHAR(255),
	description 	VARCHAR(1000),
	CONSTRAINT edmi_provider_unique UNIQUE(name)
);

--changeset YangChenming:1
CREATE TABLE EDMI_BUSINESS (
	name 			VARCHAR(255) PRIMARY KEY
);

--changeset YangChenming:1
CREATE TABLE EDMI_DOMAIN (
	name 			VARCHAR(255) PRIMARY KEY
);

--changeset YangChenming:1
DROP TABLE IF EXISTS EDMI_DOMAIN CASCADE;

--changeset YangChenming:1
DROP TABLE IF EXISTS EDMI_BUSINESS CASCADE;

