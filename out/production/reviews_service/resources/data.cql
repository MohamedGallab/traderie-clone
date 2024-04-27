CREATE KEYSPACE traderie_cassandra WITH replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};

------
CREATE TABLE reviews (
                         offer_id uuid,
                         sender_id uuid,
                         receiver_id uuid,
                         time_stamp timestamp,
                         rating int ,
                         comment text,
                         reply text,
                         PRIMARY KEY (offer_id, sender_id, receiver_id)
);