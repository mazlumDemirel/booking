FROM gradle:8.11.1-jdk AS build
COPY src/ ./src
COPY build.gradle settings.gradle  ./
RUN gradle clean build

FROM openjdk:22-jdk AS builder
COPY --from=build ./home/gradle/build/libs ./
ENTRYPOINT ["java","-jar","app.jar"]
