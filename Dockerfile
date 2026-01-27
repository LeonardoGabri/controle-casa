# ---------- build ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY src ./src
RUN mvn -B -q package -DskipTests

# ---------- runtime ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /build/target/quarkus-app /app/quarkus-app

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/quarkus-app/quarkus-run.jar"]