# Spring Boot Micro-Services

## Guitar Store - Amps micro-service

### Startup

`mvn spring:boot:run`

### Shutdown

`CTRL-C`

### Configuration

#### Environment Variables

* MARIA_HOST: The hostname of the MariaDB instance to connect to (default: localhost).
* MARIA_PORT: The port that the MariaDB instance is listening on (default: 3306).
* MARIA_DATABASE: The name of the database to use (default: sbms).
* MARIA_USER: The username to connect to MariaDB with (default: sbms).
* MARIA_PASSWORD: The password to use to connect to MariaDB (default: sbms).
* KAFKA_HOST: The hostname of the Kafka instance to connect to (default: localhost).
* KAFKA_PORT: The port that the Kafka instance is listening on (default: 9092).
* KAFKA_TOPIC_AMPS_REQUESTED: The name of the Kafka topic for the "amps requested" event
  (default: sbms.event.amps-requested).
