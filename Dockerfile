FROM maven:3-jdk-8
ADD . /rapid
WORKDIR /rapid
CMD ["mvn","jetty:run"]
