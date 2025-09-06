# Spring Boot Secure App with PostgreSQL Fuzzy Search

This project demonstrates a **Spring Boot application** configured with:

- **HTTPS** for secure communication
- **JWT-based authentication**
- **PostgreSQL** with fuzzy search capabilities
- Recommended username setup for local development

### Features

- **User Authentication:** JWT tokens for login and resource access
- **Fuzzy Search:** Leverages PostgreSQL `pg_trgm` extension for approximate string matching(to be added**)
- **HTTPS:** Secure communication using a self-signed CA
- **Spring Security:** Protect endpoints and whitelist specific URLs
- **Database:** PostgreSQL with proper schema, sequences, and constraints

---

### Prerequisites

- Java 21
- Maven
- PostgreSQL 15+
- OpenSSL (for certificate generation)
- Postman or Insomnia (for API testing)

### secure sign up:

```
curl --request POST \
  --url https://localhost:8443/auth/signup \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/11.4.0' \
  --data '{
	"username": "username",
	"password": "your_password",
	"email": "username@host.domain"
}'


```

### bulk load customers:

````
curl --request GET \
  --url https://localhost:8443/auth/customers/10 \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/11.4.0'
````