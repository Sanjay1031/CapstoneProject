package com.nashss.se.budgetme.activity.results;

import com.nashss.se.budgetme.dynamodb.models.Expense;
import com.nashss.se.budgetme.models.ExpenseModel;

public class DeleteExpenseResult {
    private final ExpenseModel expense;

    private DeleteExpenseResult(Expense expense) {
        this.expense = new ExpenseModel(expense);
    }

    public ExpenseModel getExpense() {
        return expense;
    }

    @Override
    public String toString() {
        return "DeleteExpenseResult{" +
                "expense=" + expense +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static DeleteExpenseResult.Builder builder() {
        return new DeleteExpenseResult.Builder();
    }

    public static class Builder {
        private Expense expense;

        public DeleteExpenseResult.Builder withExpense(Expense expense) {
            this.expense = expense;
            return this;
        }

        public DeleteExpenseResult build() {
            return new DeleteExpenseResult(expense);
        }
    }
}
