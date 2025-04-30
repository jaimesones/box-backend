# Usa una imagen base de Maven para construir la aplicación
FROM maven:3.8.1-openjdk-11 AS build

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo pom.xml y descarga las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia el código fuente del proyecto
COPY src ./src

# Compila y empaqueta la aplicación
RUN mvn clean package -DskipTests

# Usa una imagen base de OpenJDK para ejecutar la aplicación
FROM openjdk:11-jre-slim

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR generado desde la fase de construcción
COPY --from=build /app/target/box-backend-1.0.0-SNAPSHOT.jar app.jar

# Expone el puerto en el que correrá la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]