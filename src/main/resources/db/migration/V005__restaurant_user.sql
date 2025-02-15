CREATE TABLE datsa.restaurant_user (
	restaurant_id BIGINT NOT NULL,
	user_id BIGINT NOT NULL,
	CONSTRAINT restaurant_user_pk PRIMARY KEY (restaurant_id,user_id),
	CONSTRAINT restaurant_user_restaurant_FK FOREIGN KEY (restaurant_id) REFERENCES datsa.restaurant(id),
	CONSTRAINT restaurant_user_user_FK FOREIGN KEY (user_id) REFERENCES datsa.`user`(id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;
