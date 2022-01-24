# IMAGE USED TO BUILD DOCKERFILE
FROM openjdk:11-jdk as build


# SET WORKDIR
WORKDIR /order_service

# CP ALL FILES TO WORKDIR
COPY . .

#RUN chmod a+x gradlew
RUN chmod a+x gradlew
# CREATE FATJAR FILE
RUN ./gradlew bootJar

# CREATE FATJAR FILE
RUN ./gradlew build -x test

##################################################

# IMAGE USED FOR CONTAINER RUNTIME
FROM openjdk:11-jre-slim as run


# SET WORKDIR
WORKDIR /order_service

COPY --from=build /order_service/build/libs/*-SNAPSHOT.jar /order_service/app.jar

ENTRYPOINT ["java","-jar","/order_service/app.jar"]
