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
curl --request POST \
  --url https://localhost:8443/auth/customers/bulk/10 \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/11.4.0'
````

### suggest customer usernames:

````
curl --request POST \
  --url https://localhost:8443/auth/customers/suggest/usernames \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/11.4.0' \
  --data '{
	"characters":"abc",
	"count":5
}'

response: 

[
	"abctravel6163",
	"abctravel7790",
	"abcsuper7858",
	"abcpro8721",
	"abcpro8081"
]

````

### suggest customer usernames:

````
curl --request POST \
  --url https://localhost:8443/auth/customers/fuzzy/alpha \
  --header 'User-Agent: insomnia/11.4.0'

response: 

[
	"andyfrialpha1225",
	"curtn.ralpha564",
	"maxlittalpha533",
	"jasminealpha9572",
	"claranealpha1477",
	"benlyonalpha2384",
	"missysialpha2144",
	"ottob.kalpha4072",
	"allenrein8818",
	"alexblablog5560"
]

````

```

PostgreSQL’s pg_trgm extension provides trigram-based text search.
It breaks strings into overlapping 3-character chunks (trigrams) and uses those to measure similarity between strings.

🧿 Why we use it

    Fuzzy search → find close matches even with typos (alpha ~ alfa, alphaa123).

    Substring & ILIKE acceleration → speeds up queries like ILIKE '%term%'.
    
```

### Using Similarity Search:

```
SELECT username, similarity(username, 'alp') AS sim
FROM customers
WHERE similarity(username,'alp') > 0.05
ORDER BY sim DESC
LIMIT 50;

Result:

[
  {
    "username": "allenrein8818",
    "sim": 0.125
  },
  {
    "username": "alexblablog5560",
    "sim": 0.11111111
  },
  {
    "username": "andyfrialpha1225",
    "sim": 0.10526316
  },
  {
    "username": "allenresuper2745",
    "sim": 0.10526316
  },
  {
    "username": "andyfriin5951",
    "sim": 0.05882353
  },
  {
    "username": "annettecn8470",
    "sim": 0.05882353
  },
  {
    "username": "anniebupro8160",
    "sim": 0.055555556
  },
  {
    "username": "maxlittalpha533",
    "sim": 0.05263158
  },
  {
    "username": "curtn.ralpha564",
    "sim": 0.05263158
  },
  {
    "username": "anneteaprime5156",
    "sim": 0.05
  }
]
```