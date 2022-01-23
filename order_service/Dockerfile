# IMAGE USED TO BUILD DOCKERFILE
<<<<<<< HEAD
FROM openjdk:11 as build
#USER root
=======
FROM openjdk:11-jdk as build

>>>>>>> main
# SET WORKDIR
WORKDIR /order_service

# CP ALL FILES TO WORKDIR
COPY . .
<<<<<<< HEAD
#RUN chmod a+x gradlew

# CREATE FATJAR FILE
RUN ./gradlew bootJar
=======

RUN chmod a+x gradlew

# CREATE FATJAR FILE
RUN ./gradlew build -x test
>>>>>>> main

##################################################

# IMAGE USED FOR CONTAINER RUNTIME
<<<<<<< HEAD
FROM openjdk:11 as run
=======
FROM openjdk:11-jre-slim as run
>>>>>>> main

# SET WORKDIR
WORKDIR /order_service

COPY --from=build /order_service/build/libs/*-SNAPSHOT.jar /order_service/app.jar

ENTRYPOINT ["java","-jar","/order_service/app.jar"]
