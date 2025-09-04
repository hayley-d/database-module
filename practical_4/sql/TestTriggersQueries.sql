
  INSERT INTO full_time_employee (
    employee_number, full_name, date_of_birth, contract_code, year_hired, provincial_registration
  )
  VALUES (
    '900001',
    ROW('Mr','Test','InvalidContract')::person_name,
    DATE '1995-05-05',
    'ZZZ 999',  
    2020,
    ARRAY['GP']::provincial_code[]  
  );





  INSERT INTO full_time_employee (
    employee_number, full_name, date_of_birth, contract_code, year_hired, provincial_registration
  )
  VALUES (
    '900002',
    ROW('Ms','Test','BadProvCode')::person_name,
    DATE '1992-02-02',
    'ABC 123',   
    2019,
    ARRAY['GC']::provincial_code[] 
  );



  INSERT INTO part_time_employee (
    employee_number, full_name, date_of_birth, contract_code, year_hired, mentor
  )
  VALUES (
    '900003',
    ROW('Mr','Test','PTEInvalidContract')::person_name,
    DATE '1990-01-01',
    'XXX 123',  
    2018,
    ROW('Mrs','Mentor','Person')::person_name
  );

  UPDATE full_time_employee
  SET contract_code = 'ZZZ 999'
  WHERE employee_number = '140010';



    UPDATE full_time_employee
    SET provincial_registration =  ARRAY['GC']::provincial_code[]
    WHERE employee_number = '140010'
    RETURNING 1



  UPDATE part_time_employee
  SET contract_code = 'XXX 123'
  WHERE employee_number = '121101';


SELECT
  e.employee_number,
  personFullNames(e.full_name) AS full_name,
  ageInYears(e.date_of_birth) AS age,
  e.contract_code,
  e.year_hired
FROM full_time_employee AS e
WHERE isLocatedAt(e, 'NW'::provincial_code);

SELECT hasValidProvincialCodes(ARRAY(
  SELECT p.provincial_code FROM province p
)) AS all_known_should_be_true;

SELECT hasValidProvincialCodes(ARRAY['NW','GP']::provincial_code[]) AS should_be_true_if_both_exist;
SELECT hasDuplicateProvincialCodes(ARRAY['NW','NW']::provincial_code[]) AS dup_true;
SELECT hasDuplicateProvincialCodes(ARRAY['NW','GP','EC']::provincial_code[]) AS dup_false;




DELETE FROM full_time_employee WHERE employee_number = '140010';

SELECT employee_number, (full_name).title AS title, (full_name).first_name AS first_name,
       (full_name).last_name AS last_name, deleted_at, deleted_by
FROM deleted_full_time_employee
WHERE employee_number = '140010';






DELETE FROM part_time_employee WHERE employee_number = '900202';

SELECT employee_number, (full_name).title AS title, (full_name).first_name AS first_name,
       (full_name).last_name AS last_name, deleted_at, deleted_by
FROM deleted_part_time_employee
WHERE employee_number = '900202';

