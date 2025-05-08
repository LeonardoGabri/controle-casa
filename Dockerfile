FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /build/target/quarkus-app/ /app/
CMD ["java", "-jar", "quarkus-run.jar"]
