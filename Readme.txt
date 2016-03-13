RESTful API for money transfers between internal users/accounts.

Application runs HTTP server which can accept user's requests for:
 - getting current balance of their account
 - transferring money to other accounts
 
Each user may have several accounts. Account contains amount of money in certain currency.
If money is transfered between accounts of different currencies, the conversion is performed.
Initial data for accounts and conversion rates is stored in xml file and loaded into memory on the startup.
Format of requests and responses between client and server sides is defined in the IDL files. Serialization/deserialization and arranging of http server are performed with the help of Apache Thrift. 
Server is able to handle concurrent user's requests.
Few checks are performed against transfer requests. Invalid requests are rejected with the descriptive reason.
Integration tests try to perform transfers in various ways: 
malformed requests; from the arbitrary account to all others; from arbitrary account to another arbitrary account; consequently; concurrently;

Code is compiled into moneytransferapp-1.0-SNAPSHOT-jar-with-dependencies.jar
In order to compile full application thrift compiler will be required. Thrift can be downloaded at https://thrift.apache.org/download. For windows it is pretty single exe file which needs to be accessible via system variable PATH.
In case if installation of thrift is better avoided the compilation results are submitted into VCS too.
Server can be run with the command
java -jar moneytransferapp-1.0-SNAPSHOT-jar-with-dependencies.jar
Code is covered with unit and integration tests.
Integration tests can be run with the command
mvn failsafe:integration-test
