CREATE TABLE "Users" (
    "user_id" SERIAL PRIMARY KEY,
    "username" VARCHAR(255) Unique not null,
    "email" VARCHAR(255) Unique not null,
    "password" VARCHAR(255) not null,
    "date_of_birth" DATE not null,
    "timezone" VARCHAR(50),
    "status" ENUM('online', 'offline', 'busy') DEFAULT 'online'
	"timestamp" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	"biography" VARCHAR(1000)
);

CREATE TABLE "Reviews"(
    "offer_id" int ,
    "sender_id" int,
    "reciever_id" int,
    "rating" int not null,
    "comment" VARCHAR(200) not null,
    "reply" VARCHAR(200),
    PRIMARY KEY("offer_id", "sender_id", "reciever_id"),
    FOREIGN KEY("sender_id") REFERENCES "Users"("user_id"),
    FOREIGN KEY("reciever_id") REFERENCES "Users"("user_id"),
    "timestamp" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
)



CREATE OR REPLACE PROCEDURE createUser(
    IN p_username VARCHAR(255),
    IN p_email VARCHAR(255),
    IN p_password VARCHAR(255),
    IN p_date_of_birth DATE
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO "Users" ("username", "email", "password", "date_of_birth")
    VALUES (p_username, p_email, p_password, p_date_of_birth);
END;
$$;



CREATE OR REPLACE PROCEDURE deleteUser(
    IN p_user_id VARCHAR(255)
)

LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM "Users" WHERE "user" = p_user_id;
END;
$$;



CREATE OR REPLACE PROCEDURE editUser(
	IN p_username VARCHAR(255),
    IN p_email VARCHAR(255),
    IN p_password VARCHAR(255),
    IN p_status VARCHAR(255),
	 IN p_timezone VARCHAR(255),
    IN p_user_id VARCHAR(255),
	IN p_biography VARCHAR(255)
)

LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE Users
    SET "username" = p_username,
        "email" = p_email,
        "password" = p_password,
        "timezone" = p_timezone,
		"status" = p_status,
        "date_of_birth" = p_date_of_birth,
		"biography" = p_biography,

		
    WHERE "user_id" = p_user_id;
END;
$$;



CREATE OR REPLACE PROCEDURE getUser(
 	IN p_user_id VARCHAR(255)
	)

LANGUAGE plpgsql
AS $$
BEGIN
	Select * from "User"
    WHERE "user_id" = p_user_id;
END;
$$;
	
	
	
CREATE OR REPLACE PROCEDURE isUser(
 	IN p_username VARCHAR(255),
    IN p_password VARCHAR(255)
	)

LANGUAGE plpgsql
AS $$
BEGIN
	Select "user_id" from "User"
    WHERE "username" = p_username and "password" = p_password;
END;
$$;



CREATE OR REPLACE PROCEDURE createReview(
    IN user_id int,
    IN sender_id int,
    IN reciever_id int,
    IN rating int,
    IN comment VARCHAR(255)
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO "Reviews" ("offer_id", "sender_id", "reciever_id", "rating", "comment")
    VALUES (user_id, sender_id, reciever_id, rating, comment);
END;
$$;


	
CREATE OR REPLACE PROCEDURE editReview(
    IN user_id int,
    IN sender_id int,
    IN reciever_id int,
    IN rating int,
    IN comment VARCHAR(255)
    IN reply VARCHAR(255)
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE Reviews
    SET "rating" = rating,
        "comment" = comment,
        "reply" = reply,

		
    WHERE "user_id" = user_id AND "sender_id"= sender_id AND "reciever_id"= reciever_id;
END;
$$;




