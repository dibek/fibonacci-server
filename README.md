# Simple http server to generate fibonacci series

## Build application

./gradlew build

## Test application

./gradlew test


## Docker commands to build image and run it

docker build -t fibonacci-server-1.0.0 .

docker run -p 9000:9000 fibonacci-server-1.0.0

## Deploy image in the local cluster

kubectl apply -f deployment.yaml

kubectl apply -f service.yaml 

## Remove the service from the cluster

kubectl delete service fibonacci-server


## Connect to the service in the cluster

curl -i -X GET localhost:9001/fibonacci\?maxIterations=4

