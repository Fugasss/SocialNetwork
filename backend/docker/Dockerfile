FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

COPY gradlew gradlew.bat ./
COPY gradle/ ./gradle/
COPY build.gradle settings.gradle ./

RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon --parallel

COPY src ./src

RUN ./gradlew bootJar --no-daemon -x test --parallel

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]