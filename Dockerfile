# Stage 1: Build the application
FROM registry.access.redhat.com/ubi8/openjdk-17 AS builder

USER root
WORKDIR /app

# Copy Maven wrapper and pom.xml first for dependency caching
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM registry.access.redhat.com/ubi8/openjdk-17-runtime

ENV LANGUAGE='en_US:en'

# Copy the built artifact from the builder stage
COPY --from=builder /app/target/gratuity-calculator-1.0.0.jar /deployments/app.jar

# Expose the application port
EXPOSE 8080

# OpenShift runs containers as non-root by default
# The UBI images already handle this correctly
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"
ENV JAVA_APP_JAR="/deployments/app.jar"

ENTRYPOINT ["java", "-jar", "/deployments/app.jar"]
