FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE
ADD target/${JAR_FILE} docker-cassandra-app.jar
ENTRYPOINT ["java","-jar","docker-cassandra-app.jar"]