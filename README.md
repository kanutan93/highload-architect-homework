# ДЗ по курсу Highload Architect

### Запуск приложения:
#### Docker:
Для запуска приложения используя docker необходимо выполнить:
```shell
docker-compose up
```
Согласно docker-compose.yaml, будут запущены контейнеры:
- backend 8080:8080
- db 3306:3306

#### Локально:
Для сборки jar локально и запуска приложения необходимы следующие зависимости:

- JDK 11
- Apache Maven 3.1.1+
- MySQL

```shell
mvn clean install

java -jar target/social-network-0.0.1-SNAPSHOT.jar
```
Данные для подключения к БД в application.properties:
```
spring.datasource.url=jdbc:mysql://localhost:3306/social_network
spring.datasource.username=root
spring.datasource.password=password
```

### Тестирование API:
Для ручного тестирования API необходимо импортировать social-network-morozov.postman_collection.json в Postman 
