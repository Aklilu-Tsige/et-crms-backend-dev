-----
--- USER TABLE
--------------------------------------
CREATE TABLE users
(
    id                BIGINT
        NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 )
        CONSTRAINT countries_PK PRIMARY KEY,
    user_name         VARCHAR(50),
    first_name        VARCHAR(50),
    last_name         VARCHAR(50),
    user_title        VARCHAR(50),
    email             VARCHAR(50),
    mobile            VARCHAR(15),
    registration_date DATE,
    activated         BOOLEAN,
    profile_image     VARCHAR
);
ALTER TABLE users owner to postgres;
