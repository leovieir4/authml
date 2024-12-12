FROM openjdk:21-jdk
WORKDIR /app
COPY build/libs/*.jar app.jar
COPY credentials /root/.aws/credentials
EXPOSE 8081

ENTRYPOINT ["java", "-Dspring.profiles.active=not_local", "-jar", "app.jar"]