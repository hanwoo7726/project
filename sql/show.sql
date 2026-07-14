ALTER TABLE users.p_user
ADD COLUMN user_role_enum varchar(255);


select * from users.p_user;


update users.p_user
set user_role_enum = 'ROLE_USER'
WHERE user_role_enum = 'USER_ROLE';