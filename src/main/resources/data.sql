INSERT IGNORE INTO role (id, name_role)
 VALUES
 ('1', 'USER'),
 ('2', 'ADMIN');

INSERT IGNORE INTO `user` (`id`, `account_number`, `email`, `enabled`, `name`, `password`)
VALUES ('1', '123', 'admin@gmail.com', '0', 'admin', '$2a$10$IinfdWPgPR3P4iY8PXlx/usRTHty2J3k16hi7qwf5xaA8lCpmzpCq');
INSERT IGNORE  INTO `users_has_roles` (`role_id`, `user_id`) VALUES ('2', '1');
