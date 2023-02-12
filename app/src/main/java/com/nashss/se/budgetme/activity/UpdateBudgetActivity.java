package com.nashss.se.budgetme.activity;

import com.nashss.se.budgetme.activity.requests.UpdateBudgetRequest;
import com.nashss.se.budgetme.activity.results.UpdateBudgetResult;
import com.nashss.se.budgetme.converters.DateConverter;
import com.nashss.se.budgetme.dynamodb.BudgetDao;
import com.nashss.se.budgetme.dynamodb.models.Budget;
import com.nashss.se.budgetme.exceptions.InvalidAttributeChangeException;
import com.nashss.se.budgetme.exceptions.InvalidAttributeValueException;
import com.nashss.se.budgetme.metrics.MetricsConstants;
import com.nashss.se.budgetme.metrics.MetricsPublisher;
import com.nashss.se.budgetme.models.BudgetModel;
import com.nashss.se.budgetme.utils.ValidatorUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class UpdateBudgetActivity {

    private final Logger log = LogManager.getLogger();

    private final BudgetDao budgetDao;

    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a new UpdateBudgetActivity object.
     *
     * @param budgetDao      BudgetDao to access the budgets table.
     * @param metricsPublisher MetricsPublisher to publish metrics.
     */
    @Inject
    public UpdateBudgetActivity(BudgetDao budgetDao, MetricsPublisher metricsPublisher) {
        this.budgetDao = budgetDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * This method handles the incoming request by retrieving the budget, updating it,
     * and persisting the budget.
     * <p>
     * It then returns the updated budget.
     * <p>
     * If the budget does not exist, this should throw a BudgetNotFoundException.
     * <p>
     * If targetAmount is an invalid number, this should throw an InvalidAttributeValueException.
     * <p>
     *
     * @param updateBudgetRequest request object containing the budget ID, targetAmount,
     *  and everything else associated with the budget
     * @return updateBudgetResult result object containing the API defined {@link BudgetModel}
     */
    public UpdateBudgetResult handleRequest(final UpdateBudgetRequest updateBudgetRequest) {
        log.info("Received UpdateEmployeeRequest {}", updateBudgetRequest);

        checkAttributes(updateBudgetRequest);

        DateConverter converter = new DateConverter();

        Budget budget = budgetDao.getBudget(updateBudgetRequest.getPathBudgetId());

        if (updateBudgetRequest.getTargetAmount() != null) {
            budget.setTargetAmount(updateBudgetRequest.getTargetAmount());
        }
        if (updateBudgetRequest.getDate() != null) {
            budget.setDate(converter.unconvert(updateBudgetRequest.getDate()));
        }

        budgetDao.saveBudget(budget);
        publishExceptionMetrics(false);
        return UpdateBudgetResult.builder().withBudgetModel(budget).build();

    }

    /**
     * Helper method to publish exception metrics.
     * @param isInvalidAttributeValue indicates whether InvalidAttributeValueException is thrown
     */
    private void publishExceptionMetrics(final boolean isInvalidAttributeValue) {
        metricsPublisher.addCount(MetricsConstants.UPDATEBUDGET_INVALIDATTRIBUTEVALUE_COUNT,
                isInvalidAttributeValue ? 1 : 0);
    }

    private void checkAttributes(UpdateBudgetRequest request) {
        if (request.getBudgetId() != null &&
                !request.getBudgetId().equals(request.getPathBudgetId())) {
            throw new InvalidAttributeChangeException("Budget's ID can't be changed");
        }


        if (!ValidatorUtils.isValidNumber(request.getTargetAmount()) &&
                request.getTargetAmount() != null) {
            publishExceptionMetrics(true);
            throw new InvalidAttributeValueException("Budget targetAmount [" +
                    request.getTargetAmount() + "] contains illegal characters");
        }

    }
}
