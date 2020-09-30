FROM maven:3-openjdk-11 as build

LABEL maintainer="Zsolt Toth"

WORKDIR /tmp
COPY pom.xml .
ADD src src
RUN mvn package spring-boot:repackage

FROM openjdk:11.0-jre-slim

EXPOSE 8080
WORKDIR /usr/src/myapp
COPY --from=build /tmp/target/ilona-measurement.jar .
COPY src/main/resources/mybatis-configuration.xml .
CMD java -jar ilona-measurement.jar