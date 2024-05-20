FROM openjdk:21-jdk
WORKDIR /usr/app
COPY ./messages-service/target/messages-service-1.0-SNAPSHOT-exec.jar .
CMD ["java", "-jar", "messages-service-1.0-SNAPSHOT-exec.jar"]