FROM eclipse-temurin:22-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} puppygram.jar
ENTRYPOINT ["java", "-jar", "/puppygram.jar"]
