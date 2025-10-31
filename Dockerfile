# 런타임 전용 (경량 JRE)
FROM eclipse-temurin:17-jre-jammy

ENV TZ=Asia/Seoul \
    JAVA_OPTS="-Duser.timezone=Asia/Seoul -Xms256m -Xmx512m"

WORKDIR /app

# Gradle로 만든 JAR 복사 (가장 최근 JAR 하나만 카피되도록 *.jar)
COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]