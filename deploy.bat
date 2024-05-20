cd traderie

call mvn package -f pom.xml

kubectl delete --all deployments

kubectl delete service cassandra-service 
kubectl delete service redis-service 
kubectl delete service rabbit-mq-service 
kubectl delete service offers-service-service 
kubectl delete service messages-service-service 
kubectl delete service listings-service-service 
kubectl delete service web-server-service
kubectl delete service admin-service
kubectl delete service users-service-service
kubectl delete service reviews-service-service

docker builder prune --all -f

docker build -f messages-service\Dockerfile -t messages-service:latest .
docker build -f listings-service\Dockerfile -t listings-service:latest .
docker build -f offers-service\Dockerfile -t offers-service:latest . 
docker build -f admin-service\Dockerfile -t admin-service:latest .
docker build -f reviews-service\Dockerfile -t reviews-service:latest .
docker build -f users-service\Dockerfile -t users-service:latest .
docker build -f web-server\Dockerfile -t web-server:latest .
kubectl apply -f ./kubernetes -R
