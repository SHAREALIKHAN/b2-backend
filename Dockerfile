# Use Maven with JDK 21 to build
FROM maven:3.9.6-eclipse-temurin-21 as builder

WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

# Use JDK 21 to run
FROM openjdk:21-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]