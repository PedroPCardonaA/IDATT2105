# IDATT2105 Tokenly - NFT Marketplace

Tokenly is a Spring Boot 3 Maven project that serves as the backend for an NFT marketplace. It handles data persistence, user registration, and authentication using Spring Security 6, Spring JDBC, and an H2 in-memory database. This README provides an overview of the project and the necessary steps to run the server.

## Table of Contents

1. [Requirements](#requirements)
2. [Getting Started](#getting-started)
3. [Running the Server](#running-the-server)
4. [Database Configuration](#database-configuration)
5. [Database Description](#database-description)
6. [Authentication and Authorization](#authentication-and-authorization)

## Requirements

- Java 17
- Maven 3.9+ (if not using the included Maven wrapper)

## Getting Started

To get started, clone the repository to your local machine:

```bash
git clone https://github.com/PedroPCardonaA/IDATT2105.git
cd IDATT2105
```

## Running the Server

To run the server, use the Maven wrapper included in the project root folder.

MacOS/Linux:
```bash
./mvnw spring-boot:run
```

Windows:
```bash
.\mvnw.cmd spring-boot:run
```

The server will start on the default port '8080'.

## Database Configuration

Tokenly uses an H2 in-memory database, which is configured and constructed at runtime using SQL scripts included in resources/db/sql. No manual database configuration is required, as everything happens automatically when the system runs.

## Database Description

Our database is a database schema with multiple tables designed for an e-commerce platform. The users table stores information about users such as their username, password, and whether they are enabled. The authorities table is used for authorization and contains the usernames and their corresponding authorities. The profiles table contains user profile information, such as their name, email, birthdate, avatar, and balance.

The categories table stores information about the product categories, while the items table contains details about each product, including its name, owner, description, and source path. The listings table is used to create a listing for an item, which contains information such as the minimum and maximum price, publication time, number of visits, and whether it is closed.

The chats table tracks conversations between buyers and sellers, and the messages table stores the messages exchanged between them. The transactions table keeps a record of all transactions, including the listing, seller and buyer names, transaction price, and time. Lastly, the wish_list and bid tables store the user's wish list and bid information, respectively. These tables are linked to other tables using foreign keys to maintain data integrity.

## Authentication and Authorization

Tokenly's authentication and authorization are implemented using Spring Security 6 and OAuth2.0. User registration leverages Spring Security's password encoder to hash passwords using the bcrypt algorithm. User details are committed to the database using a JdbcUserDetailsManager. Authentication is done using a Spring Security AuthenticationManager and produces a JWT token valid for a one-hour session.

The implementation relies on hosting an internal authentication server that produces and verifies self-signed tokens. The tokens are signed using an RSA keypair generated programmatically at runtime, requiring no setup or interaction.
