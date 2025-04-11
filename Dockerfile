FROM openjdk:17
COPY build/libs/ingressJobs-0.0.1-SNAPSHOT.jar /app/
WORKDIR /app/
ENTRYPOINT ["java"]
CMD ["-jar", "/app/ingressJobs-0.0.1-SNAPSHOT.jar"]
