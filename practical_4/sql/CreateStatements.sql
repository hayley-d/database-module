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
CREATE TYPE provincial_code AS ENUM ('GP','MP','NW','FS','KZN','WC','EC','NC', 'LP', 'GC');

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
CREATE SEQUENCE empSeq START WITH 1001 INCREMENT BY 1;
CREATE SEQUENCE contractSeq START WITH 4001 INCREMENT BY 1;
CREATE SEQUENCE provSeq START WITH 5001 INCREMENT BY 1;

-- Base Employee
CREATE TABLE employee (
  employee_id BIGINT PRIMARY KEY DEFAULT nextval('empSeq'),
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
  contract_id BIGINT PRIMARY KEY DEFAULT nextval('contractSeq'),
  contract_code contract_code UNIQUE NOT NULL,
  contract_type contract_type NOT NULL,
  number_of_years SMALLINT NOT NULL CHECK (number_of_years > 0)
);

-- Province
CREATE TABLE province (
  province_id BIGINT PRIMARY KEY DEFAULT nextval('provSeq'),
  provincial_code provincial_code UNIQUE NOT NULL,
  provincial_name VARCHAR(50) NOT NULL,
  department VARCHAR(50) NOT NULL
);

-- Deleted tables
CREATE TABLE deleted_full_time_employee (LIKE full_time_employee INCLUDING DEFAULTS);
ALTER TABLE deleted_full_time_employee
  ADD COLUMN deleted_at timestamptz NOT NULL DEFAULT now(),
  ADD COLUMN deleted_by text;

CREATE TABLE deleted_part_time_employee (LIKE part_time_employee INCLUDING DEFAULTS);
ALTER TABLE deleted_part_time_employee
  ADD COLUMN deleted_at timestamptz NOT NULL DEFAULT now(),
  ADD COLUMN deleted_by text;

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

-- Functions
CREATE OR REPLACE FUNCTION personFullNames(p person_name)
RETURNS text AS $$
BEGIN
  RETURN (p).title::text || ' ' || (p).first_name || ' ' || (p).last_name;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION ageInYears(dob DATE)
RETURNS TEXT AS $$
BEGIN
  RETURN (EXTRACT(YEAR FROM age(current_date, dob))::int)::text || ' years';
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION isLocatedAt(fte full_time_employee, pcode provincial_code)
RETURNS BOOLEAN AS $$
BEGIN
  RETURN COALESCE(pcode = ANY(fte.provincial_registration), FALSE);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION durationOfEmployment(hire_year year)
RETURNS TEXT AS $$
BEGIN
  RETURN (EXTRACT(YEAR FROM current_date)::int - hire_year)::int || ' years';
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION employee_personFullNames(e employee)
RETURNS TEXT AS $$
BEGIN
  RETURN personFullNames(e.full_name);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION employee_ageInYears(e employee)
RETURNS TEXT AS $$
BEGIN
  RETURN ageInYears(e.date_of_birth);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION employee_durationOfEmployment(e employee)
RETURNS TEXT AS $$
BEGIN
   RETURN durationOfEmployment(e.year_hired);
END;
$$ LANGUAGE plpgsql;

CREATE INDEX idx_employee_emp_no ON employee(employee_number);
CREATE INDEX idx_employee_contract_code ON employee(contract_code);
CREATE INDEX idx_province_code ON province(provincial_code);

CREATE OR REPLACE FUNCTION isValidProvincialCode(p provincial_code)
RETURNS boolean  AS $$
DECLARE
  res boolean;
BEGIN
  SELECT EXISTS (SELECT 1 FROM province WHERE provincial_code = p) INTO res;
  RETURN res;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION hasValidProvincialCodes(arr provincial_code[])
RETURNS boolean AS $$
BEGIN
  SELECT NOT EXISTS (
    SELECT 1 FROM unnest(arr) c
    WHERE NOT EXISTS (SELECT 1 FROM province p WHERE p.provincial_code = c)
  );
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION hasDuplicateProvincialCodes(arr provincial_code[])
RETURNS boolean AS $$
BEGIN
  WITH t AS (SELECT unnest(arr) AS c)
  SELECT EXISTS (
    SELECT 1 FROM t GROUP BY c HAVING COUNT(*) > 1
  );
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION isValidEmployeeNumber(n employee_number)
RETURNS boolean AS $$
BEGIN
  SELECT EXISTS (SELECT 1 FROM employee e WHERE e.employee_number = n);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION app_deleted_by()
RETURNS text
LANGUAGE sql
AS $$ SELECT current_user $$;


CREATE OR REPLACE FUNCTION isLocatedAt(pcode text, provinces text[])
RETURNS boolean AS $$
DECLARE
  c text;
BEGIN
  IF array_length(provinces, 1) IS NULL THEN
    RETURN FALSE;
  END IF;

  FOREACH c IN ARRAY provinces LOOP
    IF c IS NOT NULL AND c = pcode THEN
      RETURN TRUE;
    END IF;
  END LOOP;

  RETURN FALSE;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION hasDuplicateProvincialCodes(arr provincial_code[])
RETURNS boolean AS $$
DECLARE 
  has_dup boolean;
BEGIN
  SELECT EXISTS (
    SELECT 1 FROM unnest(arr) AS c
    GROUP BY c
    HAVING COUNT(*) > 1
  )
  INTO has_dup;

  RETURN COALESCE(has_dup, FALSE);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION hasValidProvincialCodes(arr provincial_code[])
RETURNS boolean AS $$
DECLARE 
  ok boolean;
BEGIN
  SELECT NOT EXISTS (
    SELECT 1
    FROM unnest(arr) AS c
    WHERE NOT EXISTS (
      SELECT 1 FROM province p WHERE p.provincial_code = c
    )
  )
  INTO ok;

  RETURN COALESCE(ok, FALSE);
END;
$$ LANGUAGE plpgsql;

-- Trigger Functions
CREATE OR REPLACE FUNCTION validate_full_time_employee_provincial_registration()
RETURNS trigger AS $$
BEGIN
  IF NEW.provincial_registration IS NULL THEN
    RAISE EXCEPTION 'provincial_registration may not be NULL';
  END IF;

  IF hasDuplicateProvincialCodes(NEW.provincial_registration) THEN
    RAISE EXCEPTION 'Duplicate province code in provincial_registration';
  END IF;

  IF NOT hasValidProvincialCodes(NEW.provincial_registration) THEN
    RAISE EXCEPTION 'Unknown province code in provincial_registration';
  END IF;

  RETURN NEW;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trigger_check_full_time_employee_provincial_codes ON full_time_employee;

CREATE TRIGGER trigger_check_full_time_employee_provincial_codes
  BEFORE INSERT OR UPDATE ON full_time_employee
  FOR EACH ROW
  EXECUTE FUNCTION validate_full_time_employee_provincial_registration();

CREATE OR REPLACE FUNCTION enforce_employee_number_uniqueness()
RETURNS trigger AS $$
DECLARE
  dup boolean := false;
BEGIN
  IF NEW.employee_number IS NULL THEN
    RAISE EXCEPTION 'employee_number may not be NULL';
  END IF;

  PERFORM pg_advisory_xact_lock(hashtext(NEW.employee_number::text));

  SELECT EXISTS (
    SELECT 1 FROM ONLY employee e
    WHERE e.employee_number = NEW.employee_number
      AND (TG_TABLE_NAME <> 'employee' OR e.employee_id <> NEW.employee_id)
  ) OR EXISTS (
    SELECT 1 FROM full_time_employee f
    WHERE f.employee_number = NEW.employee_number
      AND (TG_TABLE_NAME <> 'full_time_employee' OR f.employee_id <> NEW.employee_id)
  ) OR EXISTS (
    SELECT 1 FROM part_time_employee p
    WHERE p.employee_number = NEW.employee_number
      AND (TG_TABLE_NAME <> 'part_time_employee' OR p.employee_id <> NEW.employee_id)
  )
  INTO dup;

  IF dup THEN
    RAISE EXCEPTION 'employee_number % already exists in the employee hierarchy', NEW.employee_number;
  END IF;

  RETURN NEW;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_employee_number_unique_emp ON employee;
DROP TRIGGER IF EXISTS trg_employee_number_unique_fte ON full_time_employee;
DROP TRIGGER IF EXISTS trg_employee_number_unique_pte ON part_time_employee;

CREATE TRIGGER trg_employee_number_unique_emp
  BEFORE INSERT OR UPDATE ON employee
  FOR EACH ROW EXECUTE FUNCTION enforce_employee_number_uniqueness();

CREATE TRIGGER trg_employee_number_unique_fte
  BEFORE INSERT OR UPDATE ON full_time_employee
  FOR EACH ROW EXECUTE FUNCTION enforce_employee_number_uniqueness();

CREATE TRIGGER trg_employee_number_unique_pte
  BEFORE INSERT OR UPDATE ON part_time_employee
  FOR EACH ROW EXECUTE FUNCTION enforce_employee_number_uniqueness();

CREATE INDEX IF NOT EXISTS idx_fte_emp_no ON full_time_employee(employee_number);
CREATE INDEX IF NOT EXISTS idx_pte_emp_no ON part_time_employee(employee_number);

CREATE OR REPLACE FUNCTION check_valid_employee_number_exists()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM employee e WHERE e.employee_number = NEW.employee_number
  ) THEN
    RAISE EXCEPTION 'Employee number % does not exist in employee table', NEW.employee_number;
  END IF;
  RETURN NEW;
END
$$;

DROP TRIGGER IF EXISTS check_valid_empno_fte ON full_time_employee;
CREATE TRIGGER check_valid_empno_fte
  AFTER INSERT OR UPDATE ON full_time_employee
  FOR EACH ROW EXECUTE FUNCTION check_valid_employee_number_exists();

DROP TRIGGER IF EXISTS check_valid_empno_pte ON part_time_employee;
CREATE TRIGGER check_valid_empno_pte
  AFTER INSERT OR UPDATE ON part_time_employee
  FOR EACH ROW EXECUTE FUNCTION check_valid_employee_number_exists();


CREATE OR REPLACE FUNCTION record_delete_fulltime()
RETURNS trigger AS $$
BEGIN
  INSERT INTO deleted_full_time_employee
  SELECT (OLD).*, now(), app_deleted_by();
  RETURN OLD;
END
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION record_delete_parttime()
RETURNS trigger AS $$
BEGIN
  INSERT INTO deleted_part_time_employee
  SELECT (OLD).*, now(), app_deleted_by();
  RETURN OLD;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS audit_delete_fulltime ON full_time_employee;
CREATE TRIGGER audit_delete_fulltime
  AFTER DELETE ON full_time_employee
  FOR EACH ROW EXECUTE FUNCTION record_delete_fulltime();

DROP TRIGGER IF EXISTS audit_delete_parttime ON part_time_employee;
CREATE TRIGGER audit_delete_parttime
  AFTER DELETE ON part_time_employee
  FOR EACH ROW EXECUTE FUNCTION record_delete_parttime();

CREATE OR REPLACE FUNCTION check_valid_contract_code()
RETURNS trigger AS $$
BEGIN
  IF NEW.contract_code IS NULL THEN
    RAISE EXCEPTION 'contract_code may not be NULL';
  END IF;
  IF NOT EXISTS (SELECT 1 FROM contract c WHERE c.contract_code = NEW.contract_code) THEN
    RAISE EXCEPTION 'Unknown contract_code: %', NEW.contract_code;
  END IF;
  RETURN NEW;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS check_valid_contract_fte ON full_time_employee;
CREATE TRIGGER check_valid_contract_fte
  BEFORE INSERT OR UPDATE ON full_time_employee
  FOR EACH ROW EXECUTE FUNCTION check_valid_contract_code();

DROP TRIGGER IF EXISTS check_valid_contract_pte ON part_time_employee;
CREATE TRIGGER check_valid_contract_pte
  BEFORE INSERT OR UPDATE ON part_time_employee
  FOR EACH ROW EXECUTE FUNCTION check_valid_contract_code();

  CREATE OR REPLACE FUNCTION check_valid_contract_on_contract()
RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
  -- Domain and UNIQUE already enforce correctness
  RETURN NEW;
END
$$;

DROP TRIGGER IF EXISTS check_valid_contract_contract ON contract;
CREATE TRIGGER check_valid_contract_contract
  BEFORE INSERT OR UPDATE ON contract
  FOR EACH ROW EXECUTE FUNCTION check_valid_contract_on_contract();
