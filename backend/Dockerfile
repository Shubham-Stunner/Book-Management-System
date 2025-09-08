FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/backend-*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
