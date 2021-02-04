CREATE TABLE merchant
(
    id         INT            NOT NULL AUTO_INCREMENT,
    name       VARCHAR(60)    NOT NULL,
    bankName   VARCHAR(100)   NOT NULL,
    swift      VARCHAR(40)    NOT NULL,
    account    VARCHAR(20)    NOT NULL,
    charge     DECIMAL(5, 2)  NOT NULL,
    per        SMALLINT       NOT NULL,
    minSum     DECIMAL(19, 2) NOT NULL,
    needToSend DECIMAL(19, 2),
    sent       DECIMAL(19, 2),
    lastSent   DATE,
    PRIMARY KEY (id)
);

select *
from merchant;

TRUNCATE TABLE merchant;

CREATE TABLE customer
(
    id       INT          NOT NULL AUTO_INCREMENT,
    name     VARCHAR(60)  NOT NULL,
    address  VARCHAR(300) NOT NULL,
    email    VARCHAR(90)  NOT NULL,
    ccNo     VARCHAR(20)  NOT NULL,
    ccType   VARCHAR(60)  NOT NULL,
    maturity DATE,
    PRIMARY KEY (id)
);

select *
from customer;

CREATE TABLE payment
(
    id         INT       NOT NULL AUTO_INCREMENT,
    dt         TIMESTAMP NOT NULL,
    merchantId INT       NOT NULL,
    customerId INT       NOT NULL,
    goods      VARCHAR(500),
    sumPaid    DECIMAL(15, 2),
    chargePaid DECIMAL(15, 2),
    FOREIGN KEY mer_fk (merchantId) REFERENCES merchant (id),
    FOREIGN KEY cust_fk (customerId) REFERENCES customer (id),
    PRIMARY KEY (id)
);

select *
from payment;

SELECT dt, merchantId, customerId, goods, sumPaid
FROM payment
WHERE merchantId = 3;

SELECT id, name, charge, per, minsum
FROM merchant
WHERE name LIKE '%Ltd%';

SELECT dt, merchantId, customerId, goods, sumPaid
FROM payment
order by merchantId;

SELECT SUM(sumPaid)
FROM payment
WHERE customerId = 2;

SELECT merchantId, COUNT(*) AS n, SUM(sumPaid) AS total
FROM payment
GROUP BY merchantId;

SELECT customerId, SUM(sumPaid)
FROM payment
GROUP BY customerId
HAVING COUNT(*) > 2;

SELECT p.dt,
       m.name as merchant,
       c.name as customer,
       p.goods,
       p.sumPaid
FROM payment p,
     merchant m,
     customer c
WHERE m.id = p.merchantId
  and c.id = p.customerId;

SELECT p.dt,
       m.name as merchant,
       c.name as customer,
       p.goods,
       p.sumPaid
FROM payment p
         LEFT OUTER JOIN merchant m on m.id = p.merchantId
         LEFT OUTER JOIN customer c on c.id = p.customerId;

UPDATE payment
SET chargePaid = sumPaid * 0.034
WHERE id = 1;

SELECT *
FROM payment;

-- In MySQL, you can't modify the same table which you use in the SELECT part.
UPDATE payment
SET chargePaid =
        (SELECT p.sumPaid * m.charge / 100.0
         FROM payment p,
              merchant m
         WHERE m.id = p.merchantId
           and p.id = 2)
WHERE id = 2;

UPDATE payment p
    JOIN merchant m on p.merchantId = m.id
SET p.chargePaid = p.sumPaid * m.charge / 100.0
WHERE p.id =1;

UPDATE payment p
SET chargePaid = sumPaid * (SELECT charge
                            FROM merchant m
                            WHERE m.id = p.merchantId) / 100.0;

UPDATE merchant m
SET needToSend =
        (SELECT sum(sumPaid - charge)
         FROM payment p
         WHERE p.merchantId = m.id);


SELECT * FROM merchant m where m.needToSend IS NULL;

