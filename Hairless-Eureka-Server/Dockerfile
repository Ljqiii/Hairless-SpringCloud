FROM java:8-jre

ADD ./target/hairless-eureka-server-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/hairless-eureka-server-0.0.1-SNAPSHOT.jar"]

EXPOSE 8761