FROM maven:3-jdk-8-alpine
ADD . /rapid
WORKDIR /rapid

EXPOSE 8080
CMD ["mvn","jetty:run"]
