package com.nashss.se.budgetme.activity.results;

import com.nashss.se.budgetme.dynamodb.models.Expense;
import com.nashss.se.budgetme.models.ExpenseModel;

public class UpdateExpenseResult {

    private final ExpenseModel expenseModel;

    private UpdateExpenseResult(Expense expenseModel) {
        this.expenseModel = new ExpenseModel(expenseModel);
    }

    public ExpenseModel getExpenseModel() {
        return expenseModel;
    }

    @Override
    public String toString() {
        return "UpdateExpenseResult{" +
                "expenseModel=" + expenseModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Expense expenseModel;

        public Builder withExpenseModel(Expense expenseModel) {
            this.expenseModel = expenseModel;
            return this;
        }

        public UpdateExpenseResult build() {
            return new UpdateExpenseResult(expenseModel);
        }
    }
}
