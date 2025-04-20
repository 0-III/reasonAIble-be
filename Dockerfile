FROM amazoncorretto:21
COPY build/libs/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/app.jar"]
