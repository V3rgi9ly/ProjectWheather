create table users(
    id SERIAL PRIMARY KEY,
    Login VARCHAR(255),
    Password VARCHAR(255)
);

create table locations(
    id SERIAL PRIMARY KEY,
    Name VARCHAR(255),
    UserId INT,
    Latitude DECIMAL,
    Longitude DECIMAL
);

create table sessions(
    id UUID PRIMARY KEY,
    UserId INT,
    ExpiresAt DATE
);