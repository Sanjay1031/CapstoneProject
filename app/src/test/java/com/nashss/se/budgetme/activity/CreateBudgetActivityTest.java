package com.nashss.se.budgetme.activity;

import com.nashss.se.budgetme.activity.requests.CreateBudgetRequest;
import com.nashss.se.budgetme.activity.results.CreateBudgetResult;
import com.nashss.se.budgetme.dynamodb.BudgetDao;
import com.nashss.se.budgetme.dynamodb.models.Budget;
import com.nashss.se.budgetme.exceptions.InvalidAttributeValueException;
import com.nashss.se.budgetme.models.BudgetModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class CreateBudgetActivityTest {

    @Mock
    BudgetDao budgetDao;

    CreateBudgetActivity createBudgetActivity;

    CreateBudgetRequest validRequest;

    @BeforeEach
    void setup() {
        openMocks(this);
        createBudgetActivity = new CreateBudgetActivity(budgetDao);
        validRequest = CreateBudgetRequest.builder()
                .withUserId("blah@mail.com")
                .withTargetAmount("100")
                .withStatus(true)
                .withDate("2023-01-13")
                .build();
    }

    @Test
    void handleRequest_validAttributes_callsCreateBudget() {

        // WHEN
        createBudgetActivity.handleRequest(validRequest);

        // THEN
        verify(budgetDao).saveBudget(any());
    }

    @Test
    void handleRequest_validAttributes_returnsResult() {

        // WHEN
        CreateBudgetResult result = createBudgetActivity.handleRequest(validRequest);
        Budget budget = new Budget();
        budget.setUserId("blah@mail.com");
        budget.setBudgetId(result.getBudgetModel().getBudgetId());
        budget.setTargetAmount("100");
        budget.setStatus(true);
        budget.setDate(LocalDate.of(2023, 01, 13));

        // THEN
        assertEquals(new BudgetModel(budget), result.getBudgetModel());
    }


    @Test
    void handleRequest_invalidTargetAmount_throwsException() {
        // GIVEN
        CreateBudgetRequest request = CreateBudgetRequest
                .builder()
                .withTargetAmount("-100")
                .withDate("2022-01-01")
                .build();

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> createBudgetActivity.handleRequest(request));
    }
}
