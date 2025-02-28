# Utilizar una imagen base de OpenJDK con Java 17
FROM openjdk:17-jdk-slim
WORKDIR /app
# Añadir el jar de la aplicación al contenedor
COPY target/nttdata-msa-account-transaction-1.0-SNAPSHOT.jar /app/nttdata-msa-account-transaction-1.0-SNAPSHOT.jar

# Exponer el puerto que utiliza la aplicación
EXPOSE 8081

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/nttdata-msa-account-transaction-1.0-SNAPSHOT.jar"]