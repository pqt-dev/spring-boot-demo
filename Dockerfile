## Dockerfile
#
FROM gradle:8.14.3-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test

FROM openjdk:21-ea-21-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar


ENTRYPOINT ["java","-jar","app.jar"]

