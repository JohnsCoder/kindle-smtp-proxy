# Use Maven to build the project
FROM maven:3.9.8-eclipse-temurin-21-alpine AS builder
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Use a lightweight image to run the app
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /build/target/kindle-smtp-proxy-1.0-SNAPSHOT.jar kindle-smtp-proxy.jar
CMD ["java", "-jar", "kindle-smtp-proxy.jar"]