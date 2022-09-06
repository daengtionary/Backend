FROM openjdk:11
WORKDIR /build/libs
COPY . .
EXPOSE 80
ENTRYPOINT ["java","-jar","/app.jar"]