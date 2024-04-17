FROM openjdk:17-alpine
WORKDIR /app
COPY build/libs/oegaein-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "./app.jar"]
