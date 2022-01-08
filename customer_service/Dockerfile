# 1. ./gradlew build && java -jar build/libs/gs-spring-boot-docker-0.1.0.jar
# 2. docker build --build-arg JAR_FILE=build/libs/\*.jar -t springio/gs-spring-boot-docker .
# 3. docker run -p 8080:8080 springio/gs-spring-boot-docker
# FROM openjdk:8-jdk-alpine
# EXPOSE 8080
# ARG JAR_FILE=build/libs/\*.jar
# COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]


# IMAGE USED TO BUILD DOCKERFILE
FROM openjdk:8-jdk-alpine as build

# SET WORKDIR
WORKDIR /customer_service

# CP ALL FILES TO WORKDIR
COPY . .

# CREATE FATJAR FILE
RUN ./gradlew bootJar

##################################################

# IMAGE USED FOR CONTAINER RUNTIME
FROM openjdk:8-jre-alpine as run

# SET WORKDIR
WORKDIR /opt/app

# ACCEPT TCP-REQ ON PORT 8080
EXPOSE 8080/tcp

ARG JAR_FILE=/customer_service/build/libs/\*.jar
COPY --from=build ${JAR_FILE} /opt/app.jar
ENTRYPOINT ["java","-jar","/opt/app.jar"]