# Build application

./gradlew build


# Docker commands to build image and run it

docker build -t fibonacci-server-1.0.0 .

docker run -p 8080:8080 fibonacci-server-1.0.0