# Java4-ServiceAuto-SpringVersion


The project is a small app that is dedicated to the auto service repairs business. The main purpose of the app is to create service orders for each car, containing the labor and the parts required for the repair of the car and generate the invoice at the end of the repair.

Story:

The idea behind the app is as follow : the client comes to the service auto repair shop with a problem at his car -> maintenante, body repair, mechanical repair etc.
The staff uses the app to create a service order by entering into the app the clients info and the main characteristics of the car. Once all the data is collected the service staff can create a service order which
Will be used to gather all the information about the cars repair -> labor and parts needed.
At the end of the repair the service order will be used to generate the invoice, that will be saved into the database.

The app is structured on modules that have specific roles:
1.	aop module: the role of this module is to log the different exceptions that the app generates and write them in .txt files, and allsow logs the user creating a new account, login and logout. The app generates the .txt files when this events occur.
2.	config module: contains the configuration files of the app -> security config, invoice pdf config, fileds validation config.
3.	domain module: containt the entities classes that are mapped at the database level (ORM)
4.	email module: responsible for generating and sending the email when a new user is created.
5.	error module: contains the exception classes that are generated by the app during running.
6.	invoice module: generates the pdf invoice.
7.	persistence module: contains the dao classes that are used for CRUD operations on the database. Contains the service classes that stores the business logic code of the app.
8.	security module: is responsible for the authentication, authorization logic of the user in the app, and activating the account when a new one is created.
9.	storage module: acts as a mini-cache
10.	utility module: stores some utility classes that are used in the app code.
11.	web module: here is the main class that runs the app. It allsow has the @Controller and @ControllerAdvice classes. Is stores the .html pages of the views in the resource folder. The .txt files generated by the aop module are stored in the resource folder.


Framework and dependecies used: Spring boot 2.4.3, Spring security, Spring web, Spring jpa, Spring aop,  Java mail sender, mysql-conector-j, thymeleaf, hibernate validator, lombok, JDK 15, mySQL database.

Project created in Intellij IDEA.
