create schema demo;
use
demo;

create table customers
(
    customer_id   bigint primary key,
    customer_name varchar not null
);

create table transactions
(
    txn_id        bigint primary key,
    customer_id   bigint                   not null,
    txn_amount    double                   not null,
    txn_timestamp timestamp with time zone not null,
    foreign key (customer_id) references customers (customer_id)
);
