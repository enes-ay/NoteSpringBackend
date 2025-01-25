FROM eclipse-temurin:17-jdk-focal AS build
WORKDIR /workspace/app

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:17-jre-focal

EXPOSE 8080

RUN mkdir /app

COPY --from=build /workspace/app/build/libs/*.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-jar","/app/spring-boot-application.jar"] 