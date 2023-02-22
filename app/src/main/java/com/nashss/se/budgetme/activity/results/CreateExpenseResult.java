package com.nashss.se.budgetme.activity.results;

import com.nashss.se.budgetme.dynamodb.models.Expense;
import com.nashss.se.budgetme.models.ExpenseModel;

/**
 * Result class to handle a CreateExpenseResult.
 */
public class CreateExpenseResult {
    private final ExpenseModel expenseModel;

    private CreateExpenseResult(Expense expense) {
        this.expenseModel = new ExpenseModel(expense);
    }

    public ExpenseModel getExpenseModel() {
        return expenseModel;
    }

    @Override
    public String toString() {
        return "CreateExpenseResult{" +
                "expense=" + expenseModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Expense expense = new Expense();

        public Builder withExpense(Expense expense) {
            this.expense = expense;
            return this;
        }

        public CreateExpenseResult build() {
            return new CreateExpenseResult(expense);
        }
    }
}
