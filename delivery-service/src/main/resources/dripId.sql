CREATE OR REPLACE FUNCTION generate_random_unique_value() RETURNS VARCHAR AS $$
DECLARE
    random_number_first VARCHAR;
    random_number_second VARCHAR;
    new_value VARCHAR;
BEGIN
    LOOP
        random_number_first := lpad(CAST(FLOOR(random() * 10000) AS VARCHAR), 4, '0');
        random_number_second := lpad(CAST(FLOOR(random() * 10000) AS VARCHAR), 4, '0');
        new_value := 'D' || random_number_first || 'l' || random_number_second;
        EXIT WHEN NOT EXISTS (SELECT 1 FROM orders WHERE track_number_internal = new_value);
    END LOOP;
    RETURN new_value;
END;
$$ LANGUAGE plpgsql;

CREATE TABLE IF NOT EXISTS "warehouse"(
    "id" BIGSERIAL PRIMARY KEY,
    "code" VARCHAR(255) NOT NULL,
    "zip_code" VARCHAR(255) NOT NULL,
    "address" VARCHAR(255) NOT NULL,
    "city" VARCHAR(255) NOT NULL,
    "region" VARCHAR(255) NOT NULL,
    "country" VARCHAR(255) NOT NULL,
    "phone_number" VARCHAR(255) NOT NULL,
    "email" VARCHAR(255) NOT NULL,
    "work_schedule" VARCHAR(255) NOT NULL,
    "image" VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "chat"(
    "id" BIGSERIAL PRIMARY KEY,
    "last_message" TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS "message_type"(
    "id" BIGSERIAL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "person"(
    "id" BIGSERIAL PRIMARY KEY,
    "email" VARCHAR(255) NOT NULL,
    "password" VARCHAR(255) NOT NULL,
    "role" VARCHAR(255) NOT NULL,
    "balance" DECIMAL(8, 2) NOT NULL DEFAULT 0.00,
    "image" VARCHAR(255) DEFAULT NULL,
    "first_name" VARCHAR(255) NOT NULL,
    "last_name" VARCHAR(255) NOT NULL,
    "phone" VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "save_address"(
    "id" BIGSERIAL PRIMARY KEY,
    "country" VARCHAR(255) NOT NULL,
    "region" VARCHAR(255) NOT NULL,
    "city" VARCHAR(255) NOT NULL,
    "address" VARCHAR(255) NOT NULL,
    "person_id" BIGINT NOT NULL,
    FOREIGN KEY("person_id") REFERENCES "person"("id")
);

CREATE TABLE IF NOT EXISTS "balance_history"(
    "id" BIGSERIAL PRIMARY KEY,
    "transaction_type" VARCHAR(255) NOT NULL,
    "cheque" VARCHAR(255) NOT NULL,
    "old_balance" DECIMAL(8, 2) NOT NULL,
    "new_balance" DECIMAL(8, 2) NOT NULL,
    "admin_id" BIGINT NOT NULL,
    "person_id" BIGINT NOT NULL,
    FOREIGN KEY("admin_id") REFERENCES "person"("id"),
    FOREIGN KEY("person_id") REFERENCES "person"("id")
);

CREATE TABLE IF NOT EXISTS "orders"(
    "id" BIGSERIAL PRIMARY KEY,
    "type" VARCHAR(255) NOT NULL,
    "order_status" VARCHAR(255) NOT NULL,
    "person_id" BIGINT NOT NULL,
    "track_number_external" VARCHAR(255) NOT NULL,
    "track_number_internal" VARCHAR(255) DEFAULT generate_random_unique_value(),
    "address" VARCHAR(255) NOT NULL,
    "warehouse_id" BIGINT NOT NULL,
    "delivery_stage_type" VARCHAR(255) DEFAULT NULL,
    "delivery_history" jsonb DEFAULT null,
    FOREIGN KEY("person_id") REFERENCES "person"("id"),
    FOREIGN KEY("warehouse_id") REFERENCES "warehouse"("id")
);

CREATE TABLE IF NOT EXISTS "product"(
    "id" BIGSERIAL PRIMARY KEY,
    "url" VARCHAR(255) DEFAULT NULL,
    "description" VARCHAR(255) NOT NULL,
    "price" DECIMAL(8, 2) NOT NULL,
    "weight" DECIMAL(8, 2) NOT NULL,
    "order_id" BIGINT NOT NULL,
    FOREIGN KEY("order_id") REFERENCES "orders"("id")
);

CREATE TABLE IF NOT EXISTS "default_message"(
    "id" BIGSERIAL PRIMARY KEY,
    "message_type_id" BIGINT NOT NULL,
    "message" VARCHAR(255) NOT NULL,
    FOREIGN KEY("message_type_id") REFERENCES "message_type"("id")
);

CREATE TABLE IF NOT EXISTS "message"(
    "id" BIGSERIAL PRIMARY KEY,
    "chat_id" BIGINT NOT NULL,
    "person_id" BIGINT NOT NULL,
    "admin_id" BIGINT NOT NULL,
    "content" VARCHAR(255) NOT NULL,
    "created_at" TIMESTAMP NOT NULL,
    "status" VARCHAR(255) NOT NULL,
    FOREIGN KEY("chat_id") REFERENCES "chat"("id"),
    FOREIGN KEY("person_id") REFERENCES "person"("id"),
    FOREIGN KEY("admin_id") REFERENCES "person"("id")
);