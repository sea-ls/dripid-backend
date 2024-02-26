CREATE TABLE "balance_history"(
    "id" BIGINT NOT NULL,
    "transaction_type" VARCHAR(255) NOT NULL,
    "amout" DECIMAL(8, 2) NOT NULL,
    "cheque" VARCHAR(255) NOT NULL,
    "balance_old" DECIMAL(8, 2) NOT NULL,
    "balance_new" DECIMAL(8, 2) NOT NULL,
    "admin" BIGINT NOT NULL,
    "user" BIGINT NOT NULL
);
ALTER TABLE
    "balance_history" ADD PRIMARY KEY("id");
CREATE TABLE "orders"(
    "id" BIGINT NOT NULL,
    "order_type" VARCHAR(255) NOT NULL,
    "order_status" VARCHAR(255) NOT NULL,
    "product" BIGINT NULL,
    "user" BIGINT NOT NULL,
    "tracking_number_in" VARCHAR(255) NOT NULL,
    "tracking_number_out" VARCHAR(255) NOT NULL,
    "address" VARCHAR(255) NOT NULL,
    "warehouse" BIGINT NOT NULL,
    "delivery_status" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "orders" ADD PRIMARY KEY("id");
CREATE TABLE "product"(
    "id" BIGINT NOT NULL,
    "url" BIGINT NOT NULL,
    "description" BIGINT NOT NULL,
    "price" DECIMAL(8, 2) NOT NULL,
    "weight" DECIMAL(8, 2) NOT NULL
);
ALTER TABLE
    "product" ADD PRIMARY KEY("id");
CREATE TABLE "users"(
    "id" BIGINT NOT NULL,
    "full_name" VARCHAR(255) NOT NULL,
    "phone_number" VARCHAR(255) NOT NULL,
    "password" VARCHAR(255) NOT NULL,
    "country" VARCHAR(255) NOT NULL,
    "city" VARCHAR(255) NOT NULL,
    "address" VARCHAR(255) NOT NULL,
    "role" VARCHAR(255) NOT NULL,
    "balance" DECIMAL(8, 2) NOT NULL,
    "image" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "users" ADD PRIMARY KEY("id");
CREATE TABLE "defaule_messages"(
    "id" BIGINT NOT NULL,
    "message_type" VARCHAR(255) NOT NULL,
    "text" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "defaule_messages" ADD PRIMARY KEY("id");
CREATE TABLE "warehouses"(
    "id" BIGINT NOT NULL,
    "logistics_code" VARCHAR(255) NOT NULL,
    "address" VARCHAR(255) NOT NULL,
    "zip_code" VARCHAR(255) NOT NULL,
    "city" VARCHAR(255) NOT NULL,
    "region" VARCHAR(255) NOT NULL,
    "country" VARCHAR(255) NOT NULL,
    "phone_number" VARCHAR(255) NOT NULL,
    "mail" VARCHAR(255) NOT NULL,
    "work_schedule" VARCHAR(255) NOT NULL,
    "image" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "warehouses" ADD PRIMARY KEY("id");
CREATE TABLE "chat"(
    "id" BIGINT NOT NULL,
    "last_message" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL
);
ALTER TABLE
    "chat" ADD PRIMARY KEY("id");
CREATE TABLE "message_types"(
    "id" BIGINT NOT NULL,
    "message_type" BIGINT NOT NULL
);
ALTER TABLE
    "message_types" ADD PRIMARY KEY("id");
CREATE TABLE "message"(
    "id" BIGINT NOT NULL,
    "chat" BIGINT NOT NULL,
    "user" BIGINT NOT NULL,
    "message" VARCHAR(255) NOT NULL,
    "created_at" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
    "viewed" BOOLEAN NOT NULL
);
ALTER TABLE
    "message" ADD PRIMARY KEY("id");
ALTER TABLE
    "orders" ADD CONSTRAINT "orders_warehouse_foreign" FOREIGN KEY("warehouse") REFERENCES "warehouses"("id");
ALTER TABLE
    "balance_history" ADD CONSTRAINT "balance_history_admin_foreign" FOREIGN KEY("admin") REFERENCES "users"("id");
ALTER TABLE
    "orders" ADD CONSTRAINT "orders_user_foreign" FOREIGN KEY("user") REFERENCES "users"("id");
ALTER TABLE
    "message" ADD CONSTRAINT "message_user_foreign" FOREIGN KEY("user") REFERENCES "users"("id");
ALTER TABLE
    "orders" ADD CONSTRAINT "orders_product_foreign" FOREIGN KEY("product") REFERENCES "product"("id");
ALTER TABLE
    "message" ADD CONSTRAINT "message_chat_foreign" FOREIGN KEY("chat") REFERENCES "chat"("id");
ALTER TABLE
    "defaule_messages" ADD CONSTRAINT "defaule_messages_message_type_foreign" FOREIGN KEY("message_type") REFERENCES "message_types"("id");
ALTER TABLE
    "balance_history" ADD CONSTRAINT "balance_history_user_foreign" FOREIGN KEY("user") REFERENCES "users"("id");