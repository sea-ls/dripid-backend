--liquibase formatted sql

--changeset sea-ls:create_function
CREATE OR REPLACE FUNCTION generate_random_unique_value() RETURNS VARCHAR AS '
    DECLARE
        random_number_first VARCHAR;
        random_number_second VARCHAR;
        new_value VARCHAR;
    BEGIN
        LOOP
            random_number_first := lpad(CAST(FLOOR(random() * 10000) AS VARCHAR), 4, ''0'');
            random_number_second := lpad(CAST(FLOOR(random() * 10000) AS VARCHAR), 4, ''0'');
            new_value := ''D'' || random_number_first || ''l'' || random_number_second;
            EXIT WHEN NOT EXISTS (SELECT 1 FROM orders WHERE track_number_internal = new_value);
        END LOOP;
        RETURN new_value;
    END;
' LANGUAGE plpgsql;
--rollback DROP FUNCTION generate_random_unique_value();

--changeset sea-ls:id1
CREATE TABLE IF NOT EXISTS "warehouse"(
    "id" BIGSERIAL PRIMARY KEY,
    "code" VARCHAR(255) NOT NULL,
    "zip_code" VARCHAR(255) NOT NULL,
    "address" VARCHAR(255) NOT NULL,
    "city" VARCHAR(255) NOT NULL,
    "region" VARCHAR(255) NOT NULL,
    "country" VARCHAR(255) NOT NULL,
    "phone_number" VARCHAR(255) NOT NULL,
    "email" VARCHAR(255),
    "work_schedule" VARCHAR(255) NOT NULL,
    "image" VARCHAR(255)
);
--rollback drop table warehouse;

--changeset sea-ls:id2
CREATE TABLE IF NOT EXISTS "message_type"(
    "id" BIGSERIAL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL
);
--rollback drop table message_type;

--changeset sea-ls:id3
CREATE TABLE IF NOT EXISTS "person"(
    "id" BIGSERIAL PRIMARY KEY,
    "keycloak_id" VARCHAR(255) NOT NULL,
    "balance" DECIMAL(8, 2) NOT NULL DEFAULT 0.00,
    "image" VARCHAR(255) DEFAULT NULL
);
--rollback drop table person;

--changeset sea-ls:id4
CREATE TABLE IF NOT EXISTS "save_address"(
    "id" BIGSERIAL PRIMARY KEY,
    "country" VARCHAR(255) NOT NULL,
    "region" VARCHAR(255) NOT NULL,
    "city" VARCHAR(255) NOT NULL,
    "address" VARCHAR(255) NOT NULL,
    "person_id" BIGINT NOT NULL,
    CONSTRAINT FK_person_id FOREIGN KEY (person_id) REFERENCES person(id)
);
--rollback drop table save_address;

--changeset sea-ls:id5
CREATE TABLE IF NOT EXISTS "balance_history"(
    "id" BIGSERIAL PRIMARY KEY,
    "transaction_type" VARCHAR(255) NOT NULL,
    "cheque" VARCHAR(255) NOT NULL,
    "old_balance" DECIMAL(8, 2) NOT NULL,
    "new_balance" DECIMAL(8, 2) NOT NULL,
    "admin_id" BIGINT NOT NULL,
    "user_id" BIGINT NOT NULL,
    CONSTRAINT FK_admin_id_balance_history FOREIGN KEY (admin_id) REFERENCES person(id),
    CONSTRAINT FK_user_id_balance_history FOREIGN KEY (user_id) REFERENCES person(id)
);
--rollback drop table balance_history;

--changeset sea-ls:id6
CREATE TABLE IF NOT EXISTS "orders"(
    "id" BIGSERIAL PRIMARY KEY,
    "order_type" VARCHAR(255) NOT NULL,
    "order_status" VARCHAR(255) NOT NULL,
    "last_update" TIMESTAMP NOT NULL,
    "person_id" BIGINT NOT NULL,
    "track_number_external" VARCHAR(255),
    "track_number_internal" VARCHAR(255) DEFAULT generate_random_unique_value(),
    "address" VARCHAR(255) NOT NULL,
    "warehouse_id" BIGINT NOT NULL,
    "delivery_stage_type" VARCHAR(255) DEFAULT NULL,
    "delivery_history" jsonb DEFAULT null,
    CONSTRAINT FK_person_id_orders FOREIGN KEY (person_id) REFERENCES person(id),
    CONSTRAINT FK_warehouse_id_orders FOREIGN KEY (warehouse_id) REFERENCES warehouse(id)
);
--rollback drop table orders;

--changeset sea-ls:id7
CREATE TABLE IF NOT EXISTS "product"(
    "id" BIGSERIAL PRIMARY KEY,
    "url" VARCHAR(255) DEFAULT NULL,
    "description" VARCHAR(255) NOT NULL,
    "price" DECIMAL(8, 2) NOT NULL,
    "weight" DECIMAL(8, 2) NOT NULL,
    "order_id" BIGINT NOT NULL,
    CONSTRAINT FK_order_id_product FOREIGN KEY (order_id) REFERENCES orders(id)
);
--rollback drop table product;

--changeset sea-ls:id8
CREATE TABLE IF NOT EXISTS "default_message"(
    "id" BIGSERIAL PRIMARY KEY,
    "message_type_id" BIGINT NOT NULL,
    "message" VARCHAR(255) NOT NULL,
    CONSTRAINT FK_message_type_id_default_message FOREIGN KEY (message_type_id) REFERENCES message_type(id)
);
--rollback drop table default_message;

--changeset sea-ls:id9
CREATE TABLE IF NOT EXISTS "message"(
    "id" BIGSERIAL PRIMARY KEY,
    "sender_id" BIGINT NOT NULL,
    "order_id" BIGINT NOT NULL,
    "content" VARCHAR(255) NOT NULL,
    "created_at" TIMESTAMP NOT NULL,
    "status" VARCHAR(255) NOT NULL,
    "event_type" VARCHAR(255) NOT NULL,
    CONSTRAINT FK_sender_id_message FOREIGN KEY (sender_id) REFERENCES person(id),
    CONSTRAINT FK_order_id_message FOREIGN KEY (order_id) REFERENCES orders(id)
);
--rollback drop table message;

--changeset sea-ls:id10
CREATE TABLE IF NOT EXISTS "currency"(
    "id" BIGSERIAL PRIMARY KEY,
    "updated_timestamp" TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT statement_timestamp(),
    "currency_name" VARCHAR(255) NOT NULL,
    "quickly_purchase" DECIMAL(8, 2) NOT NULL,
    "non_quickly_purchase" DECIMAL(8, 2) NOT NULL,
    "crypto" DECIMAL(8, 2) NOT NULL
);
--rollback drop table currency;

ALTER TABLE balance_history
    DROP CONSTRAINT IF EXISTS CHECK_transaction_type,
    ADD CONSTRAINT CHECK_transaction_type
        CHECK (transaction_type IN ('DEPOSIT', 'WITHDRAW'));

ALTER TABLE orders
    DROP CONSTRAINT IF EXISTS CHECK_order_type,
    ADD CONSTRAINT CHECK_order_type
        CHECK (order_type IN ('TEST'));

ALTER TABLE orders
    DROP CONSTRAINT IF EXISTS CHECK_order_status,
    ADD CONSTRAINT CHECK_order_status
        CHECK (order_status IN ('TEST', 'UPD_TEST'));

ALTER TABLE orders
    DROP CONSTRAINT IF EXISTS CHECK_delivery_stage_type,
    ADD CONSTRAINT CHECK_delivery_stage_type
        CHECK (delivery_stage_type IN ('TEST'));

ALTER TABLE message
    DROP CONSTRAINT IF EXISTS CHECK_event_type,
    ADD CONSTRAINT CHECK_event_type
        CHECK (event_type IN ('CHAT', 'JOIN', 'LEAVE'));

ALTER TABLE message
    DROP CONSTRAINT IF EXISTS CHECK_status,
    ADD CONSTRAINT CHECK_status
        CHECK (status IN ('RECEIVED', 'DELIVERED'));