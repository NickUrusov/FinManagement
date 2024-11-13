FROM openjdk:17-jdk-alpine
COPY target/FinancicalManagement-1.0-SNAPSHOT.jar Docker-FinancicalManagement-1.0.jar
ENTRYPOINT ["java","-jar","/Docker-FinancicalManagement-1.0.jar"]