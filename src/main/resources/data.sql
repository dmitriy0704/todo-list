insert into roles (name)
values
    ('ROLE_USER'), ('ROLE_ADMIN');

insert into users (username, password, email)
values
    ('admin@gmail.com', '$2a$10$tSGpDUPfQoizhiyyp5TPB.KkvOzS6Sc90JlVe9d815cjRymJPpiA2', 'admin@gmail.com'),
    ('user1@gmail.com', '$2a$10$tSGpDUPfQoizhiyyp5TPB.KkvOzS6Sc90JlVe9d815cjRymJPpiA2', 'user1@gmail.com'),
    ('user2@gmail.com', '$2a$10$tSGpDUPfQoizhiyyp5TPB.KkvOzS6Sc90JlVe9d815cjRymJPpiA2', 'user2@gmail.com'),
    ('user3@gmail.com', '$2a$10$tSGpDUPfQoizhiyyp5TPB.KkvOzS6Sc90JlVe9d815cjRymJPpiA2', 'user3@gmail.com');
insert into users_roles (user_id, role_id)
values
    (1, 1),
    (2, 2);