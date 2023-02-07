package CapstoneProject.app.activity;

import CapstoneProject.app.activity.requests.GetExpenseRequest;
import CapstoneProject.app.activity.results.GetExpenseResult;
import CapstoneProject.app.dynamodb.ExpenseDao;
import CapstoneProject.app.dynamodb.models.Expense;
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
        String expectedId = "expectedId";
        String expectedName = "expectedName";
        String expectedAmount = "expectedAmount";
        String expectedTag = "expectedTag";

        GetExpenseRequest request = GetExpenseRequest.builder()
                .withId(expectedId)
                .build();

        Expense expense = new Expense();
        expense.setExpenseId(expectedId);
        expense.setExpenseName(expectedName);
        expense.setExpenseAmount(expectedAmount);
        expense.setTag(expectedTag);

        when(expenseDao.getExpense(expectedId)).thenReturn(expense);



        // WHEN
        GetExpenseResult result = getExpenseActivity.handleRequest(request);

        // THEN
        assertEquals(expectedId, result.getExpense().getExpenseId());
        assertEquals(expectedName, result.getExpense().getExpenseName());
        assertEquals(expectedAmount, result.getExpense().getExpenseAmount());
        assertEquals(expectedTag, result.getExpense().getTag());
    }
}
