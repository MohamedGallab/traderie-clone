kubectl delete deployment offers-service
docker builder prune --all && cd ./traderie && mvn package && cd offers-service && docker build -t offers-service:latest . && kubectl apply -f .\kubernetes\offers-service.yaml
