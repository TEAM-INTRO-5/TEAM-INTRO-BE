FROM adoptopenjdk/openjdk11

COPY ./build/libs/zillinks-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]