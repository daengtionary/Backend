FROM openjdk:11
WORKDIR /build/libs
COPY app.jar /build/libs
EXPOSE 80
CMD java -jar /build/libs/app.jar