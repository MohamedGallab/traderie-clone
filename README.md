# Traderie Clone

## API
[Postman documentation](https://documenter.getpostman.com/view/21886355/2sA3BoaWyf#fdff4ac0-dfb2-4738-8fd3-549ba8001736)

## Set up
### Cassandra
1. Download and run docker image for cassandra `docker pull cassandra:latest`
1. Run the image
1. Access the terminal from docker and run `cqlsh` to open cassandra shell
1. Create keyspace `CREATE KEYSPACE traderie_cassandra WITH replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};`

### RabbitMQ
1. use docker `docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.13-management`

### Media Server
1. pull CouchDB `docker pull couchdb`
2. install xfs? `apt install xfsprogs`

### Reddis
1. `docker run -d --name redis-stack-server -p 6379:6379 redis/redis-stack-server:latest`
