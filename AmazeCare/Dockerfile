#Use a lightweight JRE for smaller images
FROM eclipse-temurin:21-jre-alpine
#ADD the pre-built JAR file
ADD target/AmazeCare-0.0.1-SNAPSHOT.jar AmazeCare-0.0.1-SNAPSHOT.jar
# Expose the application port
EXPOSE 8080
# Run the application
ENTRYPOINT ["java", "-jar", "/AmazeCare-0.0.1-SNAPSHOT.jar"]