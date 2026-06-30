# Docker image build
FROM eclipse-temurin:17-alpine
RUN addgroup -g 1028 devopsc \
    && adduser -D -G devopsc admin
VOLUME /tmp
ARG JAR_FILE
ARG DB_PASSWORD
COPY ${JAR_FILE} /app/app.jar
ENV DB_PASSWORD=$DB_PASSWORD
RUN chown -R admin:devopsc /app
USER admin
ENTRYPOINT ["java","-jar","/app/app.jar"]