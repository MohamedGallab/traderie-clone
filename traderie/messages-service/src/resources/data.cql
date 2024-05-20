-- Create a keyspace
CREATE KEYSPACE traderie_cassandra WITH replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};

-- Create a table
CREATE TYPE offered_product (
quantity int,
game_id int,
product_id int,
product_name text,
product_icon text
);
CREATE TABLE offers (
offer_id uuid PRIMARY KEY,
listing_id uuid,
buyer_id uuid,
seller_id uuid,
timestamp timestamp,
status string,
offered_products set<frozen<set<frozen<offered_product>>>>
);

docker run --rm -v "$(pwd)/data.cql:/scripts/data.cql" -e CQLSH_HOST=cassandra -e CQLSH_PORT=9042 -e CQLVERSION=3.4.6 nuvo/docker-cqlsh
