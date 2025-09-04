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

# Pg Admin
Step 1:
```bash
http://localhost:5050
```

Username: 
```
admin@example.com
```
Password: 
```
admin
```

**Adding your Postgres server in pgAdmin Once logged in:**

1. Right-click “Servers” → Register → Server…

2. On the General tab:
   - Name: cos326_db (or whatever you like)

3. On the Connection tab:
    - Host name/address:
    - **Host:** db (service name in docker-compose.yml)
    - **Port:** 5432
    - **Username:** cos326
    - **Password:** cos326pwd

4. Click Save.


