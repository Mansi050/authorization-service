FROM public.ecr.aws/amazoncorretto/amazoncorretto:17
ADD target/authorization-microservice-0.0.1-SNAPSHOT.jar authorization-microservice-0.0.1-SNAPSHOT.jar 
EXPOSE 8083
ENTRYPOINT ["java","-jar","/authorization-microservice-0.0.1-SNAPSHOT.jar"]
