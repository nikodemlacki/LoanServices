FROM anapsix/alpine-java:8

MAINTAINER test/landbay
LABEL PROJECT_NAME=codingExcercise \
      SERVICE=loan-service

COPY target/loan-service.jar /opt/
RUN mv /opt/loan-service.jar /opt/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/opt/app.jar"]