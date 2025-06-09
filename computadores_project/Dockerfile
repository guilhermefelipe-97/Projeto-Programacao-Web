FROM maven:3.8.5-openjdk-17 AS build

# Diretório de trabalho
WORKDIR /app

# Copia o arquivo pom.xml
COPY pom.xml .

# Baixa todas as dependências
RUN mvn dependency:go-offline -B

# Copia o código-fonte
COPY src ./src

# Compila e empacota a aplicação
RUN mvn package -DskipTests

# Imagem final
FROM openjdk:17-jdk-slim

# Diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR da fase de build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
