drop table users

-- 1. users table (no foreign keys)
CREATE TABLE users (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    role ENUM('CITIZEN', 'CASE_OFFICER', 'INSPECTOR', 'POLICE_CAPTAIN') NOT NULL
);

-- 2. evidences table (no foreign keys yet)
CREATE TABLE evidences (
    evidence_id INT AUTO_INCREMENT PRIMARY KEY,
    description TEXT,
    file_path VARCHAR(255),
    crime_report_id INT 
);

-- 3. crime_reports table (foreign key to users, evidences)
CREATE TABLE crime_reports (
    incidentId INT AUTO_INCREMENT PRIMARY KEY,
    description TEXT,
    latitude DECIMAL(9,6),
    longitude DECIMAL(9,6),
    dateTime DATETIME,
    category ENUM('THEFT', 'ASSAULT', 'VANDALISM', 'OTHER') NOT NULL, 
    reporter_username VARCHAR(255),
    evidence_id INT,
    status VARCHAR(255),
    FOREIGN KEY (reporter_username) REFERENCES users(username),
    FOREIGN KEY (evidence_id) REFERENCES evidences(evidence_id)
);

-- 4. witnesses table (no foreign keys yet)
CREATE TABLE witnesses (
    witness_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    contact_information VARCHAR(255),
    case_id INT 
);

-- 5. investigations table (no foreign keys yet)
CREATE TABLE investigations (
    investigation_id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(255),
    case_id INT,
    assigned_officer_username VARCHAR(255)
);

-- 6. cases table (foreign keys to crime_reports, users, witnesses, investigations)
CREATE TABLE cases (
    case_id INT AUTO_INCREMENT PRIMARY KEY,
    crime_report_id INT,
    case_officer_username VARCHAR(255),
    witness_id INT,
    investigation_id INT,
    status ENUM('OPEN', 'CLOSED', 'PENDING', 'INVESTIGATING') NOT NULL,
    final_remarks TEXT,
    FOREIGN KEY (crime_report_id) REFERENCES crime_reports(incidentId),
    FOREIGN KEY (case_officer_username) REFERENCES users(username),
    FOREIGN KEY (witness_id) REFERENCES witnesses(witness_id),
    FOREIGN KEY (investigation_id) REFERENCES investigations(investigation_id)
);

-- 7. forum_posts table (foreign key to users)
CREATE TABLE forum_posts (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    content TEXT,
    author_username VARCHAR(255),
    category ENUM('SAFETY_TIPS', 'NEIGHBORHOOD_WATCH', 'LOCAL_INCIDENTS', 'OTHER') NOT NULL,
    timestamp DATETIME,
    FOREIGN KEY (author_username) REFERENCES users(username)
);

CREATE TABLE contact_information (
    contact_id INT AUTO_INCREMENT PRIMARY KEY,
    phone_number VARCHAR(20),
    email_address VARCHAR(255)
);

-- Now you can add foreign keys to evidences, witnesses, and investigations tables
ALTER TABLE evidences
ADD CONSTRAINT FK_evidences_crime_reports
FOREIGN KEY (crime_report_id) REFERENCES crime_reports(incidentId);

ALTER TABLE witnesses
ADD CONSTRAINT FK_witnesses_cases
FOREIGN KEY (case_id) REFERENCES cases(case_id);

ALTER TABLE investigations
ADD CONSTRAINT FK_investigations_cases
FOREIGN KEY (case_id) REFERENCES cases(case_id);

ALTER TABLE investigations
ADD CONSTRAINT FK_investigations_users
FOREIGN KEY (assigned_officer_username) REFERENCES users(username);

-- Remove the old contact_information column
ALTER TABLE witnesses
DROP COLUMN contact_information;

-- Add a foreign key to the contact_information table
ALTER TABLE witnesses
ADD COLUMN contact_id INT,
ADD CONSTRAINT FK_witnesses_contact_information
FOREIGN KEY (contact_id) REFERENCES contact_information(contact_id);