package com.nashss.se.budgetme.dynamodb;

import com.nashss.se.budgetme.dynamodb.models.Budget;
import com.nashss.se.budgetme.exceptions.BudgetNotFoundException;
import com.nashss.se.budgetme.metrics.MetricsPublisher;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class BudgetDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @Mock
    private MetricsPublisher metricsPublisher;


    private BudgetDao budgetDao;

    @BeforeEach
    public void setup() {
        openMocks(this);
        budgetDao = new BudgetDao(dynamoDBMapper, metricsPublisher);
    }

    @Test
    public void getBudget_withBudgetId_callsMapperWithPartitionKey() {
        // GIVEN
        String budgetId = "budgetId";
        when(dynamoDBMapper.load(Budget.class, budgetId)).thenReturn(new Budget());

        // WHEN
        Budget budget = budgetDao.getBudget(budgetId);

        // THEN
        assertNotNull(budget);
        verify(dynamoDBMapper).load(Budget.class, budgetId);

    }

    @Test
    public void getBudget_budgetIdNotFound_throwsBudgetNotFoundException() {
        // GIVEN
        String nonexistentBudgetId = "NotReal";
        when(dynamoDBMapper.load(Budget.class, nonexistentBudgetId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(BudgetNotFoundException.class, () -> budgetDao.getBudget(nonexistentBudgetId));
    }

}
