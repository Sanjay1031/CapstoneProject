package com.nashss.se.budgetme.activity.results;

import com.nashss.se.budgetme.dynamodb.models.Budget;
import com.nashss.se.budgetme.models.BudgetModel;

/**
 * Result class to handle an UpdateBudgetResult.
 */
public class UpdateBudgetResult {
    private final BudgetModel budgetModel;

    private UpdateBudgetResult(Budget budgetModel) {
        this.budgetModel = new BudgetModel(budgetModel);
    }

    public BudgetModel getBudgetModel() {
        return budgetModel;
    }

    @Override
    public String toString() {
        return "UpdateBudgetResult{" +
                "budgetModel=" + budgetModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static UpdateBudgetResult.Builder builder() {
        return new UpdateBudgetResult.Builder();
    }

    public static class Builder {

        private Budget budgetModel;

        public UpdateBudgetResult.Builder withBudgetModel(Budget budgetModel) {
            this.budgetModel = budgetModel;
            return this;
        }

        public UpdateBudgetResult build() {
            return new UpdateBudgetResult(budgetModel);
        }
    }
}
