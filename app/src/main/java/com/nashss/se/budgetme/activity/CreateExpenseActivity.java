package com.nashss.se.budgetme.activity;
import com.nashss.se.budgetme.activity.requests.CreateExpenseRequest;
import com.nashss.se.budgetme.activity.results.CreateExpenseResult;
import com.nashss.se.budgetme.converters.DateConverter;
import com.nashss.se.budgetme.dynamodb.ExpenseDao;

import com.nashss.se.budgetme.dynamodb.models.Expense;
import com.nashss.se.budgetme.exceptions.InvalidAttributeValueException;
import com.nashss.se.budgetme.exceptions.MissingRequiredFieldException;
import com.nashss.se.budgetme.utils.ValidatorUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the CreateExpenseActivity for BudgetMe CreateExpense API.
 *
 * This API allows the user to create a new expense.
 */
public class CreateExpenseActivity {
    private final Logger log;

    private final ExpenseDao expenseDao;


    /**
     * Instantiates a new CreateExpenseActivity object.
     *
     * @param expenseDao ExpenseDao to access the expenditures table.
     */
    @Inject
    public CreateExpenseActivity(ExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
        log = LogManager.getLogger();
    }

    /**
     * This method handles the incoming request by creating an expense in the database.
     * <p>
     * It then returns the expense.
     * <p>
     * If expenseName, expenseAmount, or tag are invalid strings,
     * this should throw an InvalidAttributeValueException.
     *
     * @param request request object containing the expense's information
     * @return CreateExpenseResult result object containing the API defined {@link Expense}
     */
    public CreateExpenseResult handleRequest(CreateExpenseRequest request) {
        log.info("Received Create Expense Request {}", request);

        checkAttributes(request);

        DateConverter converter = new DateConverter();

        Expense expense = new Expense();
        expense.setUserId(request.getUserId());
        expense.setExpenseId(ValidatorUtils.generateId());
        expense.setExpenseName(request.getExpenseName());
        expense.setExpenseAmount(request.getExpenseAmount());
        expense.setTag(request.getTag());
        expense.setDate(converter.unconvert(request.getDate()));

        expenseDao.saveExpense(expense);

        return CreateExpenseResult.builder()
                .withExpense(expense)
                .build();
    }

    /**
     * Checks the important attributes of a CreateExpenseRequest for validity.
     * <p>
     * @param request the CreateExpenseRequest to check
     */
    private void checkAttributes(CreateExpenseRequest request) {
        if (request.getExpenseName() == null) {
            throw new MissingRequiredFieldException("expenseName is a required field.");
        }

        if (request.getExpenseAmount() == null) {
            throw new MissingRequiredFieldException("expenseAmount is a required field.");
        }

        if (request.getTag() == null) {
            throw new MissingRequiredFieldException("tag is a required field.");
        }

        if (!ValidatorUtils.isValidString(request.getExpenseName())) {
            throw new InvalidAttributeValueException("Expense name \"" +
                    request.getExpenseName() +
                    "\" contains invalid characters");
        }

        if (!ValidatorUtils.isValidNumber(request.getExpenseAmount())) {
            throw new InvalidAttributeValueException("Expense amount \"" +
                    request.getExpenseAmount() +
                    "\" contains invalid characters");
        }

        if (!ValidatorUtils.isValidString(request.getTag())) {
            throw new InvalidAttributeValueException("Tag \"" +
                    request.getTag() +
                    "\" contains invalid characters");
        }
    }
}
