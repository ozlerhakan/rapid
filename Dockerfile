FROM maven:3-jdk-8
MAINTAINER Hakan Ozler <hakan.ozler@kodcu.com>
ADD . /rapid
WORKDIR /rapid
CMD ["mvn","jetty:run"]
