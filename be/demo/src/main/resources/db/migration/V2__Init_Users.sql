INSERT INTO users(email, first_name, last_name, password, role)
VALUES (
           'admin',
           'admin',
           'admin',
           '$2a$11$Xhln5JuapOW9Tz51YUL8POiFES.GgOiVpcuI2raFj6sQcAxV4GdjW', /* admin */
           'ROLE_ADMIN'
       ),(
           'pero.peric@gmail.com',
           'Pero',
           'Peric',
           '$2a$11$ALDK5jorbCIAUmRBkc8pgeBZDxvQBN8T4mxenCnuOkYfXK0s3IRYm', /* user */
           'ROLE_USER'
       );