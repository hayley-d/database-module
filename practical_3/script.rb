DB_CONT = "cos326_db"
USER    = "cos326"
DB      = "employeesDB"

def sec(title); puts "\n\e[1;97m==> #{title}\e[0m"; end
def ok(msg);    puts "   \e[32m✔\e[0m #{msg}"; end
def run(cmd)
  puts "   $ #{cmd}"
  success = system(cmd)
  raise "Command failed: #{cmd}" unless success
end

sec "Creating schema (domains, types, sequences, tables, functions)"
run %(docker exec -i #{DB_CONT} psql -U #{USER} -d #{DB} -f /docker-entrypoint-initdb.d/CreateStatements.sql)
ok  "Schema created"

sec "Inserting sample data"
run %(docker exec -i #{DB_CONT} psql -U #{USER} -d #{DB} -f /docker-entrypoint-initdb.d/InsertQueries.sql)
ok  "Data inserted"

sec "Running report queries (Task 3)"
run %(docker exec -i #{DB_CONT} psql -U #{USER} -d #{DB} -f /docker-entrypoint-initdb.d/SelectQueries.sql)
ok  "Reports complete"

puts "\n\e[1;96mDemo finished successfully.\e[0m"