# 1. Use official Tomcat with Java 17
FROM tomcat:10.1-jdk17

# 2. Remove default Tomcat apps
RUN rm -rf /usr/local/tomcat/webapps/*

# 3. Copy your WAR file into Tomcat
COPY target/PetroMax-1.0.war /usr/local/tomcat/webapps/ROOT.war

# 4. Expose Tomcat port
EXPOSE 8080

# 5. Start Tomcat
CMD ["catalina.sh", "run"]
