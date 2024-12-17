CREATE TABLE genre (
    genre_id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
    foreign key (
);


CREATE TABLE mpa (
    mpa_id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);


CREATE TABLE users (
    user_id INT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    birthday DATE NOT NULL
);

CREATE TABLE films (
    film_id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    release_date DATE NOT NULL,
    duration INT NOT NULL,
    mpa_id INT,
    FOREIGN KEY (mpa_id) REFERENCES mpa(mpa_id)
);


CREATE TABLE friends (
    user_id INT,
    friend_id INT,
    status VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (friend_id) REFERENCES users(user_id)
);


CREATE TABLE likes (
    film_id INT,
    user_id INT,
    PRIMARY KEY (film_id, user_id),
    FOREIGN KEY (film_id) REFERENCES films(film_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

create table film_genre (
    film_id INT,
    genre_id INT
    foreign key (film_id) REFERENCES films(film_id),
    FOREIGN KEY (genre_id) REFERENCES genre(genre_id)
    );
