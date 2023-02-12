package com.nashss.se.budgetme.activity;

import com.nashss.se.budgetme.activity.requests.UpdateBudgetRequest;
import com.nashss.se.budgetme.activity.results.UpdateBudgetResult;
import com.nashss.se.budgetme.dynamodb.BudgetDao;
import com.nashss.se.budgetme.dynamodb.models.Budget;
import com.nashss.se.budgetme.exceptions.BudgetNotFoundException;
import com.nashss.se.budgetme.exceptions.InvalidAttributeChangeException;
import com.nashss.se.budgetme.exceptions.InvalidAttributeValueException;
import com.nashss.se.budgetme.metrics.MetricsConstants;
import com.nashss.se.budgetme.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class UpdateBudgetActivityTest {

    @Mock
    private BudgetDao budgetDao;

    @Mock
    private MetricsPublisher metricsPublisher;

    private UpdateBudgetActivity updateBudgetActivity;

    @BeforeEach
    public void setUp(){
        openMocks(this);
        updateBudgetActivity = new UpdateBudgetActivity(budgetDao, metricsPublisher);
    }

    @Test
    public void handleRequest_goodRequest_updatesTargetAmount() {
        // GIVEN
        String budgetId = "budgetId";
        String pathBudgetId = "budgetId";
        String expectedTargetAmount = "300";

        UpdateBudgetRequest request = UpdateBudgetRequest.builder()
                .withBudgetId(budgetId)
                .withTargetAmount(expectedTargetAmount)
                .build();
        request.setPathBudgetId(pathBudgetId);
        Budget startingBudget = new Budget();
        startingBudget.setTargetAmount("200");


        when(budgetDao.getBudget(budgetId)).thenReturn(startingBudget);

        // WHEN
        UpdateBudgetResult result = updateBudgetActivity.handleRequest(request);

        // THEN
        assertEquals(expectedTargetAmount, result.getBudgetModel().getTargetAmount());
    }

    @Test
    public void handleRequest_invalidTargetAmount_throwsInvalidAttributeValueException() {
        // GIVEN
        UpdateBudgetRequest request = UpdateBudgetRequest.builder()
                .withBudgetId("ID")
                .withTargetAmount("-100")
                .build();
        request.setPathBudgetId("ID");
        // WHEN + THEN
        try {
            updateBudgetActivity.handleRequest(request);
            fail("Expected InvalidAttributeValueException to be thrown");
        } catch (InvalidAttributeValueException e) {
            verify(metricsPublisher).addCount(MetricsConstants.UPDATEBUDGET_INVALIDATTRIBUTEVALUE_COUNT, 1);
        }

    }

    @Test
    public void handleRequest_budgetDoesNotExist_throwsBudgetNotFoundException() {
        // GIVEN
        String budgetId = "budgetId";
        String pathBudgetId = "budgetId";
        UpdateBudgetRequest request = UpdateBudgetRequest.builder()
                .withBudgetId(budgetId)
                .withTargetAmount("100")
                .build();
        request.setPathBudgetId(pathBudgetId);
        when(budgetDao.getBudget(budgetId)).thenThrow(new BudgetNotFoundException());

        // THEN
        assertThrows(BudgetNotFoundException.class, () -> updateBudgetActivity.handleRequest(request));
    }

    @Test
    public void handleRequest_budgetIDsDoNotMatch_throwsInvalidChangeException() {
        // GIVEN
        String budgetId = "bud";
        String pathBudgetId = "NotBud";
        UpdateBudgetRequest request = UpdateBudgetRequest.builder()
                .withBudgetId(budgetId)
                .withTargetAmount("222")
                .build();
        request.setPathBudgetId(pathBudgetId);

        // THEN
        assertThrows(InvalidAttributeChangeException.class, () -> updateBudgetActivity.handleRequest(request));

    }
}
