# Build aşaması için Gradle image
FROM gradle:8.5-jdk17 AS build

# Proje dosyalarını Gradle kullanıcısına ait olacak şekilde kopyala
COPY --chown=gradle:gradle . /home/gradle/src

# Çalışma dizinini ayarla
WORKDIR /home/gradle/src

# Bağımlılıkları indir ve bootJar'ı oluştur
RUN gradle clean bootJar --no-daemon

# Production için daha hafif bir JDK image kullan
FROM openjdk:17-jdk-slim

# Uygulamanın çalışacağı portu aç
EXPOSE 8080

# Uygulama için bir çalışma dizini oluştur
RUN mkdir /app

# Oluşturulan .jar dosyasını build aşamasından kopyala
COPY --from=build /home/gradle/src/build/libs/*SNAPSHOT.jar /app/spring-boot-application.jar

# Uygulamayı başlatmak için komut
ENTRYPOINT ["java", "-jar", "/app/spring-boot-application.jar"]
