package com.nashss.se.budgetme.dynamodb;

import com.nashss.se.budgetme.dynamodb.models.Expense;
import com.nashss.se.budgetme.exceptions.ExpenseNotFoundException;
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
        String nonexistentUserId = "notreal@mail.com";
        String nonexistentExpenseId = "NotReal";
        when(dynamoDBMapper.load(Expense.class, nonexistentExpenseId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(ExpenseNotFoundException.class, () -> expenseDao.getExpense(nonexistentUserId,nonexistentExpenseId));
    }
    @Test
    void getExpense_withExpenseId_callsMapperWithPartitionKey() {
        // GIVEN
        String userId = "user@mail.com";
        String expenseID = "1";
        Expense expense = new Expense();
        expense.setUserId(userId);
        expense.setExpenseId(expenseID);
        expense.setExpenseName("expenseName");
        expense.setExpenseAmount("expenseAmount");
        when(dynamoDBMapper.load(Expense.class, userId, expenseID)).thenReturn(expense);


        // WHEN
        Expense result = expenseDao.getExpense(userId,expenseID);

        // THEN
        assertNotNull(result);
        verify(dynamoDBMapper).load(Expense.class, userId, expenseID);
        assertEquals(expense, result);
    }

    @Test
    void deleteExpense_withUserIdAndExpenseId_deletesExpense() {
        // GIVEN
        String userId = "blah@mail.com";
        String expenseID = "1";
        Expense expense = new Expense();
        expense.setUserId(userId);
        expense.setExpenseId(expenseID);
        expense.setExpenseName("expenseName");
        expense.setExpenseAmount("expenseAmount");
        when(dynamoDBMapper.load(Expense.class, userId, expenseID)).thenReturn(expense);

        // WHEN
        expenseDao.deleteExpense(userId, expenseID);

        // THEN
        verify(dynamoDBMapper).delete(expense);
    }

}
