package com.nashss.se.budgetme.activity;

import com.nashss.se.budgetme.activity.requests.GetAllExpensesRequest;
import com.nashss.se.budgetme.activity.results.GetAllExpensesResult;
import com.nashss.se.budgetme.dynamodb.ExpenseDao;
import com.nashss.se.budgetme.dynamodb.models.Expense;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class GetAllExpensesActivity {
    private final Logger log = LogManager.getLogger();

    private final ExpenseDao expenseDao;

    /**
     * Instantiates a new GetAllExpenseActivity object.
     *
     * @param expenseDao ExpenseDao to access the expenditures table.
     */
    @Inject
    public GetAllExpensesActivity(ExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
    }

    /**
     * This method handles the incoming request by retrieving the expenses from the database.
     * <p>
     * It then returns the all expenses.
     * <p>
     * If the expense does not exist, this should throw a ExpenseNotFoundException.
     *
     * @param getAllExpensesRequest request object containing the expense ID
     * @return getExpenseResult result object containing the API defined {@link Expense}
     */
    public GetAllExpensesResult handleRequest(final GetAllExpensesRequest getAllExpensesRequest) {
        log.info("Received GetExpensesRequest {}", getAllExpensesRequest);

        List<Expense> expenseList = expenseDao.getAllExpenses(getAllExpensesRequest.getUserId());

        return GetAllExpensesResult.builder()
                .withExpenseList(expenseList)
                .build();
    }
}
