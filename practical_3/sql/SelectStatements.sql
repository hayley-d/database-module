SELECT employee_id, employee_number, personFullNames(full_name) AS person_name, ageInYears(date_of_birth) AS age  FROM employee;
SELECT employee_id, employee_number, personFullNames(full_name) AS person_name, contract_code, year_hired, provincial_registration FROM full_time_employee;
SELECT employee_id, employee_number, personFullNames(full_name) AS person_name, contract_code, year_hired, personFullNames(mentor) AS mentor_name FROM part_time_employee;
SELECT employee_id, employee_number, personFullNames(full_name) AS person_name, contract_code, year_hired,  durationOfEmployment(year_hired) AS duration_of_employment  FROM full_time_employee;
SELECT employee_id, employee_number, personFullNames(full_name) AS person_name, contract_code, year_hired, provincial_registration FROM full_time_employee WHERE isLocatedAt(full_time_employee, 'NW');

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
