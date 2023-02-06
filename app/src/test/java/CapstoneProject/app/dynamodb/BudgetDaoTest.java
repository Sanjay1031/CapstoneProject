package CapstoneProject.app.dynamodb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
