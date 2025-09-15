# 베이스 이미지 설정
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY build/libs/Koas-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","Koas-0.0.1-SNAPSHOT.jar"]
