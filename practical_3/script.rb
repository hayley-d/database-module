DB_CONT = "cos326_db"
USER    = "cos326"
DB      = "employeesDB"
PWDENV  = "-e PGPASSWORD=cos326pwd"

def sec(title); puts "\n\e[1;97m==> #{title}\e[0m"; end
def ok(msg);    puts "   \e[32m✔\e[0m #{msg}"; end
def info(msg);  puts "   \e[36mℹ\e[0m #{msg}"; end
def run!(cmd)
  puts "   $ #{cmd}"
  abort("Command failed: #{cmd}") unless system(cmd)
end
def sh(cmd)
  `#{cmd}`.strip
end

def wait_for_running(name, timeout: 60)
  sec "Waiting for container '#{name}' to be running"
  t0 = Time.now
  loop do
    state = sh(%(docker inspect -f "{{.State.Running}}" #{name} 2>/dev/null))
    break ok("Container is running") if state == "true"
    abort("Container not found") if state.empty?
    sleep 1
    abort("Timeout waiting for running") if Time.now - t0 > timeout
  end
end

def wait_for_healthy(name, timeout: 120)
  sec "Waiting for '#{name}' to be healthy"
  t0 = Time.now
  loop do
    health = sh(%(docker inspect -f "{{.State.Health.Status}}" #{name} 2>/dev/null))
    if health == "healthy"
      ok("Healthcheck passed")
      break
    elsif health.empty?
      info("No healthcheck found; proceeding")
      break
    else
      info("Current health: #{health}")
    end
    sleep 2
    abort("Timeout waiting for healthy") if Time.now - t0 > timeout
  end
end

wait_for_running(DB_CONT)
wait_for_healthy(DB_CONT)

sec "Creating schema (domains, types, sequences, tables, functions)"
run!(%(docker exec #{PWDENV} -i #{DB_CONT} psql -U #{USER} -d #{DB} -f /sql/CreateStatements.sql))
ok "Schema created"

sec "Inserting sample data"
run!(%(docker exec #{PWDENV} -i #{DB_CONT} psql -U #{USER} -d #{DB} -f /sql/InsertStatements.sql))
ok "Data inserted"

sec "Running report queries (Task 3)"
run!(%(docker exec #{PWDENV} -i #{DB_CONT} psql -U #{USER} -d #{DB} -f /sql/SelectStatements.sql))
ok "Reports complete"

puts "\n\e[1;96mDemo finished successfully.\e[0m"
