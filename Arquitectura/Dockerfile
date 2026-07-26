FROM eclipse-temurin:17-alpine

RUN addgroup -g 1028 devopsc \
    && adduser -D -G devopsc admin

VOLUME /tmp

ARG JAR_FILE

COPY ${JAR_FILE} /app/app.jar

RUN chown -R admin:devopsc /app

USER admin

ENTRYPOINT ["java","-jar","/app/app.jar"]