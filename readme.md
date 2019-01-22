Code to be added in application.properties file for H2 connection.

spring.h2.console.enabled=true
spring.datasource.platform=h2
spring.datasource.url=jdbc:h2:~/test
spring.datasource.username=sa
spring.datasource.password=
pring.datasource.driverClassName=org.h2.Driver
pring.jpa.hibernate.ddl-auto = create
spring.jpa.show-sql=true
spring.main.allow-bean-definition-overriding=true