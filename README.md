# Simple http server to generate fibonacci series

## Build application
```bash
./gradlew build
````
## Test application
```bash
./gradlew test
````

## Docker commands to build image and run it
```bash
docker build -t fibonacci-server-1.0.0 .

docker run -p 9000:9000 fibonacci-server-1.0.0
````
## Deploy image in the local cluster
```bash
kubectl apply -f deployment.yaml

kubectl apply -f service.yaml 
```
## Remove the service from the cluster

```bash

kubectl delete deployment fibonacci-server

kubectl delete service fibonacci-server
```



## Run a local registry for the images
```bash
docker run -d -p 5000:5000 --name registry registry:2

````
## Tag the image for the local registry
```bash
docker tag fibonacci-server-1.0.0:latest localhost:5000/fibonacci-server-1.0.0:latest
````

## Push the image to the local registry

```bash
docker push localhost:5000/fibonacci-server-1.0.0:latest
```

## Create registry secret

```bash
kubectl create secret docker-registry my-registry-secret \
--docker-server=localhost:5000 \
--docker-username=dibek \
--docker-password=dibek \
--docker-email=dibek71@gmail.com
```

## Run a tunnel for the load balancer locally with minikube

```bash
minikube tunnel
```

## Connect to the service in the cluster
```bash
curl -i -X GET <external-balancer-ip>:9001/fibonacci\?maxIterations=4

```



