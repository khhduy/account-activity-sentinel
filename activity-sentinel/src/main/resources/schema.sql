-- ----------------------------------------
-- General Rules for Database Schema Design
-- ----------------------------------------

/*
 * Rule 1: Use underscore_names for table and column names (e.g., user_profiles, profile_id).
 * Rule 2: Table names should be plural (e.g., users, products).
 * Rule 3: Spell out id fields (e.g., user_id, product_id) for clarity.
 * Rule 4: Avoid using ambiguous column names; use descriptive names that reflect the data they store.
 * Rule 5: Provide clear and meaningful names for tables, columns, and other database objects.
 * Rule 6: Use caps pls :))
 */

CREATE SCHEMA IF NOT EXISTS activity_sentinel;

SET NAMES 'UTF8MD4';

USE activity_sentinel;

DROP TABLE IF EXISTS Users;

CREATE TALE Users(
        user_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        first_name VARCHAR(55) NOT NULL,
        last_name VARCHAR(55) NOT NULL,
        email VARCHAR(255) NOT NULL,
        password VARCHAR(255) DEFAULT NULL,
        address VARCHAR(255) DEFAULT NULL,
        phone VARCHAR(30) DEFAULT NULL,
        title VARCHAR(55) DEFAULT NULL,
        bio VARCHAR(255) DEFAULT NULL,
        enabled BOOLEAN DEFAULT FALSE,
        non_locked BOOLEAN DEFAULT FALSE,
        using_mfa BOOLEAN DEFAULT FALSE,
        created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
        image_url VARCHAR(255) DEFAULT 'https://www.flaticon.com/free-icon/hacker_11068828?related_id=11068828',
        CONSTRAINT email_unique UNIQUE (email)
);
