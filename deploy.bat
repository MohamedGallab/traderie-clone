cd traderie

call mvn package -f pom.xml

kubectl delete deployment offers-service
kubectl delete deployment cassandra
kubectl delete deployment redis
kubectl delete deployment rabbit-mq
kubectl delete deployment web-server

kubectl delete service offers-service-service 
kubectl delete service cassandra-service 
kubectl delete service redis-service 
kubectl delete service rabbit-mq-service 
kubectl delete service web-server-service

docker builder prune --all -f

docker build -f offers-service\Dockerfile -t offers-service:latest . 

kubectl apply -f offers-service\kubernetes\offers-service.yaml
kubectl apply -f shared\kubernetes\cassandra.yaml
kubectl apply -f shared\kubernetes\redis.yaml
kubectl apply -f shared\kubernetes\rabbit-mq.yaml
kubectl apply -f web-server\kubernetes\web-server.yaml
