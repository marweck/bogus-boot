FROM openjdk:8-jre-alpine

VOLUME /tmp

ENV ENV_MONGO_HOST=mongo

ADD bogus.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]