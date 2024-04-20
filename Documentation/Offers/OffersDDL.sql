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

CREATE TABLE offers_by_listing (
    offer_id uuid,
    listing_id uuid,
    buyer_id uuid,
    seller_id uuid,
    timestamp timestamp,
    status string,
    offered_products set<frozen<set<frozen<offered_product>>>>
    PRIMARY KEY((listing_id),offer_id);
);

CREATE TABLE offers_by_buyer (
    offer_id uuid,
    listing_id uuid,
    buyer_id uuid,
    seller_id uuid,
    timestamp timestamp,
    status string,
    offered_products set<frozen<set<frozen<offered_product>>>>
    PRIMARY KEY((buyer_id),offer_id);
);

CREATE TABLE offers_by_seller (
    offer_id uuid,
    listing_id uuid,
    buyer_id uuid,
    seller_id uuid,
    timestamp timestamp,
    status string,
    offered_products set<frozen<set<frozen<offered_product>>>>
    PRIMARY KEY((seller_id),offer_id);
);

CREATE TABLE offers_by_seller_buyer (
    offer_id uuid,
    listing_id uuid,
    buyer_id uuid,
    seller_id uuid,
    timestamp timestamp,
    status string,
    offered_products set<frozen<set<frozen<offered_product>>>>
    PRIMARY KEY((seller_id,buyer_id),offer_id);
);