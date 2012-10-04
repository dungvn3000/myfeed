cd linkerz_server
mvn clean install -P PRODUCTION -o
cd target
java -jar linkerz_server-0.0.1.jar
