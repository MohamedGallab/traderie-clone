# Traderie Clone

## API
[Postman documentation](https://documenter.getpostman.com/view/21886355/2sA3BoaWyf#fdff4ac0-dfb2-4738-8fd3-549ba8001736)

## Set up
### Cassandra
1. `docker run -d --name cassandra -p 9042:9042 cassandra:latest`

### RabbitMQ
1. `docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.13-management`

### Media Server
1. `docker run -d --name couchdb -p 5984:5984 -e COUCHDB_USER=admin -e COUCHDB_PASSWORD=password couchdb:latest`
2. install xfs? `apt install xfsprogs`

### Reddis
1. `docker run -d --name redis-stack -p 6379:6379 -p 8001:8001 redis/redis-stack:latest`

### Minikube
1. install [minikube](https://minikube.sigs.k8s.io/docs/start/) (Use the docker driver `minikube start --driver=docker --cpus 4 --memory 8192`)
2. `minikube -p minikube docker-env --shell powershell | Invoke-Expression`
3. run deploy.bat
4. `minikube addons enable metrics-server`
