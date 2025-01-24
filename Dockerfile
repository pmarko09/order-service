FROM amazoncorretto:21.0.4-alpine
LABEL maintainer="p.marko09@yahoo.com"
COPY target/order-service-0.0.1-SNAPSHOT.jar /app/order-service.jar
EXPOSE 8092
ENTRYPOINT ["java", "-jar", "/app/order-service.jar"]