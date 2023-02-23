package com.nashss.se.budgetme.dynamodb;

import com.nashss.se.budgetme.dynamodb.models.Budget;
import com.nashss.se.budgetme.exceptions.BudgetNotFoundException;
import com.nashss.se.budgetme.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for a budget using {@link Budget} to represent the model in DynamoDB.
 */
@Singleton
public class BudgetDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a BudgetDao object.
     *
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the playlists table
     * @param metricsPublisher the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public BudgetDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Returns the {@link Budget} corresponding to the specified budgetId.
     *
     * @param budgetId the Budget ID
     * @return the stored Budget, or null if none was found.
     */
    public Budget getBudget(String budgetId) {
        Budget budget = this.dynamoDbMapper.load(Budget.class, budgetId);

        if (budget == null) {
            throw new BudgetNotFoundException("Could not find budget with id " + budgetId);
        }
        return budget;
    }

    /**
     * Saves (creates or updates) the given budget.
     *
     * @param budget The budget to save
     */
    public void saveBudget(Budget budget) {
        this.dynamoDbMapper.save(budget);

    }
}
