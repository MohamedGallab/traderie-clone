CREATE KEYSPACE offers 

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