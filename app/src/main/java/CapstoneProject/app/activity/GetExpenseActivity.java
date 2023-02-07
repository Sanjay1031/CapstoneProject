package CapstoneProject.app.activity;

import CapstoneProject.app.activity.requests.GetExpenseRequest;
import CapstoneProject.app.activity.results.GetExpenseResult;
import CapstoneProject.app.dynamodb.ExpenseDao;
import CapstoneProject.app.dynamodb.models.Expense;
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
        String requestedId = getExpenseRequest.getId();
        Expense expense = expenseDao.getExpense(requestedId);


        return GetExpenseResult.builder()
                .withExpense(expense)
                .build();

    }
}
