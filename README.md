# java-filmorate


![image](https://github.com/user-attachments/assets/b7cbd629-aa0a-42d6-91e6-d4b3a26a80ac)

## Таблицы

### Film
Film_id - первичный ключ, integer

Name - имя, varchar 

Description - описание, varchar

Release_date - дата выхода, date

Duration - длительность фильма, integer

Genre - жанр, внешний ключ, отсылается к Genre.Genre_id, integer 

Rating - рейтинг, внешний ключ, отсылается к Rating.Rating_id, integer

### User
User_id - первичный ключ, integer

Email - почта, varchar 

Login - логин, varchar

Name - имя, varchar 

Birthday - день рождения, date

### Genre
Genre_id - первичный ключ, integer

Name - название жанра, varchar 

### Rating
Rating_id - первичный ключ, integer

Name - рейтинг, varchar

### Friends
User_id - первичный и внешний ключ, отсылается к User.User_id, integer 

Friend_id - первичный и внешний ключ, отсылается к User.User_id, integer 

Status - статус дружбы (подтвержденная или нет), varchar

### Likes
Film_id - первичный и внешний ключ, отсылается к Film.Film_id, integer 

User_id - первичный и внешний ключ, отсылается к User.User_id, integer 


## Примеры запросов
пользователи с днем рождения в 2000 году
```
SELECT *
FROM User
WHERE EXTRACT( YEAR FROM Birthday) = 2000
```
все фильмы с рейтингом G
```
SELECT *
FROM Film
LEFT JOIN Rating ON Rating.Rating_id=Film.Rating
WHERE Rating.Name = 'G';
```
друзья пользователя с id 1
```
SELECT f.Friend_id, Status, u.Login
FROM Friends f
LEFT JOIN User u ON f.User_id=u.User_id
WHERE f.User_id = 1;
```
фильмы с жанром комедия или драма
 ```
SELECT f.Name, f.Description, f.Duration
FROM Film f
LEFT JOIN Genre g ON g.Genre_id=f.Genre
WHERE g.Name = 'Комедия' OR g.Name = 'Драма';
```
5 фильмов с наибольшим количеством лайков
```
SELECT f.Film_id, f.Name, 
    COUNT(l.User_id) AS Likes_Count
FROM Film f
LEFT JOIN Likes l ON f.Film_id = l.Film_id
GROUP BY f.Film_id, f.Name
ORDER BY Likes_Count DESC
LIMIT 5;
```
