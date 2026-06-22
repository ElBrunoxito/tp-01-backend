# Etapa 1: Construcción del JAR (usando Maven)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:25-jdk-alpine
ARG JAR_FILE=./target/tp01-backend-hwt-1.0.0.jar
WORKDIR /app
# Copia el JAR generado desde la etapa de construcción
COPY --from=build /app/target/tp01-backend-hwt-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
