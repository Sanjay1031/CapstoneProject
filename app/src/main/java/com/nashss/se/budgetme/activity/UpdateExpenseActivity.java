package com.nashss.se.budgetme.activity;

import com.nashss.se.budgetme.activity.requests.UpdateExpenseRequest;
import com.nashss.se.budgetme.activity.results.UpdateExpenseResult;
import com.nashss.se.budgetme.converters.DateConverter;
import com.nashss.se.budgetme.dynamodb.ExpenseDao;
import com.nashss.se.budgetme.dynamodb.models.Expense;
import com.nashss.se.budgetme.exceptions.InvalidAttributeChangeException;
import com.nashss.se.budgetme.exceptions.InvalidAttributeValueException;
import com.nashss.se.budgetme.metrics.MetricsConstants;
import com.nashss.se.budgetme.metrics.MetricsPublisher;
import com.nashss.se.budgetme.models.ExpenseModel;
import com.nashss.se.budgetme.utils.ValidatorUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class UpdateExpenseActivity {

    private final Logger log = LogManager.getLogger();

    private final ExpenseDao expenseDao;

    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a new UpdateExpenseActivity object.
     *
     * @param expenseDao      ExpenseDao to access the expenditures table.
     * @param metricsPublisher MetricsPublisher to publish metrics.
     */
    @Inject
    public UpdateExpenseActivity(ExpenseDao expenseDao, MetricsPublisher metricsPublisher) {
        this.expenseDao = expenseDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * This method handles the incoming request by retrieving the expense, updating it,
     * and persisting the expense.
     * <p>
     * It then returns the updated expense.
     * <p>
     * If the expense does not exist, this should throw a ExpenseNotFoundException.
     * <p>
     * If expenseName, expenseAmount, or tag are invalid strings, this should throw an InvalidAttributeValueException.
     * <p>
     *
     * @param updateExpenseRequest request object containing the expense ID, expense name, expenseAmount,
     *  and everything else associated with the expense
     * @return updateExpenseResult result object containing the API defined {@link ExpenseModel}
     */
    public UpdateExpenseResult handleRequest(final UpdateExpenseRequest updateExpenseRequest) {
        log.info("Received UpdateEmployeeRequest {}", updateExpenseRequest);

        checkAttributes(updateExpenseRequest);

        DateConverter converter = new DateConverter();

        Expense expense = expenseDao.getExpense(updateExpenseRequest.getUserId(), updateExpenseRequest.getExpenseId());
        if (updateExpenseRequest.getExpenseName() != null) {
            expense.setExpenseName(updateExpenseRequest.getExpenseName());
        }
        if (updateExpenseRequest.getExpenseAmount() != null) {
            expense.setExpenseAmount(updateExpenseRequest.getExpenseAmount());
        }
        if (updateExpenseRequest.getTag() != null) {
            expense.setTag(updateExpenseRequest.getTag());
        }
        if (updateExpenseRequest.getDate() != null) {
            expense.setDate(converter.unconvert(updateExpenseRequest.getDate()));
        }

        expenseDao.saveExpense(expense);
        publishExceptionMetrics(false);
        return UpdateExpenseResult.builder().withExpenseModel(expense).build();

    }

    /**
     * Helper method to publish exception metrics.
     * @param isInvalidAttributeValue indicates whether InvalidAttributeValueException is thrown
     */
    private void publishExceptionMetrics(final boolean isInvalidAttributeValue) {
        metricsPublisher.addCount(MetricsConstants.UPDATEBUDGET_INVALIDATTRIBUTEVALUE_COUNT,
                isInvalidAttributeValue ? 1 : 0);
    }

    private void checkAttributes(UpdateExpenseRequest request) {

        if (!ValidatorUtils.isValidString(request.getExpenseName()) &&
                request.getExpenseName() != null) {
            publishExceptionMetrics(true);
            throw new InvalidAttributeValueException("Expense expenseName [" +
                    request.getExpenseName() + "] contains illegal characters");
        }

        if (!ValidatorUtils.isValidNumber(request.getExpenseAmount()) &&
                request.getExpenseAmount() != null) {
            publishExceptionMetrics(true);
            throw new InvalidAttributeValueException("Expense expenseAmount [" +
                    request.getExpenseAmount() + "] contains illegal characters");
        }

        if (!ValidatorUtils.isValidString(request.getTag()) &&
                request.getTag() != null) {
            publishExceptionMetrics(true);
            throw new InvalidAttributeValueException("Expense tag [" +
                    request.getTag() + "] contains illegal characters");
        }
    }
}
