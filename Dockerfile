FROM maven:3.8.6-openjdk-11 AS build

COPY src/ /src/
COPY pom.xml /pom.xml

RUN mvn clean install -DskipTests

FROM openjdk:11-jre AS run

COPY --from=build target/social-network-*.jar social-network.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./social-network.jar"]