# Build stage
FROM maven:3.6.0-jdk-11-slim AS build
COPY . /home/app/
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip


FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/car-insurance-api-0.0.1-SNAPSHOT.jar /usr/local/lib/car-insurance-api.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/usr/local/lib/car-insurance-api.jar"]