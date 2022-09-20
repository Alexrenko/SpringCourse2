
create table orders(
    id bigserial primary key,
    username varchar(255) not null,
    total_price int not null,
    address varchar,
    phone varchar(255),
    created_at timestamp,
    updated_at timestamp
);

create table order_items(
    id bigserial primary key,
    order_id bigint not null references orders(id),
    product_id bigint not null,
    title varchar(255),
    quantity int not null,
    price_per_product int not null,
    price int not null,
    created_at timestamp,
    updated_at timestamp
);