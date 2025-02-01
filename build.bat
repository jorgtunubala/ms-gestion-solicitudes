@echo off
set SPRING_SERVER_SOLICITUDES_PORT=8095
set SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/proy_maestria
set SPRING_DATASOURCE_USERNAME=root
set SPRING_DATASOURCE_PASSWORD=root

mvn clean package