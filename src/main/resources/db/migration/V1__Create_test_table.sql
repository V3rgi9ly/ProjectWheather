create table if not exists users(
    id SERIAL PRIMARY KEY,
    login VARCHAR(255),
    password VARCHAR(255)
);

create table if not exists locations(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    user_id INT,
    latitude DECIMAL,
    longitude DECIMAL
);

create table if not exists sessions(
    id UUID PRIMARY KEY,
    user_id INT,
    expires_at  TIMESTAMP WITHOUT TIME ZONE
);