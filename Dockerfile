FROM openjdk:17-jdk-slim
LABEL maintainer="joaovitor.jaques.7748@gmail.com"
COPY target/rinhaBackEnd23-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]