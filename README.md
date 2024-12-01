# Cloudbees Train Booking API
CloudBees Booking Application to manage bookings for travels by train.

There are 4 main **APIs** to manage the processes;
1. **Creating a booking:** Purchases a ticket for a passenger using the details below;
- From
- To
- User
  - Name
  - Surname
  - Email Address
- Price paid.

  **Note:** After a succesffull booking a seat from an **A** or **B** section will be allocated for the passenger.
```
POST /tickets 
```
```bash
curl -X 'POST' 'http://localhost:8080/tickets' -H 'accept: application/json' -H 'Content-Type: application/json' -d '{
  "name": "Mazlum",
  "surname": "Demirel",
  "email": "mazlummerdandemirel@gmail.com",
  "section": "A",
  "number": 1,
  "location": "UPPER_BED",
  "departure": "France",
  "destination": "London"
}'
```
```
SAMPLE SUCCESS RESPONSE

{
"id": 1,
"name": "Mazlum",
"surname": "Demirel",
"email": "mazlummerdandemirel@gmail.com",
"section": "A",
"number": 1,
"location": "UPPER_BED",
"departure": "France",
"destination": "London",
"pricePaid": 20
}
```
```
SAMPLE ERROR RESPONSE

{
  "messages": [
    "SEAT not found"
  ]
}
```
2. **Get Receipt:** Retrieves the details of the paxDetail for the passenger.
```
GET /tickets/{ticketId} 
```
```bash
curl -X 'GET' \
'http://localhost:8080/tickets/1' \
-H 'accept: application/json'
```
```
SAMPLE TICKET RESPONSE

{
  "id": 1,
  "name": "Mazlum",
  "surname": "Demirel",
  "email": "mazlummerdandemirel@gmail.com",
  "section": "A",
  "number": 1,
  "location": "UPPER_BED",
  "departure": "France",
  "destination": "London",
  "pricePaid": 20
}
```
```
SAMPLE NOT FOUND RESPONSE
{
  "messages": [
    "TICKET not found"
  ]
}
```

3. **Filter passenger/seat info bysection:** Retrieves the passenger info depending on the section.
```
GET /tickets?section=[A,B] 
```
```bash
curl -X 'GET' 'http://localhost:8080/tickets?section=A' -H 'accept: application/json'
```
```
SAMPLE RESPONSE

{
"section": "A",
"paxes": [
{
"id": 1,
"name": "Mazlum",
"surname": "Demirel",
"email": "mazlummerdandemirel@gmail.com",
"section": "A",
"number": 1,
"location": "UPPER_BED",
"departure": "France",
"destination": "London",
"pricePaid": 20
}
]
}
```

4. **Cancel the Purchase:** Removes a passenger from the train.
```
DELETE /tickets/{ticketId} 
```
```bash
curl -X 'DELETE' 'http://localhost:8080/tickets/10' -H 'accept: */*'
```

5. **Modify Ticket:** Changes passenger's seat.
```
PUT /tickets/{ticketId} 
```
```bash
curl -X 'PUT' 'http://localhost:8080/tickets/1' -H 'accept: */*' -H 'Content-Type: application/json' -d '{
  "number": 2,
  "section": "A",
  "location": "LOWER_BED"
}'
```
```
SAMPLE ERROR RESPONSE

{
  "messages": [
    "SEAT not found"
  ]
}
```


#### NOTE: To access the swagger UI you can click below link after running the application.

[http://localhost:8080/swagger.html](http://localhost:8080/swagger.html)

### Instructions
##### Building and launching tests
Developed with JAVA 22, no extra requirements are needed for build and launch the application.

Building and launching tests:
```bash
./gradlew build
```
You can see the Jacoco coverage report at **/build/reports/jacoco/test/html/index.html**

##### Running
```bash
./gradlew bootRun
```

Alternatively, your can run this project with docker, since the Dockerfile contains
a multi-stage build, no build in the host machine is needed before:
```bash
docker build --tag=cloudbees/booking:latest . 
docker run -p8080:8080 cloudbees/booking