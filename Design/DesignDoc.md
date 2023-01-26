# Design Document

## _AmazonBudgetService_ Design

## 1. Problem Statement

Amazon Elite Budget Service is a highly advanced budgeting app that will enable users to have more control over their money/expenditures.
Users can currently view their expenses by visiting the associated website/app for their card provider. However, users have requested that their expenditures and budget plans should be presented in a more streamlined, informative manner.

This design document describes the main use cases and functionality of Amazon Elite Budget Service, a new, native AWS service that will provide users a cleaner, more informative means to view expenditures and budget goals.
It is designed to interact with the Expense Management Client(which allows users to view expenditure information based on assigned tags and budget information based on their set goal).


## 2. Top Questions to Resolve in Review

_List the most important questions you have about your design, or things that you are still debating internally that you might like help working through._

1. Can we search expenses by date? 
2. Would date need to be a string rather than a ZoneDateTime
3. Payments through this API is not in scope
4. Does the budget model need more variables? 


## 3. Use Cases

_This is where we work backwards from the customer and define what our customers would like to do (and why). You may also include use cases for yourselves (as developers), or for the organization providing the product to customers._

U1. As a user, I want to be able to view all my expenditures

U2. As a user, I want to be able to view my expenditures based on their associated tag (EX. food, utilities, rent, personal, loan).

U3. As a user, I want to be able to create a new expenditure and then add the expense plus the associated tag (Reason for expense).
U4. As a user, I want to be able to update an expenditure’s information if changes arise.

U5. As a user, I want to be able to view a list of all my expenditures sorted by highest to lowest expense.

U6. As a user, I want to be able to view a list of all my expenditures sorted by date (newest - oldest)

U6. As a user, I want to be able to get a specific day’s worth of expenses.

U7. As a user, I want to be able to get a specific week’s worth of expenses.

U8. As a user, I want to be able to get a specific month’s worth of expenses.

U9. As a user, I want to be able to get a specific year’s worth of expenses.

U10. As a user, I want to be able to view a graph or chart that depicts my expenses for a certain period in an informative manner.



## 4. Project Scope

_Clarify which parts of the problem you intend to solve. It helps reviewers know what questions to ask to make sure you are solving for what you say and stops discussions from getting sidetracked by aspects you do not intend to handle in your design._



### 4.1. In Scope

_Which parts of the problem defined in Sections 1 and 2 will you solve with this design? This should include the base functionality of your product. What pieces are required for your product to work?_

* Adding, updating, and retrieving/viewing expenditure information
* User specific data (multiple users allowed)
* Retrieving expenditure information based on tags

### 4.2. Out of Scope

_Based on your problem description in Sections 1 and 2, are there any aspects you are not planning to solve? Do potential expansions or related problems occur to you that you want to explicitly say you are not worrying about now? Feel free to put anything here that you think your team can't accomplish in the unit, but would love to do with more time._

* This API does not link with any card provider or bank provider so
* This API does not facilitate payment. It is simply a means of retrieving data in a meaningful manner.




# 5. Proposed Architecture Overview

_Describe broadly how you are proposing to solve for the requirements you described in Section 2. This may include class diagram(s) showing what components you are planning to build. You should argue why this architecture (organization of components) is reasonable. That is, why it represents a good data flow and a good separation of concerns. Where applicable, argue why this architecture satisfies the stated requirements._

This initial iteration will provide the minimum viable product (MVP) including adding, retrieving, and updating an employee contact information for the admin role.

We will use API Gateway and Lambda to create 5 endpoints (GetExpense, Add/UpdateExpense, GetTaggedExpense, GetBudget, Add/UpdateBudget) that will handle the creation, updation, and retrieval of expense and budget information to satisfy our requirements.

We will store expense and budget information in a table in DynamoDB.

Amazon Elite Budget Service will also provide a web interface for users. A main page providing a variety of options to add, update and view expense and budget information.




# 6. API

## 6.1. Public Models

_Define the data models your service will expose in its responses via your *`-Model`* package. These will be equivalent to the *`PlaylistModel`* and *`SongModel`* from the Unit 3 project._








```
// ExpenseModel

String expenseId;
String expenseName;
String expenseAmount; 
String tag;
ZonedDateTime expenseDate;

// BudgetModel

String budgetId;
String targetAmount; 
ZonedDateTime date;

```

## 6.2. _GetExpense Endpoint_

* Accepts `GET` requests to `/expense/:date`
* Accepts an expenseID and returns the corresponding ExpenseModel.
    * If the given expenseID is not found, will throw a
      `ExpenditureNotFoundException`

## 6.3 _GetAllExpenses Endpoint_

* Accepts `GET` requests to `/expense`
* Returns all the expenses with any tag in the ExpenseModel format.
    * If there is no data found, will throw a
      `NoDataFoundException`

## 6.4. GetTaggedExpense Endpoint

* Accepts `GET` requests to `/employee/:tag`
* Returns all the expenses for the requested tag in the ExpenseModel format.
    * If there is no department found, will throw a
      `InvalidDepartmentException`

## 6.5. _UpdateExpense Endpoint_

* Accepts `PUT` requests to `/expense/:date`
* Accepts data to update an expense including an updated
  expenseName, expenseAmount, tag, and date. Returns the updated expense.
* If the expenseID or name is not found, will throw a   
  `ExpenseNotFoundException`
* For security concerns, we will validate the provided expense name does not
  contain invalid characters: `" ' \`
* If the expense name contains invalid characters, will throw an
  `InvalidAttributeValueException`

## 6.6. _AddExpense Endpoint_

* Accepts `POST` requests to `/expense/:date`
* Accepts data to create a new expenditure which includes the
  expenseId, expenseName, expenseAmount, tag, and date. Returns the new   
  expense.
* For security concerns, we will validate the provided expense name does not
  contain invalid characters: `" ' \`
* If the expense name contains invalid characters, will throw an
  `InvalidAttributeValueException`

## 6.7. _GetBudget Endpoint_

* Accepts `GET` requests to `/budget/:date`
* Accepts a date and returns the corresponding BudgetModel.
    * If the given date is not found, will throw a
      `BudgetNotFoundException`

## 6.6. _AddBudget Endpoint_

* Accepts `POST` requests to `/budget/:date`
* Accepts data to create a new budget which includes the
  budgetId, targetAmount,and date. Returns the new   
  budget.
* For security concerns, we will validate the provided budget name does not
  contain invalid characters: `" ' \`
* If the budget name contains invalid characters, will throw an
  `InvalidAttributeValueException`

## 6.5. _UpdateBudget Endpoint_

* Accepts `PUT` requests to `/budget/:date`
* Accepts data to update a budget including an updated
  targetAmount,and date. Returns the updated budget.
* If the budgetID is not found, will throw a   
  `BudgetNotFoundException`





# 7. Tables

_Define the DynamoDB tables you will need for the data your service will use. It may be helpful to first think of what objects your service will need, then translate that to a table structure, like with the *`Playlist` POJO* versus the `playlists` table in the Unit 3 project._

* ExpenseTable
  expenseId //  string
  expenseName // string
  expenseAmount // string
  tag // string (GSI Partition Key)
  Date // partition key, string

* BudgetTable
  budgetId //  string
  targetAmount // string
  Date // partition key, string



# 8. Pages

_Include mock-ups of the web pages you expect to build. These can be as sophisticated as mockups/wireframes using drawing software, or as simple as hand-drawn pictures that represent the key customer-facing components of the pages. It should be clear what the interactions will be on the page, especially where customers enter and submit data. You- may want to accompany the mockups with some description of behaviors of the page (e.g. “When customer submits the submit-dog-photo button, the customer is sent to the doggie detail page”)_


https://www.figma.com/file/16rgxBphwAFC0oQ11k2Fd2/Capstone-Project-Design-Board?node-id=0%3A1&t=eguOsCe5g57QWJl5-1



