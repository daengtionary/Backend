FROM openjdk:11
WORKDIR /build/libs
COPY app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]