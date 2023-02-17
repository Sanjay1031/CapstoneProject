package com.nashss.se.budgetme.activity;

import com.nashss.se.budgetme.activity.requests.GetExpenseRequest;
import com.nashss.se.budgetme.activity.results.GetExpenseResult;
import com.nashss.se.budgetme.dynamodb.ExpenseDao;
import com.nashss.se.budgetme.dynamodb.models.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetExpenseActivityTest {
    @Mock
    private ExpenseDao expenseDao;

    private GetExpenseActivity getExpenseActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        getExpenseActivity = new GetExpenseActivity(expenseDao);
    }

    @Test
    public void handleRequest_savedExpenseFound_returnsExpenseModelInResult() {
        // GIVEN
        String expectedUserId = "blah@mail.com";
        String expectedExpenseId = "expectedId";
        String expectedName = "expectedName";
        String expectedAmount = "expectedAmount";
        String expectedTag = "expectedTag";

        GetExpenseRequest request = GetExpenseRequest.builder()
                .withUserId(expectedUserId)
                .withExpenseId(expectedExpenseId)
                .build();

        Expense expense = new Expense();
        expense.setExpenseId(expectedExpenseId);
        expense.setExpenseName(expectedName);
        expense.setExpenseAmount(expectedAmount);
        expense.setTag(expectedTag);

        when(expenseDao.getExpense(expectedUserId,expectedExpenseId)).thenReturn(expense);



        // WHEN
        GetExpenseResult result = getExpenseActivity.handleRequest(request);

        // THEN
        assertEquals(expectedExpenseId, result.getExpense().getExpenseId());
        assertEquals(expectedName, result.getExpense().getExpenseName());
        assertEquals(expectedAmount, result.getExpense().getExpenseAmount());
        assertEquals(expectedTag, result.getExpense().getTag());
    }
}
