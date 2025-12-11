# Usar una imagen base con Java 17
FROM eclipse-temurin:17-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR generado por Maven
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto 8081
EXPOSE 8081

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8081"]