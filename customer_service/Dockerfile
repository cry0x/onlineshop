# 1. ./gradlew build && java -jar build/libs/gs-spring-boot-docker-0.1.0.jar
# 2. docker build --build-arg JAR_FILE=build/libs/\*.jar -t springio/gs-spring-boot-docker .
# 3. docker run -p 8080:8080 springio/gs-spring-boot-docker
FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/\*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]