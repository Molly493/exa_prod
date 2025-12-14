FROM eclipse-temurin:17-alpine 
WORKDIR /app 	
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar  
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8081"]
#CMD ["java", "-jar", "app.jar", "--server.port=8081"]