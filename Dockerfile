FROM openjdk:8-jre-alpine
MAINTAINER Petz

# Common
ENV TZ America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# SpringBoot
COPY target/petz-server-http-rest-0.0.1.jar app.jar

VOLUME /tmp
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Xmx512m","-Xss128m","-jar","app.jar"]
