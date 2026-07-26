FROM eclipse-temurin:17-alpine

RUN addgroup -g 1028 devopsc \
    && adduser -D -G devopsc admin

VOLUME /tmp

COPY app.jar /app/app.jar

RUN chown -R admin:devopsc /app

USER admin

ENTRYPOINT ["java","-jar","/app/app.jar"]