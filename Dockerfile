
FROM eclipse-temurin:17-jre-jammy
ENV TZ=Asia/Seoul \
    JAVA_OPTS="-Duser.timezone=Asia/Seoul -Xms256m -Xmx512m"
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]