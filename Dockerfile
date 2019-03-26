FROM adoptopenjdk/openjdk11:alpine-jre

VOLUME /tmp

ENV ENV_MONGO_HOST=mongo

ARG JAR_FILE
ADD target/${JAR_FILE} app.jar

ENTRYPOINT ["./app.jar"]
