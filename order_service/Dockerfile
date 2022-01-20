# IMAGE USED TO BUILD DOCKERFILE
FROM openjdk:11 as build
#USER root
# SET WORKDIR
WORKDIR /order_service

# CP ALL FILES TO WORKDIR
COPY . .
#RUN chmod a+x gradlew

# CREATE FATJAR FILE
RUN ./gradlew bootJar

##################################################

# IMAGE USED FOR CONTAINER RUNTIME
FROM openjdk:11 as run

# SET WORKDIR
WORKDIR /order_service

COPY --from=build /order_service/build/libs/*-SNAPSHOT.jar /order_service/app.jar

ENTRYPOINT ["java","-jar","/order_service/app.jar"]
