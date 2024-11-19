insert into roles (name)
values
    ('ROLE_USER'), ('ROLE_ADMIN');

insert into users (username, password, email)
values
    ('user@gmail.com', '$2a$10$tSGpDUPfQoizhiyyp5TPB.KkvOzS6Sc90JlVe9d815cjRymJPpiA2', 'user@gmail.com'),
    ('admin@gmail.com', '$2a$10$tSGpDUPfQoizhiyyp5TPB.KkvOzS6Sc90JlVe9d815cjRymJPpiA2', 'admin@gmail.com');

insert into users_roles (user_id, role_id)
values
    (1, 1),
    (2, 2);