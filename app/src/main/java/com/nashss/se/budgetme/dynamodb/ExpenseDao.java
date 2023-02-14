package com.nashss.se.budgetme.dynamodb;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.nashss.se.budgetme.dynamodb.models.Expense;
import com.nashss.se.budgetme.exceptions.ExpenseNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Accesses data for an expense using {@link Expense} to represent the model in DynamoDB.
 */

public class ExpenseDao {
    private DynamoDBMapper dynamoDbMapper;

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
    public Expense getExpense(String expenseId) {
        Expense expense = dynamoDbMapper.load(Expense.class, expenseId);
        if (expense == null) {
            throw new ExpenseNotFoundException(
                    String.format("Could not find expenditure with expenseId '%s'", expense));
        }

        return expense;
    }

    public List<Expense> getAllExpenses(String userId) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return dynamoDbMapper.scan(Expense.class, scanExpression);
    }
    /**
     * Saves (creates or updates) the given expense.
     * @param expense The expense to save
     */
    public void saveExpense(Expense expense) {
        this.dynamoDbMapper.save(expense);
    }
}
