package com.nashss.se.budgetme.activity;

import com.nashss.se.budgetme.activity.requests.GetExpenseRequest;
import com.nashss.se.budgetme.activity.results.GetExpenseResult;

import com.nashss.se.budgetme.dynamodb.ExpenseDao;
import com.nashss.se.budgetme.dynamodb.models.Expense;

import com.nashss.se.budgetme.models.ExpenseModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the GetExpenseActivity for BudgetMe GetExpense API.
 *
 * This API allows the user to get one of their saved expenditures.
 */
public class GetExpenseActivity {
    private final Logger log = LogManager.getLogger();
    private final ExpenseDao expenseDao;

    /**
     * Instantiates a new GetExpenseActivity object.
     *
     * @param expenseDao ExpenseDao to access the expenditures table.
     */
    @Inject
    public GetExpenseActivity(ExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
    }

    /**
     * This method handles the incoming request by retrieving the expense from the database.
     * <p>
     * It then returns the expense.
     * <p>
     * If the expense does not exist, this should throw a ExpenseNotFoundException.
     *
     * @param getExpenseRequest request object containing the expense ID
     * @return getExpenseResult result object containing the API defined {@link ExpenseModel}
     */
    public GetExpenseResult handleRequest(final GetExpenseRequest getExpenseRequest) {
        log.info("Received GetPlaylistRequest {}", getExpenseRequest);
        String requestedUserId = getExpenseRequest.getUserId();
        String requestedExpenseId = getExpenseRequest.getExpenseId();
        Expense expense = expenseDao.getExpense(requestedUserId, requestedExpenseId);


        return GetExpenseResult.builder()
                .withExpense(expense)
                .build();

    }
}
