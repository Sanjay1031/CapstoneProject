package com.nashss.se.budgetme.dynamodb;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import com.nashss.se.budgetme.dynamodb.models.Expense;
import com.nashss.se.budgetme.exceptions.ExpenseNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Accesses data for an expense using {@link Expense} to represent the model in DynamoDB.
 */

public class ExpenseDao {

    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates an ExpenseDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the expenditures table
     */
    @Inject
    public ExpenseDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Retrieves an Expenditure by expenseId.
     *
     * If not found, throws AlbumTrackNotFoundException.
     *
     * @param expenseId The expenseId to look up
     * @return The corresponding expense if found
     */
    public Expense getExpense(String userId, String expenseId) {
        Expense expense = dynamoDbMapper.load(Expense.class, userId, expenseId);

        if (expense == null) {
            throw new ExpenseNotFoundException(
                    String.format("Could not find expenditure with expenseId '%s'", expense));
        }

        return expense;
    }

    public List<Expense> getAllExpenses(String userId) {
        Expense expense = new Expense();
        expense.setUserId(userId);
        DynamoDBQueryExpression<Expense> queryExpression = new DynamoDBQueryExpression<Expense>()
                .withHashKeyValues(expense);
        return dynamoDbMapper.query(Expense.class, queryExpression);

    }
    /**
     * Saves (creates or updates) the given expense.
     * @param expense The expense to save
     */
    public void saveExpense(Expense expense) {
        this.dynamoDbMapper.save(expense);
    }

    public void deleteExpense(String userId, String expenseId) {
        Expense expense = new Expense();
        expense.setUserId(userId);
        expense.setExpenseId(expenseId);
        dynamoDbMapper.delete(expense);
    }

}
