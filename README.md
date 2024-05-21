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
1. install [minikube](https://minikube.sigs.k8s.io/docs/start/)
2. Start minikube `minikube start --driver=docker --cpus 8 --memory 15920`
3. `minikube -p minikube docker-env --shell powershell | Invoke-Expression`
4. run deploy.bat
6. generate load on web server `kubectl run -i --tty load-generator --rm --image=busybox --restart=Never -- /bin/sh -c "while sleep 0.01; do wget -q -O- http://web-server-service:8080/api/v1/offers; done"`
