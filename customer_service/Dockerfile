##################################################
#                                                #
#   DOCKERFILE TO BUILD THE MICROSERVICE IMAGE   #
#                                                #
##################################################

# IMAGE USED TO BUILD DOCKERFILE
FROM openjdk:8-jdk-alpine as build

# SET WORKDIR
WORKDIR /customer_service

# CP ALL FILES TO WORKDIR
COPY . .

RUN chmod a+x gradlew

# CREATE FATJAR FILE
RUN ./gradlew bootJar

##################################################

# IMAGE USED FOR CONTAINER RUNTIME
FROM openjdk:8-jre-alpine as run

# SET WORKDIR
WORKDIR /customer_service

COPY --from=build /customer_service/build/libs/*-SNAPSHOT.jar /customer_service/app.jar

ENTRYPOINT ["java","-jar","/customer_service/app.jar"]
