FROM gradle:8.5-jdk17 AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Gradle Wrapper'a izin ver
RUN chmod +x ./gradlew

# Clean ve bootJar işlemi
RUN ./gradlew clean bootJar --no-daemon

FROM openjdk:17-jdk-slim

EXPOSE 8080

# Uygulama dosyasını kopyala
COPY --from=build /home/gradle/src/build/libs/notes-api-0.0.1-SNAPSHOT.jar /app/spring-boot-application.jar

# Uygulamayı başlat
ENTRYPOINT ["java", "-jar", "/app/spring-boot-application.jar"]
