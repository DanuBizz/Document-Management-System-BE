#!/bin/bash

# Create Users
echo "Creating users..."
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "Mike Smith", "email": "mike.smith@example.com", "isAdmin": false}'
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "Luigi Green", "email": "luigi.green@example.com", "isAdmin": true}'
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "Sonic Blue", "email": "sonic.blue@example.com", "isAdmin": false}'
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "Peach Princess", "email": "peach.princess@example.com", "isAdmin": true}'
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "Mario Mario", "email": "mario.mario@example.com", "isAdmin": false}'
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "Zelda Hyrule", "email": "zelda.hyrule@example.com", "isAdmin": true}'
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "Link Hero", "email": "link.hero@example.com", "isAdmin": false}'
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "Samus Aran", "email": "samus.aran@example.com", "isAdmin": true}'
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "Kirby Puff", "email": "kirby.puff@example.com", "isAdmin": false}'
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "Donkey Kong", "email": "donkey.kong@example.com", "isAdmin": true}'

# Create Groups
echo "Creating groups..."
curl -X POST http://localhost:8080/groups -H "Content-Type: application/json" -d '{"name": "Adventure Squad", "usernames": ["Mike Smith", "Luigi Green"]}'
curl -X POST http://localhost:8080/groups -H "Content-Type: application/json" -d '{"name": "Speed Demons", "usernames": ["Sonic Blue", "Peach Princess"]}'
curl -X POST http://localhost:8080/groups -H "Content-Type: application/json" -d '{"name": "Heroic Team", "usernames": ["Mario Mario", "Zelda Hyrule"]}'
curl -X POST http://localhost:8080/groups -H "Content-Type: application/json" -d '{"name": "Brave Warriors", "usernames": ["Link Hero", "Samus Aran"]}'
curl -X POST http://localhost:8080/groups -H "Content-Type: application/json" -d '{"name": "Power Pack", "usernames": ["Kirby Puff", "Donkey Kong"]}'
curl -X POST http://localhost:8080/groups -H "Content-Type: application/json" -d '{"name": "Dynamic Duo", "usernames": ["Mike Smith", "Sonic Blue"]}'
curl -X POST http://localhost:8080/groups -H "Content-Type: application/json" -d '{"name": "Royal Guard", "usernames": ["Luigi Green", "Peach Princess"]}'
curl -X POST http://localhost:8080/groups -H "Content-Type: application/json" -d '{"name": "Legendary Heroes", "usernames": ["Mario Mario", "Link Hero"]}'
curl -X POST http://localhost:8080/groups -H "Content-Type: application/json" -d '{"name": "Ultimate Fighters", "usernames": ["Zelda Hyrule", "Samus Aran"]}'
curl -X POST http://localhost:8080/groups -H "Content-Type: application/json" -d '{"name": "Mighty Duo", "usernames": ["Kirby Puff", "Donkey Kong"]}'

# Create Categories
echo "Creating categories..."
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Seefestspiele", "groupNames": ["Adventure Squad", "Speed Demons"]}'
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Theater", "groupNames": ["Heroic Team", "Brave Warriors"]}'
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Kunstausstellung", "groupNames": ["Adventure Squad", "Heroic Team"]}'
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Filmfestival", "groupNames": ["Speed Demons", "Brave Warriors"]}'
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Konzert", "groupNames": ["Adventure Squad", "Brave Warriors"]}'
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Musical", "groupNames": ["Speed Demons", "Heroic Team"]}'
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Oper", "groupNames": ["Adventure Squad", "Speed Demons", "Heroic Team", "Brave Warriors"]}'
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Literatur", "groupNames": ["Power Pack", "Dynamic Duo", "Royal Guard", "Legendary Heroes"]}'
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Kabarett", "groupNames": ["Ultimate Fighters", "Mighty Duo"]}'
curl -X POST http://localhost:8080/categories -H "Content-Type: application/json" -d '{"name": "Comedy", "groupNames": ["Power Pack", "Dynamic Duo"]}'

# Create Documents
echo "Creating documents..."
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

echo "Data population completed."
