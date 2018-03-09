# IdentityE2E
Unit tests for IdentityE2E
This projects consists of a service that will scan a given directory for files and reports back metadat for the files found.
It will also scan the directory for a type. 

Unit tests are provided which will scan a given directory for a file with Vehicle registrations. A data provider is used to iterate through the data and validate vehicle details from gov dvla vehicle query web site
Screen shots are saved to IdentityE2E/identity-e2e_tests/src/main/screenshots/
The data file used is located in IdentityE2E/identity-e2e_tests/src/main/testdata/GOV_DVLA_VehicleTestData.xlsx
