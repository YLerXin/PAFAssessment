# ==============================
# Stage 1: Build Application
# ==============================
#FROM openjdk:23-jdk-oracle AS builder
FROM eclipse-temurin:23.0.1_11-jdk AS builder

ARG COMPILE_DIR=/compiledir
WORKDIR ${COMPILE_DIR}

RUN java -version && javac -version
# Copy Maven Wrapper
COPY mvnw . 
COPY mvnw.cmd .
COPY .mvn .mvn

# Make sure your mvnw has executable permission
RUN chmod +x mvnw

# Copy the pom.xml so we can do dependency resolution if needed
COPY pom.xml .

# Optionally resolve dependencies first (faster incremental builds)
# RUN ./mvnw dependency:resolve

# Now copy the source code
COPY src src

# Build the application without running tests
RUN ./mvnw package -Dmaven.test.skip=true

# ==============================
# Stage 2: Runtime Environment
# ==============================
#FROM openjdk:23-jdk-oracle
FROM eclipse-temurin:23.0.1_11-jdk

ARG WORK_DIR=/app
WORKDIR ${WORK_DIR}

# Copy the built JAR file from builder stage
COPY --from=builder /compiledir/target/movies-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
ENV SERVER_PORT=8080
EXPOSE ${SERVER_PORT}

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
