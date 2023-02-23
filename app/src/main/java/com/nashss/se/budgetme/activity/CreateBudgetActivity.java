package com.nashss.se.budgetme.activity;

import com.nashss.se.budgetme.activity.requests.CreateBudgetRequest;
import com.nashss.se.budgetme.activity.results.CreateBudgetResult;
import com.nashss.se.budgetme.converters.DateConverter;
import com.nashss.se.budgetme.dynamodb.BudgetDao;
import com.nashss.se.budgetme.dynamodb.models.Budget;
import com.nashss.se.budgetme.exceptions.InvalidAttributeValueException;
import com.nashss.se.budgetme.exceptions.MissingRequiredFieldException;
import com.nashss.se.budgetme.utils.ValidatorUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the CreateBudgetActivity for BudgetMe CreateBudget API.
 *
 * This API allows the user to create a new budget.
 */
public class CreateBudgetActivity {
    private final Logger log;

    private final BudgetDao budgetDao;


    /**
     * Instantiates a new CreateBudgetActivity object.
     *
     * @param budgetDao BudgetDao to access the expenditures table.
     */
    @Inject
    public CreateBudgetActivity(BudgetDao budgetDao) {
        this.budgetDao = budgetDao;
        log = LogManager.getLogger();
    }

    /**
     * This method handles the incoming request by creating an budget in the database.
     * <p>
     * It then returns the budget.
     * <p>
     * If targetAmount is an invalid number,
     * this should throw an InvalidAttributeValueException.
     *
     * @param request request object containing the budget's information
     * @return CreateBudgetResult result object containing the API defined {@link Budget}
     */
    public CreateBudgetResult handleRequest(CreateBudgetRequest request) {
        log.info("Received Create Budget Request {}", request);

        checkAttributes(request);

        DateConverter converter = new DateConverter();

        Budget budget = new Budget();
        budget.setUserId(request.getUserId());
        budget.setBudgetId(ValidatorUtils.generateId());
        budget.setTargetAmount(request.getTargetAmount());
        budget.setStatus(request.getStatus());
        budget.setDate(converter.unconvert(request.getDate()));

        budgetDao.saveBudget(budget);

        return CreateBudgetResult.builder()
                .withBudget(budget)
                .build();
    }

    /**
     * Checks the important attributes of a CreateBudgetRequest for validity.
     * <p>
     * This includes required fields and String validation for email.
     * </p>
     * @param request the CreateBudgetRequest to check
     */
    private void checkAttributes(CreateBudgetRequest request) {

        if (request.getTargetAmount() == null) {
            throw new MissingRequiredFieldException("targetAmount is a required field.");
        }

        if (!ValidatorUtils.isValidNumber(request.getTargetAmount())) {
            throw new InvalidAttributeValueException("Target amount \"" +
                    request.getTargetAmount() +
                    "\" contains invalid characters");
        }
        if (!ValidatorUtils.isValidEmail(request.getUserId())) {
            throw new InvalidAttributeValueException("User Id \"" +
                    request.getUserId() +
                    "\" contains invalid characters");
        }
    }
}
