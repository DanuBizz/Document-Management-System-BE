-- Insert Admin data for users if AD deactivated
INSERT INTO users (username, email, is_admin) VALUES
                                                  ('admin', 'admin@example.com', TRUE);