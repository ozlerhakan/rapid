FROM openjdk:8-jre-alpine

COPY /home/travis/build/ozlerhakan/rapid/target/dependency/jetty-runner.jar /home/travis/build/ozlerhakan/rapid/target/rapid.war  /rapid/

ENTRYPOINT ["java", "-jar", "/rapid/jetty-runner.jar", "/rapid/rapid.war"]