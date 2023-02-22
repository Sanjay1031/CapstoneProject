package com.nashss.se.budgetme.activity;

import com.nashss.se.budgetme.activity.requests.UpdateExpenseRequest;
import com.nashss.se.budgetme.activity.results.UpdateExpenseResult;
import com.nashss.se.budgetme.dynamodb.ExpenseDao;
import com.nashss.se.budgetme.dynamodb.models.Expense;
import com.nashss.se.budgetme.exceptions.ExpenseNotFoundException;
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

public class UpdateExpenseActivityTest {

    @Mock
    private ExpenseDao expenseDao;

    @Mock
    private MetricsPublisher metricsPublisher;

    private UpdateExpenseActivity updateExpenseActivity;

    @BeforeEach
    public void setUp(){
        openMocks(this);
        updateExpenseActivity = new UpdateExpenseActivity(expenseDao, metricsPublisher);
    }

    @Test
    public void handleRequest_goodRequest_updatesExpenseName() {
        // GIVEN
        String userId = "blah@mail.com";
        String expenseId = "expenseId";
        String expectedExpenseName = "newName";

        UpdateExpenseRequest request = UpdateExpenseRequest.builder()
                .withUserId(userId)
                .withExpenseId(expenseId)
                .withExpenseName(expectedExpenseName)
                .withExpenseAmount("300")
                .withTag("tag")
                .build();
        Expense startingExpense = new Expense();
        startingExpense.setExpenseName("oldName");


        when(expenseDao.getExpense(userId, expenseId)).thenReturn(startingExpense);

        // WHEN
        UpdateExpenseResult result = updateExpenseActivity.handleRequest(request);

        // THEN
        assertEquals(expectedExpenseName, result.getExpenseModel().getExpenseName());
    }

    @Test
    public void handleRequest_goodRequest_updatesTagName() {
        // GIVEN
        String userId = "blah@mail.com";
        String expenseId = "expenseId";
        String expectedTag = "newTag";

        UpdateExpenseRequest request = UpdateExpenseRequest.builder()
                .withUserId(userId)
                .withExpenseId(expenseId)
                .withExpenseName("expenseName")
                .withExpenseAmount("200")
                .withTag(expectedTag)
                .build();
        Expense startingExpense = new Expense();
        startingExpense.setTag("oldTag");


        when(expenseDao.getExpense(userId,expenseId)).thenReturn(startingExpense);

        // WHEN
        UpdateExpenseResult result = updateExpenseActivity.handleRequest(request);

        // THEN
        assertEquals(expectedTag, result.getExpenseModel().getTag());

    }

    @Test
    public void handleRequest_invalidExpenseName_throwsInvalidAttributeValueException() {
        // GIVEN
        UpdateExpenseRequest request = UpdateExpenseRequest.builder()
                .withExpenseId("ID")
                .withExpenseName("$$$$$$")
                .withExpenseAmount("100")
                .withTag("tag")
                .build();
        // WHEN + THEN
        try {
            updateExpenseActivity.handleRequest(request);
            fail("Expected InvalidAttributeValueException to be thrown");
        } catch (InvalidAttributeValueException e) {
            verify(metricsPublisher).addCount(MetricsConstants.UPDATEBUDGET_INVALIDATTRIBUTEVALUE_COUNT, 1);
        }

    }

    @Test
    public void handleRequest_expenseDoesNotExist_throwsExpenseNotFoundException() {
        // GIVEN
        String userId = "blah@mail.com";
        String expenseId = "expenseId";
        String pathExpenseId = "expenseId";
        UpdateExpenseRequest request = UpdateExpenseRequest.builder()
                .withUserId(userId)
                .withExpenseId(expenseId)
                .withExpenseName("firstName")
                .withExpenseAmount("100")
                .withTag("tag")
                .build();
        when(expenseDao.getExpense(userId, expenseId)).thenThrow(new ExpenseNotFoundException());

        // THEN
        assertThrows(ExpenseNotFoundException.class, () -> updateExpenseActivity.handleRequest(request));
    }

}
