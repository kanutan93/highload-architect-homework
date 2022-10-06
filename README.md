# ДЗ по курсу Highload Architect

### Данные для подключения к БД в application.properties:
```
spring.datasource.url=jdbc:mysql://localhost:3306/social_network
spring.datasource.username=root
spring.datasource.password=password
```

### Запуск приложения:
Для запуска приложения необходимы следующие зависимости:

- JDK 11
- Apache Maven 3.1.1+
- MySQL

```shell
mvn clean install

java -jar target/social-network-0.0.1-SNAPSHOT.jar
```