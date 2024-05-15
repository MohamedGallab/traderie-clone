-- Create a keyspace
CREATE KEYSPACE traderie_cassandra WITH replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};
