# Traderie Clone
a web-based application designed to connect people looking to trade in-game items. Built using a microservices architecture with Spring, this project ensures scalability and modularity, making it easy to manage and extend.
## API
[Postman documentation](https://documenter.getpostman.com/view/21886355/2sA3BoaWyf#fdff4ac0-dfb2-4738-8fd3-549ba8001736)

## Set up
### Cassandra
1. `docker run -d --name cassandra -p 9042:9042 cassandra:latest`

### RabbitMQ
1. `docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.13-management`

### Reddis
1. `docker run -d --name redis-stack -p 6379:6379 -p 8001:8001 redis/redis-stack:latest`

### Minikube
1. install [minikube](https://minikube.sigs.k8s.io/docs/start/)
2. Start minikube `minikube start --driver=docker --cpus 8 --memory 15920`
3. `minikube -p minikube docker-env --shell powershell | Invoke-Expression`
4. run deploy.bat
5. to access through postman `minikube tunnel`
6. generate load on web server `kubectl run -i --tty load-generator --rm --image=busybox --restart=Never -- /bin/sh -c "while sleep 0.01; do wget -q -O- http://web-server-service:8080/api/v1/offers; done"`


## Benchmarks

![image](https://github.com/MohamedGallab/traderie-clone/assets/74183135/f7910967-0712-406f-bd60-b3dca98db546)
![image](https://github.com/MohamedGallab/traderie-clone/assets/74183135/ee37cdd1-6bc2-4af8-a7bf-61c2ef749f4f)
