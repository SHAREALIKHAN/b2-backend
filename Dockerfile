# Use Maven to build the project first
FROM maven:3.9.6-eclipse-temurin-17 as builder

WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

# Now use a slim JDK image to run the app
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]