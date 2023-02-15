package com.nashss.se.budgetme.activity;

import com.nashss.se.budgetme.activity.requests.CreateExpenseRequest;
import com.nashss.se.budgetme.activity.results.CreateExpenseResult;
import com.nashss.se.budgetme.dynamodb.ExpenseDao;
import com.nashss.se.budgetme.dynamodb.models.Expense;
import com.nashss.se.budgetme.exceptions.InvalidAttributeValueException;
import com.nashss.se.budgetme.models.ExpenseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class CreateExpenseActivityTest {

    @Mock
    ExpenseDao expenseDao;

    CreateExpenseActivity createExpenseActivity;

    CreateExpenseRequest validRequest;

    @BeforeEach
    void setup() {
        openMocks(this);
        createExpenseActivity = new CreateExpenseActivity(expenseDao);
        validRequest = CreateExpenseRequest.builder()
                .withUserId("blah@mail.com")
                .withExpenseName("Power Bill")
                .withExpenseAmount("100")
                .withTag("Utilities")
                .withDate("2023-01-13")
                .build();
    }

    @Test
    void handleRequest_validAttributes_callsCreateExpense() {

        // WHEN
        createExpenseActivity.handleRequest(validRequest);

        // THEN
        verify(expenseDao).saveExpense(any());
    }

    @Test
    void handleRequest_validAttributes_returnsResult() {

        // WHEN
        CreateExpenseResult result = createExpenseActivity.handleRequest(validRequest);
        Expense expense = new Expense();
        expense.setUserId("blah@mail.com");
        expense.setExpenseId(result.getExpenseModel().getExpenseId());
        expense.setExpenseName("Power Bill");
        expense.setExpenseAmount("100");
        expense.setTag("Utilities");
        expense.setDate(LocalDate.of(2023, 01, 13));

        // THEN
        assertEquals(new ExpenseModel(expense), result.getExpenseModel());
    }

    @Test
    void handleRequest_invalidExpenseName_throwsException() {
        // GIVEN
        CreateExpenseRequest request = CreateExpenseRequest
                .builder()
                .withExpenseName("$Money")
                .withExpenseAmount("100")
                .withTag("Utilities")
                .withDate("2022-01-01")
                .build();

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> createExpenseActivity.handleRequest(request));
    }

    @Test
    void handleRequest_invalidExpenseAmount_throwsException() {
        // GIVEN
        CreateExpenseRequest request = CreateExpenseRequest
                .builder()
                .withExpenseName("Money")
                .withExpenseAmount("-100")
                .withTag("Loan")
                .withDate("2022-01-01")
                .build();

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> createExpenseActivity.handleRequest(request));
    }

    @Test
    void handleRequest_invalidTag_throwsException() {
        // GIVEN
        CreateExpenseRequest request = CreateExpenseRequest
                .builder()
                .withExpenseName("Money")
                .withExpenseAmount("100")
                .withTag("$Loan")
                .withDate("2022-01-01")
                .build();

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> createExpenseActivity.handleRequest(request));
    }
}
