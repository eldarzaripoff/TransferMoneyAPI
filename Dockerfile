FROM openjdk:17-jdk-slim
EXPOSE 8080
COPY target/TransferMoneyAPI-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
LABEL authors="zarip"