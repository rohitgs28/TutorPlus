--
-- File generated with SQLiteStudio v3.1.1 on Wed Nov 8 21:57:35 2017
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: tbl_tutor_review
CREATE TABLE tbl_tutor_review (review_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, tutor_id INTEGER REFERENCES tbl_TutorPlus_tutor (tutor_id), review_stars CHAR (50), review_message CHAR (200));

-- Table: tbl_TutorPlus_Booking_Portal
CREATE TABLE tbl_TutorPlus_Booking_Portal (booking_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, tutor_id INTEGER REFERENCES tbl_TutorPlus_tutor (tutor_id), student_id INTEGER REFERENCES tbl_TutorPlus_Student (student_id), time_id INTEGER REFERENCES tbl_TutorPlus_Student (student_id), subject_id REFERENCES tbl_TutorPlus_subject (subject_id));

-- Table: tbl_TutorPlus_location
CREATE TABLE tbl_TutorPlus_location (location_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, location_name CHAR (200));

-- Table: tbl_TutorPlus_Student
CREATE TABLE tbl_TutorPlus_Student (student_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, user_id INTEGER REFERENCES tbl_TutorPlus_User (user_id), student_name CHAR (100), location_id INTEGER REFERENCES tbl_TutorPlus_location (location_id));

-- Table: tbl_TutorPlus_subject
CREATE TABLE tbl_TutorPlus_subject (subject_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, subject_name CHAR (100));

-- Table: tbl_tutorPlus_timings
CREATE TABLE tbl_tutorPlus_timings (timings_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, user_id INTEGER REFERENCES tbl_TutorPlus_User (user_id), Timings CHAR, Week_day CHAR (50), Availablity BOOLEAN, tutor_id INTEGER REFERENCES tbl_TutorPlus_tutor (tutor_id));

-- Table: tbl_TutorPlus_tutor
CREATE TABLE tbl_TutorPlus_tutor (tutor_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, user_id INTEGER REFERENCES tbl_TutorPlus_User (user_id), user_name CHAR (100), tutor_given_name CHAR (100), credentials CHAR (100), achievements CHAR (100), subject_id INTEGER REFERENCES tbl_TutorPlus_subject (subject_id), location_id INTEGER REFERENCES tbl_TutorPlus_location (location_id), max_hours CHAR, contact_no CHAR (15), tutor_email CHAR (30), cost_per_hour CHAR (30));

-- Table: tbl_TutorPlus_User
CREATE TABLE tbl_TutorPlus_User (user_id INTEGER PRIMARY KEY UNIQUE NOT NULL, user_name CHAR (100), user_role CHAR (10));

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
