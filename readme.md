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