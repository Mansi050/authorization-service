FROM public.ecr.aws/amazoncorretto/amazoncorretto:17
EXPOSE 8083
ADD target/authorization-service.jar authorization-service.jar
ENTRYPOINT ["java","-jar","/authorization-microservice-0.0.1-SNAPSHOT.jar"]
