FROM maven:3.8-openjdk-8-slim

COPY ./ ./

RUN mvn clean package

CMD ["java", "-jar", "./target/player-communication-0.0.1-SNAPSHOT.jar"]