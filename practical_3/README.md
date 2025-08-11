# Practical 3 Commands

# Start Docker & start demo script
```bash
docker compose up -d
ruby demo.rb 
```

# Run statements create 
```bash
docker exec -i cos326_db psql -U cos326 -d employeesDB -f /docker-entrypoint-initdb.d/CreateStatements.sql
```
# Run insert statements
```bash
docker exec -i cos326_db psql -U cos326 -d employeesDB -f /docker-entrypoint-initdb.d/InsertQueries.sql
```

# Run select statements
```bash
docker exec -i cos326_db psql -U cos326 -d employeesDB -f /docker-entrypoint-initdb.d/SelectQueries.sql
```

# Clean the DB and stop docker
```bash
docker compose down -v
docker compose up -d
```
