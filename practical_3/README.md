# Run create + inserts
docker exec -i cos326_db psql -U cos326 -d employeesDB -f /docker-entrypoint-initdb.d/CreateStatements.sql
docker exec -i cos326_db psql -U cos326 -d employeesDB -f /docker-entrypoint-initdb.d/InsertQueries.sql

# For the demo queries (run them one-by-one so the output is visible)
docker exec -i cos326_db psql -U cos326 -d employeesDB -f /docker-entrypoint-initdb.d/SelectQueries.sql

docker compose up -d
ruby demo.rb

