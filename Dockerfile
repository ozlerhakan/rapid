FROM openjdk:8-jre-alpine

# jar files are copied after travis finishes the build process
# you can use the Dockerfile.multi file to generate the same image locally
COPY target/dependency/jetty-runner.jar target/rapid.war  /rapid/

ENTRYPOINT ["java", "-jar", "/rapid/jetty-runner.jar"]

CMD ["/rapid/rapid.war"]