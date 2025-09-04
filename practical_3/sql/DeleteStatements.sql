
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