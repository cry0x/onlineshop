FROM openjdk:11-jdk as build

WORKDIR /build/app

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src

RUN ./mvnw clean install

FROM openjdk:11-jre-slim as run

COPY --from=build /build/app/target/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]