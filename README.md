# Problem Description

As an MVP we decided to create a RESTfull API where users can create an account and initiate a money transfer.

### Domain

#### Account
Every account can contain multiple money transfers. A new account should be created via endpoint and can have a 100 units of balance upon creation. Account supports single currency, but you should be able to create multiple Accounts in different currencies.
Only two currencies are supported at the moment - Euro (EUR) and British Pounds (GBP).

#### Transaction
Transactions happen on the account and might be of two types (Incoming or Outgoing). Incoming transactions increase an account's balance while outgoing decrease it.

# Project

### Stack
* Java 17
* Gradle / Maven
* Spring Boot 3.1
  * Spring Web MVC
  * Spring Data JPA
* H2 in-memory database

### Navigating through code
* Code - [src/main/java/com/teya/bankingsystem](./src/main/java/com/teya/bankingsystem)
* Tests - [src/test/java/com/teya/bankingsystem](./src/test/java/com/teya/bankingsystem)

### Building and running the app
#### Gradle
```
./gradlew build
./gradlew bootRun
```
#### Maven
```
./mvnw install
./mvnw spring-boot:run
```
