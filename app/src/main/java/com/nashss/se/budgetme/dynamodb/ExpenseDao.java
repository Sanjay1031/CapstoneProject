package com.nashss.se.budgetme.dynamodb;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.budgetme.dynamodb.models.Expense;
import com.nashss.se.budgetme.exceptions.ExpenseNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":v1", new AttributeValue().withS(userId));
        valueMap.put(":v2", new AttributeValue().withS(expenseId));

        DynamoDBQueryExpression<Expense> queryExpression = new DynamoDBQueryExpression<Expense>()
                .withKeyConditionExpression("userId = :v1 and expenseId = :v2")
                .withExpressionAttributeValues(valueMap);

        List<Expense> expense = dynamoDbMapper.query(Expense.class, queryExpression);


        if (expense == null) {
            throw new ExpenseNotFoundException(
                    String.format("Could not find expenditure with expenseId '%s'", expense));
        }

        return expense.get(0);
    }
    /**
     * Saves (creates or updates) the given expense.
     * @param expense The expense to save
     */
    public void saveExpense(Expense expense) {
        this.dynamoDbMapper.save(expense);
    }
}
