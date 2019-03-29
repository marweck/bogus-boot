FROM adoptopenjdk/openjdk12:alpine-jre

ENV ENV_MONGO_HOST=mongo

ARG JAR_FILE
ADD target/${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]