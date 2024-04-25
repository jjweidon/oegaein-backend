FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/oegaein-0.0.1-SNAPSHOT.jar app.jar
# install chorme driver
#RUN apt-get -y update
#RUN apt -y install wget
#RUN apt -y install unzip
#RUN apt -y install curl
#RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
#RUN apt -y install ./google-chrome-stable_current_amd64.deb
#RUN wget -O /tmp/chromedriver.zip http://chromedriver.storage.googleapis.com/` curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE`/chromedriver_linux64.zip
#RUN mkdir chrome
#RUN unzip /tmp/chromedriver.zip chromedriver -d /app/chrome
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "./app.jar"]