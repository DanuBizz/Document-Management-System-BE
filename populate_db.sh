#!/bin/bash

# Users
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "Mike", "email": "mike@example.com", "isAdmin": false}'
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "Luigi", "email": "luigi@example.com", "isAdmin": true}'
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "Sonic", "email": "sonic@example.com", "isAdmin": false}'
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "Peach", "email": "peach@example.com", "isAdmin": true}'
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "Mario", "email": "mario@example.com", "isAdmin": false}'
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "Zelda", "email": "zelda@example.com", "isAdmin": true}'
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "Link", "email": "link@example.com", "isAdmin": false}'
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "Samus", "email": "samus@example.com", "isAdmin": true}'
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "Kirby", "email": "kirby@example.com", "isAdmin": false}'
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "DonkeyKong", "email": "donkeykong@example.com", "isAdmin": true}'

# Documents
curl -X POST http://localhost:8080/documents -H "Content-Type: application/json" -d '{"name": "Arbeitsvertrag"}'
curl -X POST http://localhost:8080/documents -H "Content-Type: application/json" -d '{"name": "Sicherheitsblatt"}'
curl -X POST http://localhost:8080/documents -H "Content-Type: application/json" -d '{"name": "Vertragsbedingungen"}'
curl -X POST http://localhost:8080/documents -H "Content-Type: application/json" -d '{"name": "Anleitung"}'
curl -X POST http://localhost:8080/documents -H "Content-Type: application/json" -d '{"name": "Rechnung"}'
curl -X POST http://localhost:8080/documents -H "Content-Type: application/json" -d '{"name": "Präsentation"}'
curl -X POST http://localhost:8080/documents -H "Content-Type: application/json" -d '{"name": "Bewerbungsunterlagen"}'
curl -X POST http://localhost:8080/documents -H "Content-Type: application/json" -d '{"name": "Angebotsvorlage"}'
curl -X POST http://localhost:8080/documents -H "Content-Type: application/json" -d '{"name": "Handbuch"}'
curl -X POST http://localhost:8080/documents -H "Content-Type: application/json" -d '{"name": "Testprotokoll"}'

# Categories
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Seefestspiele", "userIds": [1, 2]}'
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Theater", "userIds": [3, 4]}'
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Kunstausstellung", "userIds": [1, 3]}'
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Filmfestival", "userIds": [2, 4]}'
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Konzert", "userIds": [1, 4]}'
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Musical", "userIds": [2, 3]}'
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Oper", "userIds": [1, 2, 3, 4]}'
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Literatur", "userIds": [5, 6, 7, 8]}'
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Kabarett", "userIds": [9, 10]}'
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Comedy", "userIds": [5, 6]}'

echo "Data population completed."
