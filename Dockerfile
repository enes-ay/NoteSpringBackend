FROM eclipse-temurin:17-jdk-focal AS build
WORKDIR /workspace/app

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Bağımlılıkları önbelleğe al
RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon

# Kaynak kodlarını ekle ve derle
COPY src src
RUN ./gradlew bootJar --no-daemon
