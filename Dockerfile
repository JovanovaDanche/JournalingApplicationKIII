FROM amazoncorretto:21-alpine

WORKDIR /app

# Install Maven
RUN apk add --no-cache curl tar && \
    curl -O https://archive.apache.org/dist/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz && \
    tar -xzf apache-maven-3.9.9-bin.tar.gz -C /opt && \
    ln -s /opt/apache-maven-3.9.9/bin/mvn /usr/bin/mvn

COPY . .

RUN chmod +x mvnw && ./mvnw dependency:resolve

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]
