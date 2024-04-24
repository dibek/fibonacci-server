# Simple http server to generate fibonacci series

## Build application
```bash
./gradlew build
````
## Test application
```bash
./gradlew test
````

## Integration test

To run the integration test we need to set the variable 
FIBONACCI_SERVER_IP
Example using the cluster:
```bash
export FIBONACCI_SERVER_IP=$(kubectl get svc fibonacci-server | awk 'NR==2 {print $4}')
```


## Docker commands to build image and run it
```bash
docker build -t fibonacci-server .

docker run -p 9000:9000 fibonacci-server:latest
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
`docker run -d -p 5000:5000 --name registry registry:2`

````
## Tag the image for the local registry
```bash
`docker tag fibonacci-server:latest registry.dev.svc.cluster.local:5000/fibonacci-server:latest`
````

## Push the image to the local registry

```bash
docker push registry.dev.svc.cluster.local:5000/fibonacci-server:latest
```

## Create registry secret

```bash
kubectl create secret docker-registry my-registry-secret \
--docker-server=localhost:5000 \
--docker-username=dibek \
--docker-password=dibek \
--docker-email=dibek71@gmail.com
```

## Start minikube 

```bash
minikube start --cpus 4 --memory 4096 --insecure-registry registry.dev.svc.cluster.local:5000
```

### Setting up env dev
```bash
kubectl create namespace dev
```

### Create a service to reach registry inside minikube

```bash
cat <<EOF | kubectl apply -n dev -f -
---
kind: Service
apiVersion: v1
metadata:
  name: registry
spec:
  ports:
  - protocol: TCP
    port: 5000
    targetPort: 5000
---
kind: Endpoints
apiVersion: v1
metadata:
  name: registry
subsets:
  - addresses:
      - ip: $DEV_IP
    ports:
      - port: 5000
EOF
```




### Switch to dev environment and setting as default

```bash
 kubectl config set-context --current --namespace=dev  
```

## Run a tunnel for the load balancer locally with minikube

```bash
minikube tunnel
```

## Connect to the service in the cluster
```bash
curl -i -X GET $(kubectl get svc fibonacci-server | awk 'NR==2 {print $4}'):9001/fibonacci\?n=4

```

## Add registry reachable from the cluster 

See : https://gist.github.com/trisberg/37c97b6cc53def9a3e38be6143786589


## Find the ip address to try the load balancer 

First we need to find the ip address with this command
```bash
kubectl get svc fibonacci-server
```

Then we can use curl to test it (we need awk installed if we want to use the included script)

```bash
curl $(kubectl get svc fibonacci-server | awk 'NR==2 {print $4}'):9001/fibonacci
```