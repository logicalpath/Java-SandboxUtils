export FILEPATH=file:////Users/eddie.dickey/QESandbox/QE_Sandbox_Utils/tc.csv

// Scripts, Test CaseID, TestCase Description

CREATE CONSTRAINT ON (s:Script) ASSERT s.name IS UNIQUE;
CREATE CONSTRAINT ON (tc:TestCase) ASSERT tc.id IS UNIQUE;

USING PERIODIC COMMIT
LOAD CSV WITH HEADERS
FROM {FILEPATH} AS line
WITH line

MERGE (script:Script { name: line.`Eggplant Script Name` })
MERGE (testcase:TestCase { TestrailID: line.`Testrail Test Case ID`, TCDescription: line.`Test Case Description` })

CREATE (script)-[:Contains]->(testcase)
