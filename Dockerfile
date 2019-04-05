FROM adoptopenjdk/openjdk12:alpine AS builder

RUN jlink \
    --add-modules java.desktop,jdk.unsupported,java.sql,java.naming,java.management,java.instrument,java.security.jgss \
    --verbose \
    --strip-debug \
    --compress 2 \
    --no-header-files \
    --no-man-pages \
    --output /opt/jre

FROM panga/alpine:3.7-glibc2.25 AS runner

COPY --from=builder /opt/jre /opt/jre

ENV LANG=C.UTF-8 \
    PATH=$PATH:/opt/jre/bin \
    ENV_MONGO_HOST=mongo

VOLUME /tmp

ARG JAR_FILE
ADD target/${JAR_FILE} /opt/app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]