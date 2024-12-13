# Stage 1: Build the application
FROM ubuntu:latest AS build
WORKDIR /app

# Install Java JDK
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk && \
    rm -rf /var/lib/apt/lists/*

# Install unzip and wget
RUN apt-get update && apt-get install -y unzip wget

# Download and install Gradle 8.5 manually
RUN wget https://services.gradle.org/distributions/gradle-8.5-bin.zip && \
    unzip gradle-8.5-bin.zip && \
    mv gradle-8.5 /opt/gradle && \
    ln -s /opt/gradle/bin/gradle /usr/bin/gradle

COPY . .
RUN gradle clean build

# Stage 2: Create the runtime image
FROM ubuntu:latest
WORKDIR /app

# Install Java JRE
RUN apt-get update && \
    apt-get install -y openjdk-21-jre && \
    rm -rf /var/lib/apt/lists/*

# Copy the JAR file (replace with the actual name)
COPY --from=build /app/build/libs/authentication-0.0.1-SNAPSHOT.jar app.jar

# Set JAR permissions
RUN chmod 755 /app/app.jar

# Copy AWS credentials (if needed)
COPY credentials /root/.aws/credentials

EXPOSE 8081
ENTRYPOINT ["java", "-Dspring.profiles.active=not_local", "-jar", "app.jar"]