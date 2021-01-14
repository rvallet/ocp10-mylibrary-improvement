-- library_bdd
CREATE DATABASE `library_bdd`

-- library_bdd.book

CREATE TABLE `book` (
  `id_book` bigint(20) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) DEFAULT NULL,
  `collection` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `editor` varchar(255) DEFAULT NULL,
  `img_path_th_attribute` varchar(255) DEFAULT NULL,
  `is_loan_available` bit(1) DEFAULT NULL,
  `is_online` bit(1) DEFAULT NULL,
  `isbn` varchar(255) DEFAULT NULL,
  `release_date` datetime DEFAULT NULL,
  `short_description` varchar(255) DEFAULT NULL,
  `stock` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_book`),
  UNIQUE KEY `UKehpdfjpu1jm3hijhj4mm0hx9h` (`isbn`)
)


-- library_bdd.book_loan definition

CREATE TABLE `book_loan` (
  `id_book_loan` bigint(20) NOT NULL AUTO_INCREMENT,
  `end_loan` datetime DEFAULT NULL,
  `is_loan_extended` bit(1) DEFAULT NULL,
  `loan_status` varchar(255) DEFAULT NULL,
  `return_loan` datetime DEFAULT NULL,
  `start_loan` datetime DEFAULT NULL,
  `id_book` bigint(20) DEFAULT NULL,
  `id_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_book_loan`),
  KEY `FKd1wl3wjd4p0bs72qxdi817iuy` (`id_book`),
  KEY `FKf48jwtplg1klh10uyefv174ic` (`id_user`)
)


-- library_bdd.book_loan_email_reminder definition

CREATE TABLE `book_loan_email_reminder` (
  `id_book_loan_email_reminder` bigint(20) NOT NULL AUTO_INCREMENT,
  `book_id` bigint(20) DEFAULT NULL,
  `book_loan_id` bigint(20) DEFAULT NULL,
  `book_title` varchar(255) DEFAULT NULL,
  `end_loan` datetime DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `is_email_sent` bit(1) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `sending_email_date` datetime DEFAULT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_book_loan_email_reminder`)
)


-- library_bdd.`user` definition

CREATE TABLE `user` (
  `id_user` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `reset_token` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`)
)

