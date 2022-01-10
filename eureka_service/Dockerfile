FROM openjdk:11-jdk as build

WORKDIR /build/app

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src

RUN chmod a+x mvnw

RUN ./mvnw clean package

##################################################

FROM openjdk:11-jre-slim as run

COPY --from=build /build/app/target/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
