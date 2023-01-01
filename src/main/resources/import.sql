INSERT INTO states (id, name, created_at, updated_at)
VALUES
  ('e8efeec1-fada-40eb-8683-3237c7052666', 'Rio Grande do Sul', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('bbff8164-ec0a-4733-b4bb-d813f200de9e', 'Davao', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO cities (id, name, state_id, created_at, updated_at)
VALUES
  ('203bae02-7f64-4633-a0a0-db7917a34223', 'Novo Hamburgo', 'e8efeec1-fada-40eb-8683-3237c7052666', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('0fee15f4-cd92-4ac1-b7df-78799ef29194', 'Davao City', 'bbff8164-ec0a-4733-b4bb-d813f200de9e', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO payment_methods (id, name, created_at, updated_at)
VALUES 
  ('56eb39eb-bdcf-4ef9-ab01-c4ebf01648d3', 'Credit card', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('9b01ad85-2cb4-404b-99eb-b95a9d0315d0', 'Debit card', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('8843fc52-9126-40fc-85f1-d4271b4dc9d1', 'Cash', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('1baf736c-481b-4a0c-9dd8-a1a40c0ea963', 'PIX', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('f0afd993-cc0d-4987-ac77-cd2b511c74aa', 'GCash', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('310416ca-af89-49dd-908f-25884c9c7308', 'PayMaya', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO cuisines (id, name, created_at, updated_at)
VALUES 
  ('95cf683a-b0c9-4f74-a34b-ba2f78964223', 'Brazilian', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('6749d70f-8b42-471e-9e4b-ccc9170e14de', 'Filipino', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO restaurants (id, name, shipping_rate, is_active, is_open, zip_code, street_name, street_number, apartment_number, neighborhood, city_id, cuisine_id, created_at, updated_at)
VALUES
  ('1d9a3967-db13-46a7-b2c1-416b1d2f5018', 'Churrascão do Zé', 15.00, false, false, '93410090', 'Voluntários da Pátria', '795', '801', 'Pátria Nova', '203bae02-7f64-4633-a0a0-db7917a34223', '95cf683a-b0c9-4f74-a34b-ba2f78964223', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('5dab425f-595b-49e8-8ab7-0877c45de1cf', 'Manok ni Kuya', 5.00, false, false, '8000', 'Biao Escuela', null, null, 'Tugbok, Davao', '0fee15f4-cd92-4ac1-b7df-78799ef29194', '6749d70f-8b42-471e-9e4b-ccc9170e14de', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('0d14bf21-13c8-411e-9449-c2124b8ae415', 'Say-Say', 0, false, false, '8000', 'Biao Escuela', null, null, 'Tugbok, Davao', '0fee15f4-cd92-4ac1-b7df-78799ef29194', '6749d70f-8b42-471e-9e4b-ccc9170e14de', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO restaurants_payment_methods (restaurant_id, payment_method_id)
VALUES
  ('1d9a3967-db13-46a7-b2c1-416b1d2f5018', '56eb39eb-bdcf-4ef9-ab01-c4ebf01648d3'),
  ('1d9a3967-db13-46a7-b2c1-416b1d2f5018', '9b01ad85-2cb4-404b-99eb-b95a9d0315d0'),
  ('1d9a3967-db13-46a7-b2c1-416b1d2f5018', '8843fc52-9126-40fc-85f1-d4271b4dc9d1'),
  ('1d9a3967-db13-46a7-b2c1-416b1d2f5018', '1baf736c-481b-4a0c-9dd8-a1a40c0ea963'),
  ('5dab425f-595b-49e8-8ab7-0877c45de1cf', '56eb39eb-bdcf-4ef9-ab01-c4ebf01648d3'),
  ('5dab425f-595b-49e8-8ab7-0877c45de1cf', '8843fc52-9126-40fc-85f1-d4271b4dc9d1'),
  ('5dab425f-595b-49e8-8ab7-0877c45de1cf', 'f0afd993-cc0d-4987-ac77-cd2b511c74aa'),
  ('5dab425f-595b-49e8-8ab7-0877c45de1cf', '310416ca-af89-49dd-908f-25884c9c7308'),
  ('0d14bf21-13c8-411e-9449-c2124b8ae415', '8843fc52-9126-40fc-85f1-d4271b4dc9d1');

INSERT INTO products (id, name, description, price, is_active, restaurant_id, created_at, updated_at)
VALUES
  ('bfee9d41-5ea8-4b93-bb5f-e14a82ccd954', 'Paleta de Porco', null, 150.00, true, '1d9a3967-db13-46a7-b2c1-416b1d2f5018', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('31fd9f47-51ad-4beb-9201-23931f434a75', 'Costela de Boi', null, 150.00, true, '1d9a3967-db13-46a7-b2c1-416b1d2f5018', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('03609762-bd43-4ad8-a03c-623d2697e3c3', 'Lechon Manok', null, 150.00, true, '5dab425f-595b-49e8-8ab7-0877c45de1cf', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('bf33b914-5683-4b6f-aef7-319a4ef5c718', 'Lechon Baboy', null, 150.00, true, '5dab425f-595b-49e8-8ab7-0877c45de1cf', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('ce60cddd-857d-43ba-93f4-e37e3e1e2b32', 'Siopao', null, 150.00, true, '0d14bf21-13c8-411e-9449-c2124b8ae415', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);