# ===== ETAPA 1: BUILD =====
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src ./src
RUN mvn clean package -DskipTests -q


# ===== ETAPA 2: RUNTIME =====
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8761

ENV PORT=8761

ENTRYPOINT ["java", "-jar", "app.jar"]