# Imagem com Java 21 + Maven
FROM maven:3.9.9-eclipse-temurin-21

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia tudo do projeto
COPY . .

# Porta padrão do Quarkus
EXPOSE 9000

# Roda em modo dev (hot reload)
CMD ["mvn", "quarkus:dev", "-Dquarkus.http.host=0.0.0.0"]