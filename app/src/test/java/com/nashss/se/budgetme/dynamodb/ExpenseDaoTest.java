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
//    @Test
//    void getExpense_expenseNotFound_throwsExpenseNotFoundException() {
//        // GIVEN
//        String userId = "blah@mail.com";
//        String nonexistentExpenseId = "NotReal";
//        when(dynamoDBMapper.load(Expense.class, nonexistentExpenseId)).thenReturn(null);
//
//        // WHEN + THEN
//        assertThrows(ExpenseNotFoundException.class, () -> expenseDao.getExpense(userId,nonexistentExpenseId));
//    }
//    @Test
//    void getExpense_withUserIdAndExpenseId_callsMapperWithPartitionKey() {
//        // GIVEN
//        String userId = "blah@mail.com";
//        String expenseID = "1";
//        Expense expense = new Expense();
//        expense.setUserId(userId);
//        expense.setExpenseId(expenseID);
//        expense.setExpenseName("expenseName");
//        expense.setExpenseAmount("expenseAmount");
//        when(dynamoDBMapper.load(Expense.class, userId)).thenReturn(expense);
//
//        // WHEN
//        Expense result = expenseDao.getExpense(userId, expenseID);
//
//        // THEN
//        assertNotNull(result);
//        verify(dynamoDBMapper).load(Expense.class, expenseID);
//        assertEquals(expense, result);
//    }

}
