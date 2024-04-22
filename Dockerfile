FROM openjdk:11-jre-slim

WORKDIR /app

COPY FibonacciServer.java /app

RUN javac FibonacciServer.java

CMD ["java", "FibonacciServer"]
