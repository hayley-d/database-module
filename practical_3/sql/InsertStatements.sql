INSERT INTO contract (contract_code, contract_type, number_of_years) VALUES
('CAL 113', 'Full Time', 5),
('CAL 114', 'Part Time', 2);

INSERT INTO province (provincial_code, provincial_name, department) VALUES
('GP', 'Gauteng', 'Software Development'),
('WC', 'Western Cape', 'Auditing'),
('FS', 'Free State', 'Finance'),
('NW', 'North West', 'Human Resources');

INSERT INTO full_time_employee (
    employee_number,
    full_name,
    date_of_birth,
    contract_code,
    year_hired,
    provincial_registration
)
VALUES
    ('140010', ROW('Mr','Philipp','du Plessis'), '1999-01-10', 'CAL 113', 2010, ARRAY['NW']::provincial_code[]),
    ('140015', ROW('Miss','Hayley','Dodkins'), '1997-05-25', 'CAL 113', 2017, ARRAY['GP','NW']::provincial_code[]),
    ('131120', ROW('Mr','John','Doe'), '1997-01-30', 'CAL 113', 2020, ARRAY['FS']::provincial_code[]),
    ('131140', ROW('Ms','Jane','Doe'), '1998-02-20', 'CAL 113', 2023, ARRAY['WC']::provincial_code[]);

INSERT INTO part_time_employee (
    employee_number,
    full_name,
    date_of_birth,
    contract_code,
    year_hired,
    mentor
)
VALUES
    ('101122', ROW('Miss', 'Emma', 'Dodkins'), '2009-06-15', 'CAL 114', 2022, ROW('Mr','Rick', 'Sanchez')),
    ('121101', ROW('Mrs', 'Merkly', 'Slotherton'), '2007-04-27', 'CAL 114', 2021, ROW('Mr','Geralt', 'Rivia'));