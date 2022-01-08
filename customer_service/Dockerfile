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