-- ----------------------------------------
-- General Rules for Database Schema Design
-- ----------------------------------------

/*
 * Rule 1: Use underscore_names for table and column names (e.g., user_profiles, profile_id).
 * Rule 2: Table names should be plural (e.g., users, products).
 * Rule 3: Spell out id fields (e.g., user_id, product_id) for clarity.
 * Rule 4: Avoid using ambiguous column names; use descriptive names that reflect the data they store.
 * Rule 5: Provide clear and meaningful names for tables, columns, and other database objects.
 * Rule 6: Use caps pls
 */

CREATE SCHEMA IF NOT EXISTS activity_sentinel;

USE activity_sentinel;

DROP TABLE IF EXISTS Users;

CREATE TABLE Users
(
        id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        first_name   VARCHAR(55) NOT NULL,
        last_name    VARCHAR(55) NOT NULL,
        email        VARCHAR(255) NOT NULL,
        password     VARCHAR(255) DEFAULT NULL,
        address      VARCHAR(255) DEFAULT NULL,
        phone        VARCHAR(30) DEFAULT NULL,
        title        VARCHAR(55) DEFAULT NULL,
        bio          VARCHAR(255) DEFAULT NULL,
        enabled      BOOLEAN DEFAULT FALSE,
        non_locked   BOOLEAN DEFAULT FALSE,
        using_mfa    BOOLEAN DEFAULT FALSE,
        created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
        image_url    VARCHAR(255) DEFAULT 'https://www.flaticon.com/free-icon/hacker_11068828?related_id=11068828',
        CONSTRAINT UQ_Users_Email UNIQUE (email)
);

DROP TABLE IF EXISTS Roles;
CREATE TABLE Roles
(
        id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        name       VARCHAR(55) NOT NULL,
        permission VARCHAR(255) NOT NULL, -- user:read, user:write, user:delete, customer:read
        CONSTRAINT UQ_Roles_Name UNIQUE (name)
);

DROP TABLE IF EXISTS UserRoles;
CREATE TABLE UserRoles
(
        id      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        user_id BIGINT UNSIGNED NOT NULL,
        role_id BIGINT UNSIGNED NOT NULL,
        FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
        FOREIGN KEY (role_id) REFERENCES Roles (id) ON DELETE CASCADE ON UPDATE CASCADE,
        CONSTRAINT UQ_UserRoles_User_Id UNIQUE (user_id)
);

DROP TABLE IF EXISTS Events;
CREATE TABLE Events
(
        id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        type        VARCHAR(55) NOT NULL, CHECK(type IN (
                'LOGIN_ATTEMPT', 'LOGIN_ATTEMPT_FAILURE',
                'LOGIN_ATTEMPT_SUCCESS', 'LOGOUT',
                'PROFILE_UPDATE', 'ROLE_UPDATE',
                'ACCOUNT_SETTING_UPDATE', 'PASSWORD_UPDATE',
                'PASSWORD_RESET', 'MFA_UPDATE'
                )),
        description VARCHAR(255) NOT NULL,
        CONSTRAINT UQ_Events_Type UNIQUE (type)
);

DROP TABLE IF EXISTS UserEvents;
CREATE TABLE UserEvents
(
        id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        user_id    BIGINT UNSIGNED NOT NULL,
        event_id   BIGINT UNSIGNED NOT NULL,
        device     VARCHAR(100) DEFAULT NULL,
        ip_address VARCHAR(100) DEFAULT NULL,
        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
        FOREIGN KEY (event_id) REFERENCES Events (id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS AccountVerifications;
CREATE TABLE AccountVerifications
(
        id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        user_id    BIGINT UNSIGNED NOT NULL,
        token      VARCHAR(255) NOT NULL,
        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
        CONSTRAINT UQ_AccountVerifications_User_Id UNIQUE (user_id),
        CONSTRAINT UQ_AccountVerifications_Token UNIQUE (token)
);

DROP TABLE IF EXISTS PasswordReset;
CREATE TABLE PasswordReset
(
        id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        user_id    BIGINT UNSIGNED NOT NULL,
        token      VARCHAR(255) NOT NULL,
        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
        expires_at DATETIME NOT NULL,       
        FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
        CONSTRAINT UQ_PasswordReset_User_Id UNIQUE (user_id),
        CONSTRAINT UQ_PasswordReset_Token UNIQUE (token)
);

DROP TABLE IF EXISTS TwoFactorVerifications;
CREATE TABLE TwoFactorVerifications
(
        id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        user_id    BIGINT UNSIGNED NOT NULL,
        code       VARCHAR(10) NOT NULL,
        expires_at DATETIME NOT NULL,  
        FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
        CONSTRAINT UQ_TwoFactorVerifications_User_Id UNIQUE (user_id),
        CONSTRAINT UQ_TwoFactorVerifications_Token UNIQUE (code)
);

# INSERT INTO Roles (name, permission)
# VALUES ('ROLE_USER', 'READ:USER,READ:CUSTOMER'),
#      ('ROLE_MANAGER', 'READ:USER,READ:CUSTOMER,UPDATE:USER,UPDATE:CUSTOMER'),
#      ('ROLE_ADMIN', 'READ:USER,READ:CUSTOMER,CREATE:USER,CREATE:CUSTOMER,UPDATE:USER,UPDATE:CUSTOMER'),
#      ('ROLE_SYSADMIN', 'READ:USER,READ:CUSTOMER,CREATE:USER,CREATE:CUSTOMER,UPDATE:USER,UPDATE:CUSTOMER,DELETE:USER,DELETE:CUSTOMER');
