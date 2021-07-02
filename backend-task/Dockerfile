FROM openjdk:8-jdk-alpine

EXPOSE 8080

COPY target/task-0.0.1-SNAPSHOT.jar app.jar

COPY sample.db sample.db

ENTRYPOINT ["java","-jar","/app.jar"]
