MERGE INTO mpa AS mt
USING (VALUES
    ('G'),
    ('PG'),
    ('PG-13'),
    ('R'),
    ('NC-17')
) AS source(name)
ON mt.name = source.name
WHEN NOT MATCHED THEN
    INSERT (name) VALUES (source.name);

MERGE INTO genre AS gt
USING (VALUES
('Комедия'),
('Драма'),
('Мультфильм'),
('Триллер'),
('Документальный'),
('Боевик')
) AS source(name)
ON gt.name = source.name
WHEN NOT MATCHED THEN
    INSERT (name) VALUES (source.name);
