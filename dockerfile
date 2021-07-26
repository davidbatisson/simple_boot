FROM openjdk:8-jre

COPY target/*.jar myapp.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/.urandom","-jar","/myapp.jar"]