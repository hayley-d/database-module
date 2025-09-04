BEGIN;
  SAVEPOINT upd_fte_bad_contract;

  UPDATE full_time_employee
  SET contract_code = 'ZZZ 999'
  WHERE employee_number = '140010';

ROLLBACK TO SAVEPOINT upd_fte_bad_contract;
COMMIT;

BEGIN;
  SAVEPOINT upd_fte_bad_prov;

    UPDATE full_time_employee
    SET provincial_registration =  ARRAY['GC']::provincial_code[]
    WHERE employee_number = '140010'
    RETURNING 1

ROLLBACK TO SAVEPOINT upd_fte_bad_prov;
COMMIT;

BEGIN;
  SAVEPOINT upd_pte_bad_contract;

  UPDATE part_time_employee
  SET contract_code = 'XXX 123'
  WHERE employee_number = '121101';

ROLLBACK TO SAVEPOINT upd_pte_bad_contract;
COMMIT;