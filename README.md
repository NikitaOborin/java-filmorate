# java-filmorate
Template repository for Filmorate project.


## **ER-диаграмма**
![ER диаграмма промежуточное ТЗ11](https://user-images.githubusercontent.com/114754182/226403741-7c9f75b9-4bad-4366-8383-1c0cd76fdc9b.png)

## **Примеры SQL-запросов**

Получение топ 10 фильмов:

```
SELECT film_name.films
FROM films
WHERE film_id IN (SELECT film_id.likes
                  FROM likes
                  GROUP BY film_id
                  ORDER BY SUM(user_id) DESC
                  LIMIT 10);
```                  
