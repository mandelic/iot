DROP TABLE IF EXISTS floorplan;

CREATE TABLE floorplan (
   id SERIAL PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   type VARCHAR(255) NOT NULL,
   data BYTEA NOT NULL,
   asset_id UUID NOT NULL
);