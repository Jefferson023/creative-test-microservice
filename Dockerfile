FROM openjdk:11
EXPOSE 8080
RUN mkdir /app
COPY build/libs/*.jar /app/creative-1.0.jar
CMD ["java","-jar","/app/creative-1.0.jar"]