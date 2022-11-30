FROM maven:3-jdk-11 AS build-env
WORKDIR /opt

# Copy everything
COPY . /opt

# Just echo so we can see, if everything is there :)
RUN ls -l

# Run Maven build
RUN mvn clean install

# Build runtime image
FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /opt
COPY --from=build-env /opt/target/todoapi-0.0.1-SNAPSHOT.jar /opt/app.jar

# Expose web api port number
EXPOSE 8080

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]