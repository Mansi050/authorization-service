FROM openjdk:17
EXPOSE 8083
ADD target/authorization-microservice-0.0.1-SNAPSHOT.jar authorization-microservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar authorization-microservice-0.0.1-SNAPSHOT.jar"]
