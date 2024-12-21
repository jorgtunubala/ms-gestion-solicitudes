@echo off
set SPRING_SERVER_SOLICITUDES_PORT=8095
set SPRING_DATASOURCE_URL=jdbc:mysql://dockertest.unicauca.edu.co:4207/computacion
set SPRING_DATASOURCE_USERNAME=computacion
set SPRING_DATASOURCE_PASSWORD=DB-C0mput4c10N

mvn clean package