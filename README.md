# BudgetMe API

## Link to project: https://dauw3zvvzpoed.cloudfront.net

## Project Description 

* BudgetMe is a tool that enables users to have more control over their money/expenses. The app streamlines the management of expense information such as name, amount, category, and date.

## Features 
* Users can view all their expenses 
* Users can create an expense 
* Users can update an expense 
* Users can delete an expense 
* Users can view an individual expense 

## Technologies  

* BudgetMe incorporates AWS services/technologies such as CloudFormation, CloudFront, Lambda, Cognito, and DynamoDB. The project is set up using the cloudformation infrastructure, upon logging in through AWS Cognito users will interact with the frontend which is created through Javascript, HTML, and CSS. Based on the action the user requests, the request hits the API gateway and is transferred to the associated Lambda function. The action the user requests will ultimately be reflected in DyanmoDB. 

## Future Features 

* Include the ability to create, view, update, and delete a budget (Frontend code needs works. Backend code created and tested.)
* Implement a status indicator to notify users if they have met or gone over their set budget
* Include visual diagrams (graph or pie chart) of expense information 

## Design Details 

* For a detailed look at all the endpoints, tables, and proposed architectures, view the design doc: [Design Doc](https://github.com/Sanjay1031/CapstoneProject/blob/main/design/DesignDoc.md)
