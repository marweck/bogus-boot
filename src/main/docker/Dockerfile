FROM lwieske/java-8:server-jre-8u72-slim
VOLUME /tmp
ADD bogus-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]