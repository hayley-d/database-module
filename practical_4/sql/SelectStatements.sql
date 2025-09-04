SELECT employee_id, employee_number, personFullNames(full_name) AS person_name, ageInYears(date_of_birth) AS age  FROM employee;
SELECT employee_id, employee_number, personFullNames(full_name) AS person_name, contract_code, year_hired, provincial_registration FROM full_time_employee;
SELECT employee_id, employee_number, personFullNames(full_name) AS person_name, contract_code, year_hired, personFullNames(mentor) AS mentor_name FROM part_time_employee;
SELECT employee_id, employee_number, personFullNames(full_name) AS person_name, contract_code, year_hired,  durationOfEmployment(year_hired) AS duration_of_employment  FROM full_time_employee;
SELECT employee_id, employee_number, personFullNames(full_name) AS person_name, contract_code, year_hired, provincial_registration FROM full_time_employee WHERE isLocatedAt(full_time_employee, 'NW');


