FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY /src/main/resources/vosk-model /tmp/vosk-model
RUN mvn clean package

FROM eclipse-temurin:17
WORKDIR /app
COPY --from=build /app/target/SpeechToText-0.0.1-SNAPSHOT.jar .
COPY --from=build /app/target/classes/vosk-model /tmp/vosk-model
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "SpeechToText-0.0.1-SNAPSHOT.jar"]
