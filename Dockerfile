FROM ubuntu:20.04
RUN apt-get update
RUN install openjdk:11
EXPOSE 8080

ENV TZ=Asia/Seoul
COPY ./src/main

CMD build
ENTRYPOINT ["java","-jar","/app.jar"]