CREATE TABLE ers_users (
    ers_user_id serial PRIMARY KEY,
    ers_username varchar(33) UNIQUE NOT NULL,
    ers_password varchar(33) NOT NULL,
    user_first_name varchar(100) NOT NULL,
    user_last_name varchar(100) NOT NULL,
    user_email varchar(150) UNIQUE NOT NULL,
    user_role_id int REFERENCES ers_user_roles(ers_user_role_id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE ers_user_roles (
    ers_user_role_id serial PRIMARY KEY,
    user_role varchar(30) NOT NULL
);

CREATE TABLE ers_reimbursement (
	reimb_id serial PRIMARY KEY,
	reimb_amount double precision NOT NULL,
	reimb_submitted timestamp DEFAULT now(),
	reimb_resolved timestamp,
    reimb_description varchar(300) NOT null,
    reimb_author int REFERENCES ers_users(ers_user_id) ON DELETE CASCADE NOT NULL,
    reimb_resolver int REFERENCES ers_users(ers_user_id) ON DELETE CASCADE,
    reimb_status_id int REFERENCES ers_reimbursement_status(reimb_status_id) ON DELETE CASCADE DEFAULT 1,
    reimb_type_id int REFERENCES ers_reimbursement_type(reimb_type_id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE ers_reimbursement_status (
                                          reimb_status_id serial PRIMARY KEY,
                                          reimb_status varchar(15) NOT NULL
);

CREATE TABLE ers_reimbursement_type (
                                        reimb_type_id serial PRIMARY KEY,
                                        reimb_type varchar(15) NOT NULL
);

-- Example user account creation (1 = Employee) | (2 = Financial Manager)
INSERT INTO ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id) values ('employee',  'enterthematrix', 'Keanu', 'Reeves', 'emp@revature.com', 1);
INSERT INTO ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id) values ('admin',  'pass1234', 'Administrator', 'SuperAdmin', 'admin@revature.com', 2);

-- Verify user account credentials for login purposes
SELECT * FROM ers_users;

-- Values to be hard coded into tables
INSERT INTO ers_user_roles VALUES (DEFAULT, 'Employee');
INSERT INTO ers_user_roles VALUES (DEFAULT, 'Financial Manager');

INSERT INTO ers_reimbursement_type VALUES (DEFAULT, 'Travel');
INSERT INTO ers_reimbursement_type VALUES (DEFAULT, 'Lodging');
INSERT INTO ers_reimbursement_type VALUES (DEFAULT, 'Food');
INSERT INTO ers_reimbursement_type VALUES (DEFAULT, 'Other');

INSERT INTO ers_reimbursement_status VALUES (DEFAULT, 'Pending');
INSERT INTO ers_reimbursement_status VALUES (DEFAULT, 'Approved');
INSERT INTO ers_reimbursement_status VALUES (DEFAULT, 'Denied');