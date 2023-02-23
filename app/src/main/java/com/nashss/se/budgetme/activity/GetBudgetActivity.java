package com.nashss.se.budgetme.activity;

import com.nashss.se.budgetme.activity.requests.GetBudgetRequest;
import com.nashss.se.budgetme.activity.results.GetBudgetResult;
import com.nashss.se.budgetme.dynamodb.BudgetDao;
import com.nashss.se.budgetme.dynamodb.models.Budget;
import com.nashss.se.budgetme.models.BudgetModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the GetBudgetActivity for BudgetMe GetBudget API.
 *
 * This API allows the user to get one of their saved expenditures.
 */
public class GetBudgetActivity {
    private final Logger log = LogManager.getLogger();
    private final BudgetDao budgetDao;

    /**
     * Instantiates a new GetBudgetActivity object.
     *
     * @param budgetDao budgetDao to access the expenditures table.
     */
    @Inject
    public GetBudgetActivity(BudgetDao budgetDao) {
        this.budgetDao = budgetDao;
    }

    /**
     * This method handles the incoming request by retrieving the budget from the database.
     * <p>
     * It then returns the budget.
     * <p>
     * If the budget does not exist, this should throw a budgetNotFoundException.
     *
     * @param getBudgetRequest request object containing the budget ID
     * @return getBudgetResult result object containing the API defined {@link BudgetModel}
     */
    public GetBudgetResult handleRequest(final GetBudgetRequest getBudgetRequest) {
        log.info("Received GetPlaylistRequest {}", getBudgetRequest);
        String requestedId = getBudgetRequest.getId();
        Budget budget = budgetDao.getBudget(requestedId);


        return GetBudgetResult.builder()
                .withBudget(budget)
                .build();

    }
}
