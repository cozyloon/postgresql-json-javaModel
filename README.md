# postgresql-json-javaModel

Required Postgresql queries

To create a table called employee with columns name, address, and age, you can use the following SQL statement:
```sql
CREATE TABLE employee(
    name VARCHAR(255) NOT NULL,
    address VARCHAR (255),
    age int
);
```

To select all the data from the employee table, use the following SQL statement:
```sql
SELECT * FROM employee;
```

To insert a new record into the employee table, use the following SQL statement:
```sql
INSERT INTO employee (name, address, age) VALUES ('chathumal', 'polonnaruwa', 26);
```

To select data from the employee table where the name column is equal to 'chathumal', use the following SQL statement:
```sql
SELECT * FROM employee WHERE name = 'chathumal';
```

To create a table called orders with columns id (a serial primary key) and info (a JSON column), use the following SQL statement:
```sql
CREATE TABLE orders (
    id serial NOT NULL PRIMARY KEY,
    info json NOT NULL
);
```

To insert data into the orders table, use the following SQL statement:
```sql
INSERT INTO orders (info)
VALUES ('{ "customer": "Lily Bush", "items": {"product": "Diaper","qty": 24}}'),
       ('{ "customer": "Josh William", "items": {"product": "Toy Car","qty": 1}}'),
       ('{ "customer": "Mary Clark", "items": {"product": "Toy Train","qty": 2}}');
```

To select all the data from the orders table, use the following SQL statement:
```sql
SELECT info FROM orders;
```

To select data from the orders table where the customer field of the info column is equal to 'Lily Bush', use the following SQL statement:
```sql
SELECT info FROM orders WHERE info->>'customer' = 'Lily Bush';
```
