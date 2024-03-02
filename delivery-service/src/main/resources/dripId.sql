/*CREATE TABLE IF NOT EXISTS "warehouses"(
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

CREATE TABLE IF NOT EXISTS "chats"(
    "id" BIGSERIAL PRIMARY KEY,
    "last_message" TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS "message_types"(
    "id" BIGSERIAL PRIMARY KEY,
    "type" VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "users"(
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

CREATE TABLE IF NOT EXISTS "save_addresses"(
    "id" BIGSERIAL PRIMARY KEY,
    "country" VARCHAR(255) NOT NULL,
    "region" VARCHAR(255) NOT NULL,
    "city" VARCHAR(255) NOT NULL,
    "address" VARCHAR(255) NOT NULL,
    "user" BIGINT DEFAULT NULL,
    FOREIGN KEY("user_id") REFERENCES "users"("id")
);

CREATE TABLE IF NOT EXISTS "balance_history"(
    "id" BIGSERIAL PRIMARY KEY,
    "transaction_type" VARCHAR(255) NOT NULL,
    "check" VARCHAR(255) NOT NULL,
    "balance_old" DECIMAL(8, 2) NOT NULL,
    "balance_new" DECIMAL(8, 2) NOT NULL,
    "admin_id" BIGINT NOT NULL,
    "user_id" BIGINT NOT NULL,
    FOREIGN KEY("admin_id") REFERENCES "users"("id"),
    FOREIGN KEY("user_id") REFERENCES "users"("id")
);

CREATE TABLE IF NOT EXISTS "orders"(
    "id" BIGSERIAL PRIMARY KEY,
    "order_type" VARCHAR(255) NOT NULL,
    "order_status" VARCHAR(255) NOT NULL,
    "user_id" BIGINT NOT NULL,
    "track_number_external" VARCHAR(255) NOT NULL,
    "track_number_internal" VARCHAR(255) NOT NULL,
    "address" VARCHAR(255) NOT NULL,
    "warehouse_id" BIGINT NOT NULL,
    "delivery_stage" VARCHAR(255) DEFAULT NULL,
    FOREIGN KEY("user_id") REFERENCES "users"("id"),
    FOREIGN KEY("warehouse_id") REFERENCES "warehouses"("id")
);

CREATE TABLE IF NOT EXISTS "products"(
    "id" BIGSERIAL PRIMARY KEY,
    "url" VARCHAR(255) DEFAULT NULL,
    "description" VARCHAR(255) NOT NULL,
    "price" DECIMAL(8, 2) NOT NULL,
    "weight" DECIMAL(8, 2) NOT NULL,
    "order" BIGINT NOT NULL,
    FOREIGN KEY("order_id") REFERENCES "orders"("id")
    );

CREATE TABLE IF NOT EXISTS "default_messages"(
    "id" BIGSERIAL PRIMARY KEY,
    "message_type_id" BIGINT NOT NULL,
    "message" VARCHAR(255) NOT NULL,
    FOREIGN KEY("message_type_id") REFERENCES "message_types"("id")
);

CREATE TABLE IF NOT EXISTS "messages"(
    "id" BIGSERIAL PRIMARY KEY,
    "chat_id" BIGINT NOT NULL,
    "user_id" BIGINT NOT NULL,
    "admin_id" BIGINT NOT NULL,
    "content" VARCHAR(255) NOT NULL,
    "created_at" TIMESTAMP NOT NULL,
    "status" VARCHAR(255) NOT NULL,
    FOREIGN KEY("chat_id") REFERENCES "chats"("id"),
    FOREIGN KEY("user_id") REFERENCES "users"("id"),
    FOREIGN KEY("admin_id") REFERENCES "users"("id")
);
*/