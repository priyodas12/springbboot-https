## Overview

- HTTPS ensures secure communication between client and server.
- For local development, browsers and clients do **not trust self-signed certificates** by default.
- Creating your **own CA** allows you to sign server certificates, which can then be trusted locally.

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