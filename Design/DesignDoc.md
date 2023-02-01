# Design Document

## _BudgetMe_ App Design

## 1. Problem Statement

BudgetMe is a highly advanced budgeting/expense tracking app that will enable users to have more control over their money/expenditures.
Users can currently view their expenses by visiting the associated website/app for their card provider. However, users have requested that their expenditures and budget plans should be presented in a more streamlined, informative manner.

This design document describes the main use cases and functionality of BudgetMe, a new service that implements AWS technologies and will provide users a cleaner, more informative means to view expenditures and budget goals.
It is designed to interact with a front end component (which allows users to view expenditure information based on assigned tags and budget information based on their set goal).


## 2. Top Questions to Resolve in Review

1. Can we search expenses by date? 
2. Would date need to be a string rather than a ZoneDateTime?
3. Would status need to be string rather than boolean?
4. Does the budget model need more variables? 


## 3. Use Cases

U1. As a user, I want to be able to view all my expenditures

U2. As a user, I want to be able to view my expenditures based on their associated tag (EX. food, utilities, rent, personal, loan).

U3. As a user, I want to be able to create a new expenditure and then add the expense plus the associated tag (Reason for expense).

U4. As a user, I want to be able to update an expenditure’s information if changes arise.

U5. As a user, I want to be able to view a list of all my expenditures sorted by highest to lowest expense.

U6. As a user, I want to be able to view a list of all my expenditures sorted by date (newest - oldest)

U6. As a user, I want to be able to get expenses for a specified time period (day, week, month, and year).

U7. As a user, I want to be able to view an informative graph or chart that depicts my expenses for a certain time period. 

U8. As a user, I want to be able to create a new budget and then add a set target amount for a specified time period (day, week, month, and year). 

U9. As a user, I want to be able to update a budget's information if changes arise.

U10. As a user, I want to be able to view whether I have achieved my budget goal for set time period.

U11. As a user, I want to be able to view the remaining balance in my budget after my expenses for specified time period. 


## 4. Project Scope

### 4.1. In Scope

* Adding, updating, and retrieving/viewing expenditure information
* Adding, updating, and retrieving/viewing budget information
* User specific data (multiple users allowed)
* Retrieving expenditure information based on tags

### 4.2. Out of Scope

* Displaying expenditure and budget information in a graphical representation 
* Rolling over remaining budget information for the next term 
* Calculating balance remaining in budget after expenses  


# 5. Proposed Architecture Overview

This initial iteration will provide the minimum viable product (MVP) including adding, retrieving, and updating an expense information for the user role.

We will use API Gateway and Lambda to create 6 endpoints (GetExpense, Add/UpdateExpense, GetTaggedExpense, GetBudget, Add/UpdateBudget, BudgetStatus) that will handle the creation, update, and retrieval of expense and budget information to satisfy our requirements.

We will store expense and budget information in a table in DynamoDB.

BudgetMe will also provide a web interface for users. A main page providing a variety of options to add, update and view expense and budget information.


# 6. API

## 6.1. Public Models

```
// ExpenseModel

String expenseId; (Generated UUID or 5/6 digit unique code)
String expenseName;
String expenseAmount; 
String tag;
ZonedDateTime expenseDate;

// BudgetModel

String budgetId; (Generated UUID or 5/6 digit unique code)
String targetAmount; 
Boolean status; 
ZonedDateTime date;

```

## 6.2. _GetExpense Endpoint_

* Accepts `GET` requests to `/expense/:expenseId`
* Accepts an expenseID and returns the corresponding ExpenseModel.
    * If the given expenseID is not found, will throw a
      `ExpenditureNotFoundException`

## 6.3 _GetAllExpenses Endpoint_

* Accepts `GET` requests to `/expense`
* Returns all the expenses with any tag in the ExpenseModel format.
    * If there is no data found, will throw a
      `NoDataFoundException`

## 6.4. GetTaggedExpense Endpoint

* Accepts `GET` requests to `/expense/tags/:tag`
* Returns all the expenses for the requested tag in the ExpenseModel format.
    * If there is no department found, will throw a
      `InvalidDepartmentException`

## 6.5. _UpdateExpense Endpoint_

* Accepts `PUT` requests to `/expense/:expenseId`
* Accepts data to update an expense including an updated
  expenseName, expenseAmount, tag, and date. Returns the updated expense.
* Example Data Shape: {"expenseId" : "12345" ,"expenseName" : "Power","expenseAmount" : "100" ,"tag" : "Utilities" ,"date" : "01/23/23"} 
* If the expenseID or name is not found, will throw a   
  `ExpenseNotFoundException`
* For security concerns, we will validate the provided expense name does not
  contain invalid characters: `" ' \`
* If the expense name contains invalid characters, will throw an
  `InvalidAttributeValueException`

## 6.6. _AddExpense Endpoint_

* Accepts `POST` requests to `/expense/:expenseId`
* Accepts data to create a new expenditure which includes the
  expenseId, expenseName, expenseAmount, tag, and date. Returns the new   
  expense.
* Example Data Shape: {"expenseId" : "12345" ,"expenseName" : "Power","expenseAmount" : "100" ,"tag" : "Utilities" ,"date" : "01/23/23"}
* For security concerns, we will validate the provided expense name does not
  contain invalid characters: `" ' \`
* If the expense name contains invalid characters, will throw an
  `InvalidAttributeValueException`

## 6.7. _GetBudget Endpoint_

* Accepts `GET` requests to `/budget/:budgetId`
* Accepts a budgetId and returns the corresponding BudgetModel.
    * If the given date is not found, will throw a
      `BudgetNotFoundException`

## 6.6. _AddBudget Endpoint_

* Accepts `POST` requests to `/budget/:budgetId`
* Accepts data to create a new budget which includes the
  budgetId, targetAmount,and date. Returns the new   
  budget.
* Example Data Shape: {"budgetId" : "12345" ,"targetAmount" : "500" ,"status" : "true" ,"date" : "01/23/23"}
* For security concerns, we will validate the provided budget name does not
  contain invalid characters: `" ' \`
* If the budget name contains invalid characters, will throw an
  `InvalidAttributeValueException`

## 6.5. _UpdateBudget Endpoint_

* Accepts `PUT` requests to `/budget/:budgetId`
* Accepts data to update a budget including an updated
  targetAmount,and date. Returns the updated budget.
* Example Data Shape: {"budgetId" : "12345" ,"targetAmount" : "500" ,"status" : "true" ,"date" : "01/23/23"}
* If the budgetID is not found, will throw a   
  `BudgetNotFoundException`

## 6.6. _BudgetStatus Endpoint_

* Accepts `GET` requests to `/budget/:budgetId`
* Accepts a budgetId and returns the status (achieved/ not achieved) of specified budget 
  by querying the expenses and determining if it is less than or equal to the set budget.  
  * If the given date is not found, will throw a
    `BudgetNotFoundException`

    
# 7. Tables

* ExpenseTable
  expenseId // partition key, string
  expenseName // string
  expenseAmount // string
  tag // string (GSI Partition Key)
  date // string (GSI Partition Key)

* BudgetTable
  budgetId // partition key, string
  targetAmount // string
  date // string



# 8. Pages

https://www.figma.com/file/16rgxBphwAFC0oQ11k2Fd2/Capstone-Project-Design-Board?node-id=0%3A1&t=eguOsCe5g57QWJl5-1



