FROM openjdk:8-jdk-alpine

RUN apk add --no-cache curl tar bash

ENV MAVEN_VERSION=3.5.0

RUN mkdir -p /usr/share/maven && \
    curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar -xzC /usr/share/maven --strip-components=1 && \
    ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV USER_HOME_DIR="/root"
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

# speeding up a bit maven
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

COPY pom.xml /rapid/

WORKDIR /rapid

# use 1 thread per available CPU core then remove the target directory
RUN mvn -T 1C install && rm -rf target

#COPY src /rapid/src
COPY src/main /rapid/src/main/

EXPOSE 8080

ENTRYPOINT ["/usr/bin/mvn"]

CMD ["jetty:run"]
