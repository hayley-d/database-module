-- 6 digit employee number
CREATE DOMAIN employee_number AS VARCHAR(6)
  NOT NULL
  CHECK (VALUE ~ '^[0-9]{6}$');

-- Year as YYYY
CREATE DOMAIN year AS SMALLINT 
  CHECK (VALUE BETWEEN 1900 AND 2100);

-- Contract code ABC 123
CREATE DOMAIN contract_code AS VARCHAR(7)
  CHECK (VALUE ~ '^[A-Z]{3} [0-9]{3}$');

-- Provincial code enum
CREATE TYPE provincial_code AS ENUM ('GP','MP','NW','FS','KZN','WC','EC','NC', 'LP');

-- Title enum
CREATE TYPE title AS ENUM ('Ms','Mev','Miss','Mrs','Mr','Mnr','Dr','Prof');

-- Contract type
CREATE TYPE contract_type AS ENUM ('Full Time','Part Time');

-- Name type 
CREATE TYPE person_name AS (
  title title, 
  first_name VARCHAR(50),
  last_name VARCHAR(50) 
);

-- Sequences
CREATE SEQUENCE seq_employee_id START WITH 1001 INCREMENT BY 1;
CREATE SEQUENCE seq_contract_id START WITH 4001 INCREMENT BY 1;
CREATE SEQUENCE seq_province_id START WITH 5001 INCREMENT BY 1;

-- Base Employee
CREATE TABLE employee (
  employee_id BIGINT PRIMARY KEY DEFAULT nextval('seq_employee_id'),
  employee_number employee_number UNIQUE,
  full_name person_name NOT NULL,
  date_of_birth DATE NOT NULL,
  contract_code contract_code NOT NULL,
  year_hired year NOT NULL,
  CHECK ( (full_name).title NOT IN ('Dr','Prof') )
);

-- Full-Time Employee is-a employee
CREATE TABLE full_time_employee (
  provincial_registration provincial_code ARRAY NOT NULL,
  PRIMARY KEY (employee_id)
) INHERITS (employee);

-- Part-Time Employee is-a employee
CREATE TABLE part_time_employee (
  mentor person_name NOT NULL,
  PRIMARY KEY (employee_id)
) INHERITS (employee);

-- Contract
CREATE TABLE contract (
  contract_id BIGINT PRIMARY KEY DEFAULT nextval('seq_contract_id'),
  contract_code contract_code UNIQUE NOT NULL,
  contract_type contract_type NOT NULL,
  number_of_years SMALLINT NOT NULL CHECK (number_of_years > 0)
);

-- Province
CREATE TABLE province (
  province_id BIGINT PRIMARY KEY DEFAULT nextval('seq_province_id'),
  provincial_code provincial_code UNIQUE NOT NULL,
  provincial_name VARCHAR(50) NOT NULL,
  department VARCHAR(50) NOT NULL
);

-- Link employee.contract_code to contract.contract_code
ALTER TABLE employee
  ADD CONSTRAINT fk_employee_contract
  FOREIGN KEY (contract_code)
  REFERENCES contract(contract_code)
  ON UPDATE CASCADE ON DELETE RESTRICT;

-- Trigger to validate each provincial code in the array exists in province
CREATE OR REPLACE FUNCTION check_prov_codes_exist() RETURNS trigger AS $$
DECLARE
  c provincial_code;
BEGIN
  IF NEW.provincial_registration IS NULL THEN
    RAISE EXCEPTION 'provincial_registration may not be NULL';
  END IF;

  FOREACH c IN ARRAY NEW.provincial_registration LOOP
    PERFORM 1 FROM province WHERE provincial_code = c;
    IF NOT FOUND THEN
      RAISE EXCEPTION 'Unknown province code: %', c;
    END IF;
  END LOOP;

  RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_fte_prov_codes
  BEFORE INSERT OR UPDATE ON full_time_employee
  FOR EACH ROW EXECUTE FUNCTION check_prov_codes_exist();

-- Functions
CREATE OR REPLACE FUNCTION personFullNames(p person_name)
RETURNS TEXT AS $$
  SELECT (p).title::TEXT || ' ' || (p).first_name || ' ' || (p).last_name;
$$ LANGUAGE sql;

CREATE OR REPLACE FUNCTION ageInYears(dob DATE)
RETURNS TEXT AS $$
  SELECT EXTRACT(YEAR FROM age(current_date, dob))::INT || ' years';
$$ LANGUAGE sql;

CREATE OR REPLACE FUNCTION isLocatedAt(fte full_time_employee, pcode provincial_code)
RETURNS BOOLEAN AS $$
  SELECT pcode = ANY(fte.provincial_registration);
$$ LANGUAGE sql;

CREATE OR REPLACE FUNCTION durationOfEmployment(hire_year year)
RETURNS TEXT AS $$
  SELECT (EXTRACT(YEAR FROM current_date)::INT - hire_year)::INT || ' years';
$$ LANGUAGE sql;

CREATE OR REPLACE FUNCTION employee_personFullNames(e employee)
RETURNS TEXT AS $$
  SELECT personFullNames(e.full_name);
$$ LANGUAGE sql;

CREATE OR REPLACE FUNCTION employee_ageInYears(e employee)
RETURNS TEXT AS $$
  SELECT ageInYears(e.date_of_birth);
$$ LANGUAGE sql;

CREATE OR REPLACE FUNCTION employee_durationOfEmployment(e employee)
RETURNS TEXT AS $$
  SELECT durationOfEmployment(e.year_hired);
$$ LANGUAGE sql;

CREATE INDEX idx_employee_emp_no ON employee(employee_number);
CREATE INDEX idx_employee_contract_code ON employee(contract_code);
CREATE INDEX idx_province_code ON province(provincial_code);


