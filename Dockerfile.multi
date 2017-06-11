FROM openjdk:8-jdk-alpine as build

RUN apk add --no-cache curl tar

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
COPY src/main /rapid/src/main/

WORKDIR /rapid

# use 1 thread per available CPU core then remove the target directory
RUN mvn -T 1C install -DskipTests

# prod stage
FROM openjdk:8-jre-alpine

COPY --from=build /rapid/target/dependency/jetty-runner.jar  /rapid/
COPY --from=build /rapid/target/rapid.war  /rapid/

ENTRYPOINT ["java", "-jar", "/rapid/jetty-runner.jar", "/rapid/rapid.war"]