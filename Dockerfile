# Koristi Java 21 JDK kao bazni image
FROM eclipse-temurin:21-jdk

# Postavi radni direktorijum unutar kontejnera
WORKDIR /app

# Kopiraj JAR fajl u kontejner
COPY ./target/JobEasy-0.0.1-SNAPSHOT.jar app.jar

# Eksponiraj port koji koristi aplikacija
EXPOSE 8080

# Pokreni aplikaciju kada kontejner startuje
ENTRYPOINT ["java", "-jar", "app.jar"]


