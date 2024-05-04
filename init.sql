-- Disable foreign key checks
SET FOREIGN_KEY_CHECKS = 0;

-- Drop tables in reverse order of dependencies
DROP TABLE IF EXISTS user_documents;
DROP TABLE IF EXISTS user_category;
DROP TABLE IF EXISTS document_versions;
DROP TABLE IF EXISTS documents;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS categories;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- Create tables
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       is_admin BOOLEAN NOT NULL
);

CREATE TABLE documents (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE document_versions (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   document_id BIGINT NOT NULL,
                                   timestamp DATETIME NOT NULL,
                                   file_path VARCHAR(255) NOT NULL,
                                   is_read BOOLEAN NOT NULL,
                                   is_latest BOOLEAN NOT NULL,
                                   is_visible BOOLEAN NOT NULL,
                                   FOREIGN KEY (document_id) REFERENCES documents(id)
);

CREATE TABLE categories (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE user_category (
                               category_id BIGINT NOT NULL,
                               user_id BIGINT NOT NULL,
                               PRIMARY KEY (category_id, user_id),
                               FOREIGN KEY (category_id) REFERENCES categories(id),
                               FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE user_documents (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                user_id BIGINT NOT NULL,
                                document_version_id BIGINT NOT NULL,
                                has_read BOOLEAN NOT NULL,
                                FOREIGN KEY (user_id) REFERENCES users(id),
                                FOREIGN KEY (document_version_id) REFERENCES document_versions(id)
);

-- Insert test data for users
INSERT INTO users (username, email, is_admin) VALUES
                                                  ('john_doe', 'john.doe@example.com', FALSE),
                                                  ('jane_smith', 'jane.smith@example.com', FALSE),
                                                  ('alice_jones', 'alice.jones@example.com', TRUE),
                                                  ('bob_brown', 'bob.brown@example.com', TRUE),
                                                  ('charlie_clark', 'charlie.clark@example.com', FALSE),
                                                  ('emily_evans', 'emily.evans@example.com', FALSE),
                                                  ('david_wilson', 'david.wilson@example.com', FALSE);

-- Insert test data for documents
INSERT INTO documents (name) VALUES
                                 ('Project Proposal'),
                                 ('Annual Report'),
                                 ('Meeting Minutes'),
                                 ('Sales Data Analysis'),
                                 ('Product Launch Plan');

-- Insert test data for document_versions
INSERT INTO document_versions (document_id, timestamp, file_path, is_read, is_latest, is_visible) VALUES
                                                                                                      (1, '2024-01-01 12:00:00', '/documents/proposal_v1.docx', FALSE, TRUE, TRUE),
                                                                                                      (1, '2024-01-02 12:00:00', '/documents/proposal_v2.docx', TRUE, FALSE, TRUE),
                                                                                                      (2, '2024-02-01 14:30:00', '/documents/report_v1.pdf', FALSE, TRUE, TRUE),
                                                                                                      (2, '2024-02-02 15:00:00', '/documents/report_v2.pdf', TRUE, FALSE, TRUE),
                                                                                                      (3, '2024-03-01 09:00:00', '/documents/minutes_v1.txt', FALSE, TRUE, TRUE),
                                                                                                      (4, '2024-03-02 11:00:00', '/documents/sales_v1.xlsx', TRUE, TRUE, TRUE),
                                                                                                      (5, '2024-04-01 10:00:00', '/documents/launch_plan_v1.pptx', FALSE, TRUE, TRUE);

-- Insert test data for categories
INSERT INTO categories (name) VALUES
                                  ('Finance'),
                                  ('Management'),
                                  ('Sales'),
                                  ('Product Development'),
                                  ('Marketing');

-- Insert test data for user_category
INSERT INTO user_category (category_id, user_id) VALUES
                                                     (1, 1),
                                                     (1, 2),
                                                     (2, 3),
                                                     (2, 4),
                                                     (3, 5),
                                                     (4, 6),
                                                     (5, 7);

-- Insert test data for user_documents
INSERT INTO user_documents (user_id, document_version_id, has_read) VALUES
                                                                        (1, 1, FALSE),
                                                                        (2, 2, TRUE),
                                                                        (3, 3, FALSE),
                                                                        (4, 4, TRUE),
                                                                        (5, 5, FALSE),
                                                                        (6, 6, TRUE),
                                                                        (7, 7, FALSE);
