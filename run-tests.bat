@echo off
set SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/maestriacomputacion
set SPRING_DATASOURCE_USERNAME=root
set SPRING_DATASOURCE_PASSWORD=mysql
set SPRING_MAIL_SMTP=smtp.gmail.com
set SPRING_MAIL_PORT=587
set SPRING_MAIL_USERNAME=maestriaprueba2024@gmail.com
set SPRING_MAIL_PASSWORD=qawl sbgr mawn dzkm
set SPRING_SERVER_SOLICITUDES_PORT=8095
set SPRING_OAUTH2_JWT_URI=https://dev-d0qrbj0szt6vmqvq.us.auth0.com/
set SPRING_OAUTH2_JWT_AUDIENCE=https://maestriacomputacion.com

mvn clean test
