FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
COPY --from=build /demo_new/target/demo_new-1.0-SNAPSHOT.jar app.jar
EXPOSE 9092
CMD ["java", "-jar", "app.jar"]