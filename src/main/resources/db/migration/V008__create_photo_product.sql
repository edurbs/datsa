CREATE TABLE datsa.product_photo (
	product_id BIGINT NOT NULL,
	file_name varchar(150) NOT NULL,
	description varchar(150) NULL,
	content_type varchar(80) NOT NULL,
	`size` INT NOT NULL,
	CONSTRAINT product_photo_pk PRIMARY KEY (product_id),
	CONSTRAINT product_photo_product_FK FOREIGN KEY (product_id) REFERENCES datsa.product(id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;
