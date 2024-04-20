USE "Traderie";

CREATE TABLE listings_by_product( 
  product_id uuid,  
  listing_type boolean,
  listing_id uuid,
  time_stamp timestamp,
  product_name text,
  product_icon text,
  quantity int,
  user_id uuid,
  desired_offer set< frozen < set < frozen < product_amount> > > >,
  PRIMARY KEY ((product_id,listing_type), time_stamp,listing_id))
  WITH CLUSTERING ORDER BY (time_stamp ASC);

CREATE TABLE listings_by_user_by_game(
  product_id uuid,  
  game_id uuid,
  listing_type boolean,
  time_stamp timestamp,
  listing_id uuid,
  product_name text,
  product_icon text,
  quantity int,
  user_id uuid,
  desired_offer set< frozen < set < frozen < product_amount> > > >,
  PRIMARY KEY ((user_id,game_id,listing_type), time_stamp,listing_id))
  WITH CLUSTERING ORDER BY (time_stamp ASC);