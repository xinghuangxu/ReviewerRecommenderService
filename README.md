# Code Reviewer Recommendation(C2R)
## Introduction
The tool is built to provide code reviewer recommendation as a service. 
It's built with Spring Boot. Please feel free to fork the project and enhance it. Thanks.

## Deployment
1. configure the application by modifying the configuration under resources/application.properties
1. run "mvn package" find the war file under target/
2. deploy the war file to your server (any serve who supports war file such as tomcat, glassfish would work)

## Maven Project
This is a maven spring project. You will need to install all the dependencies in the pom.xml file to run this project.
You can also package this project as a war file.

## Run the code
SpringBootWebApplication.class contains the main function to run the application.

## Configuring Spring Boot for MySQL
The configuration file is under resources/application.properties
This repository has the project files for a blog post about configuring Spring Boot to work with MySQL.