package com.nashss.se.budgetme.activity;

import com.nashss.se.budgetme.activity.requests.GetBudgetRequest;
import com.nashss.se.budgetme.activity.results.GetBudgetResult;
import com.nashss.se.budgetme.dynamodb.BudgetDao;
import com.nashss.se.budgetme.dynamodb.models.Budget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetBudgetActivityTest {
    @Mock
    private BudgetDao budgetDao;

    private GetBudgetActivity getBudgetActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        getBudgetActivity = new GetBudgetActivity(budgetDao);
    }

    @Test
    public void handleRequest_savedBudgetFound_returnsBudgetModelInResult() {
        // GIVEN
        String expectedId = "expectedId";
        String expectedAmount = "expectedAmount";


        GetBudgetRequest request = GetBudgetRequest.builder()
                .withId(expectedId)
                .build();

        Budget budget = new Budget();
        budget.setBudgetId(expectedId);
        budget.setTargetAmount(expectedAmount);


        when(budgetDao.getBudget(expectedId)).thenReturn(budget);



        // WHEN
        GetBudgetResult result = getBudgetActivity.handleRequest(request);

        // THEN
        assertEquals(expectedId, result.getBudget().getBudgetId());
        assertEquals(expectedAmount, result.getBudget().getTargetAmount());
    }
}
