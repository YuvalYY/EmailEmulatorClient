Project for emulating an email client using a REST client

* The client sends a request in a body which contains
  the recipient and sender's email addresses and the body.
* The client is used by an example REST controller in this project.
* Example of usage: curl --location 'http://localhost:8082/emailClient/send' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "toEmail": "your.email@yahoo.com",
  "fromEmail": "my.email@world.com",
  "body": "Hi, how are you?"
  }'
