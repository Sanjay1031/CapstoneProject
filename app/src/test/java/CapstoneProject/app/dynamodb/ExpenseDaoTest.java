package CapstoneProject.app.dynamodb;

import CapstoneProject.app.dynamodb.models.Expense;
import CapstoneProject.app.exceptions.ExpenseNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;


public class ExpenseDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;

    private ExpenseDao expenseDao;

    @BeforeEach
    public void setup() {
        openMocks(this);
        expenseDao = new ExpenseDao(dynamoDBMapper);
    }
    @Test
    void getExpense_expenseNotFound_throwsExpenseNotFoundException() {
        // GIVEN
        String nonexistentExpenseId = "NotReal";
        when(dynamoDBMapper.load(Expense.class, nonexistentExpenseId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(ExpenseNotFoundException.class, () -> expenseDao.getExpense(nonexistentExpenseId));
    }
    @Test
    void getExpense_withExpenseId_callsMapperWithPartitionKey() {
        // GIVEN
        String expenseID = "1";
        Expense expense = new Expense();
        expense.setExpenseId(expenseID);
        expense.setExpenseName("expenseName");
        expense.setExpenseAmount("expenseAmount");
        when(dynamoDBMapper.load(Expense.class, expenseID)).thenReturn(expense);

        // WHEN
        Expense result = expenseDao.getExpense(expenseID);

        // THEN
        assertNotNull(result);
        verify(dynamoDBMapper).load(Expense.class, expenseID);
        assertEquals(expense, result);
    }

}