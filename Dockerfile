FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

# Gradle dosyalarını kopyala
COPY gradle gradle
COPY gradlew .
COPY settings.gradle.kts .
COPY build.gradle.kts .

# Gradle wrapper'a execute izni ver
RUN chmod +x gradlew

# Bağımlılıkları indir
RUN ./gradlew dependencies --no-daemon

# Kaynak kodunu kopyala
COPY src src

# Uygulamayı derle
RUN ./gradlew bootJar --no-daemon

EXPOSE 8080

# Uygulamayı çalıştır
ENTRYPOINT ["java", "-jar", "/app/build/libs/notes-api-0.0.1-SNAPSHOT.jar"]
