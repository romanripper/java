## To run project:

- Import as maven project
- Create schema in MySQL
- In spring-mvc-museum/src/main/resources/database.properties and spring-mvc-museum/src/main/resources/META-INF/persistence.xml change jdbc.url to match your schema, username and password
- Run spring-mvc-museum/src/main/java/ripper/populate_db/PopulateDbMain.java to create tables and populate the database
- Run project on server
