# Traderie Clone

## API
[Postman documentation](https://documenter.getpostman.com/view/21886355/2sA3BoaWyf#fdff4ac0-dfb2-4738-8fd3-549ba8001736)

## Set up
### Cassandra
1. Download and run docker image for cassandra `docker pull cassandra:latest`
1. Run the image
1. Access the terminal from docker and run `cqlsh` to open cassandra shell
1. Create keyspace `CREATE KEYSPACE traderie_cassandra WITH replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};`
