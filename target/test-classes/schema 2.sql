Drop Table if exists user_role CASCADE;
Drop table if exists form_status CASCADE;

create user_role (
    id  IDENTITY,
    user_role varchar(255)
)

create form_status (
    id  IDENTITY,
    form_status varchar(255)
)