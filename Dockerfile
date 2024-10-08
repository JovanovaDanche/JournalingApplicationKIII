FROM amazoncorretto:21-alpine
WORKDIR /app
EXPOSE 8080
COPY . .
RUN chmod +x mvnw && ./mvnw dependency:resolve
CMD ["./mvnw", "spring-boot:run"]