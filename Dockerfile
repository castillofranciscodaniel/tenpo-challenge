FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/tenpo-challenge-1.0.0.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","tenpo-challenge-1.0.0.jar"]