INSERT INTO EXPERT VALUES (101, 'Ramesh', 'AVAILABLE', 8);
COMMIT;
INSERT INTO EXPERT VALUES (102, 'Suresh', 'AVAILABLE', 8);
COMMIT;
INSERT INTO EXPERT VALUES (103, 'Ganesh', 'AVAILABLE', 8);
COMMIT;
INSERT INTO EXPERT VALUES (104, 'Mahesh', 'AVAILABLE', 8);
COMMIT;

INSERT INTO CUSTOMER VALUES (201, 'Sundar');
COMMIT;
INSERT INTO CUSTOMER VALUES (202, 'Mandar');
COMMIT;
INSERT INTO CUSTOMER VALUES (203, 'Kedar');
COMMIT;

INSERT INTO ACCOUNTING_TASK VALUES ('Task 1', 'Review Sales', 2, 2, null);
COMMIT;
INSERT INTO ACCOUNTING_TASK VALUES ('Task 2', 'Review Purchases', 2, 2, 'Task 1');
COMMIT;
INSERT INTO ACCOUNTING_TASK VALUES ('Task 3', 'Compute Tax', 2, 3, 'Task 2');
COMMIT;
INSERT INTO ACCOUNTING_TASK VALUES ('Task 4', 'Add Tax to Books', 2, 1, 'Task 3');
COMMIT;