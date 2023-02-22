package com.nashss.se.budgetme.activity;

import com.nashss.se.budgetme.activity.requests.DeleteExpenseRequest;
import com.nashss.se.budgetme.activity.results.DeleteExpenseResult;
import com.nashss.se.budgetme.dynamodb.ExpenseDao;
import com.nashss.se.budgetme.dynamodb.models.Expense;
import com.nashss.se.budgetme.exceptions.ExpenseNotFoundException;
import com.nashss.se.budgetme.models.ExpenseModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the DeleteExpenseActivity for BudgetMe DeleteExpense API.
 *
 * This API allows the user to get one of their saved expenditures.
 */
public class DeleteExpenseActivity {
    private final Logger log = LogManager.getLogger();

    private final ExpenseDao expenseDao;

    /**
     * Instantiates a new DeleteExpenseActivity object.
     *
     * @param expenseDao ExpenseDao to access the expenditures table.
     */
    @Inject
    public DeleteExpenseActivity(ExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
    }

    /**
     * This method handles the incoming request by delete the expense from the database.
     * <p>
     * It then returns the expense.
     * <p>
     * If the expense does not exist, this should throw a ExpenseNotFoundException.
     *
     * @param deleteExpenseRequest request object containing the expense ID
     * @return getExpenseResult result object containing the API defined {@link ExpenseModel}
     */
    public DeleteExpenseResult handleRequest(final DeleteExpenseRequest deleteExpenseRequest) {
        log.info("Received GetPlaylistRequest {}", deleteExpenseRequest);

        if (expenseDao.getExpense(deleteExpenseRequest.getUserId(), deleteExpenseRequest.getExpenseId()) == null) {
            throw new ExpenseNotFoundException();
        }

        Expense expense = expenseDao.getExpense(deleteExpenseRequest.getUserId(), deleteExpenseRequest.getExpenseId());

        expenseDao.deleteExpense(expense);

        return DeleteExpenseResult.builder()
                .withExpense(expense)
                .build();

    }
}
