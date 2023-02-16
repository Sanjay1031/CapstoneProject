package com.nashss.se.budgetme.activity;

import com.nashss.se.budgetme.activity.requests.GetAllExpensesRequest;
import com.nashss.se.budgetme.activity.results.GetAllExpensesResult;
import com.nashss.se.budgetme.dynamodb.ExpenseDao;
import com.nashss.se.budgetme.dynamodb.models.Expense;
import com.nashss.se.budgetme.models.ExpenseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetAllExpensesActivityTest {

    @Mock
    ExpenseDao expenseDao;

    GetAllExpensesActivity getAllExpensesActivity;

    @BeforeEach
    void setup() {
        openMocks(this);
        getAllExpensesActivity = new GetAllExpensesActivity(expenseDao);
    }

    @Test
    void handleRequest_expenseFound_expenseListReturnedInResult() {

        // GIVEN
        String userId = "blah@mail.com";

        Expense expense = new Expense();
        expense.setUserId(userId);
        expense.setExpenseId("00043");
        expense.setExpenseName("Food");
        expense.setExpenseAmount("100");
        expense.setTag("Food");

        GetAllExpensesRequest request =
                GetAllExpensesRequest.builder()
                        .withId(expense.getExpenseId())
                        .withUserId(expense.getUserId())
                        .build();

        List<Expense> expenseList = new ArrayList<>();
        expenseList.add(expense);

        when(expenseDao.getAllExpenses(userId)).thenReturn(expenseList);

        // WHEN
        GetAllExpensesResult result = getAllExpensesActivity.handleRequest(request);

        // THEN
        verify(expenseDao).getAllExpenses(userId);
        assertNotNull(result.getExpenseList());
        assertEquals(List.of(new ExpenseModel(expense)), result.getExpenseList());
    }
}
