FROM eclipse-temurin:21-jre-jammy
COPY demo_new-1.0-SNAPSHOT-jar-with-dependencies.jar server.jar
CMD ["java", "-Djava.awt.headless=true", "-jar", "server.jar"]
