FROM public.ecr.aws/amazoncorretto/amazoncorretto:17
EXPOSE 8083
ADD target/authorization-microservice-0.0.1-SNAPSHOT.jar authorization-microservice-0.0.1-SNAPSHOT.jar 
ENTRYPOINT ["java","-jar","/authorization-microservice-0.0.1-SNAPSHOT.jar"]
