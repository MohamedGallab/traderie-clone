FROM openjdk:21-jdk
WORKDIR /usr/app
COPY ./users-service/target/users-service-1.0-SNAPSHOT-exec.jar .
CMD ["java", "-jar", "users-service-1.0-SNAPSHOT-exec.jar"]