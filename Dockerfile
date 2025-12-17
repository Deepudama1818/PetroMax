# ---------- STAGE 1: Build WAR ----------
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom and source code
COPY pom.xml .
COPY src ./src

# Build the WAR
RUN mvn clean package -DskipTests

# ---------- STAGE 2: Run on Tomcat ----------
FROM tomcat:10.1-jdk17

# Remove default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR from builder stage
COPY --from=builder /app/target/PetroMax-1.0.war \
    /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
