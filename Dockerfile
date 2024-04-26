FROM openjdk:17-jdk-slim as builder
# install chorme driver
WORKDIR /app
RUN apt-get -y update
RUN apt -y install wget
RUN apt -y install unzip
RUN apt -y install curl
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN wget https://storage.googleapis.com/chrome-for-testing-public/124.0.6367.91/linux64/chromedriver-linux64.zip
RUN mkdir chrome
RUN unzip ./chromedriver-linux64.zip
RUN mv ./chromedriver-linux64/chromedriver /app/chrome

FROM openjdk:17-jdk-slim
WORKDIR /app
# install chrome
COPY --from=builder /app/google-chrome-stable_current_amd64.deb .
RUN apt-get -y update
RUN apt -y install ./google-chrome-stable_current_amd64.deb
# get chromedriver
COPY --from=builder /app/chrome/chromedriver ./chrome/
COPY build/libs/oegaein-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "./app.jar"]