CREATE TABLE products_by_game(
  game_id uuid,
  product_id uuid,
  product_name text,
  product_icon text,
  PRIMARY KEY ((game_id), product_name, product_id))
  WITH CLUSTERING ORDER BY (product_name ASC);