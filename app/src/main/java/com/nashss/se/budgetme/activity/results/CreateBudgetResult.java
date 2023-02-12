package com.nashss.se.budgetme.activity.results;

import com.nashss.se.budgetme.dynamodb.models.Budget;
import com.nashss.se.budgetme.models.BudgetModel;

public class CreateBudgetResult {
    private final BudgetModel budgetModel;

    private CreateBudgetResult(Budget budget) {
        this.budgetModel = new BudgetModel(budget);
    }

    public BudgetModel getBudgetModel() {
        return budgetModel;
    }

    @Override
    public String toString() {
        return "CreateBudgetResult{" +
                "budget=" + budgetModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static CreateBudgetResult.Builder builder() {
        return new CreateBudgetResult.Builder();
    }

    public static class Builder {
        private Budget budget = new Budget();

        public CreateBudgetResult.Builder withBudget(Budget budget) {
            this.budget = budget;
            return this;
        }

        public CreateBudgetResult build() {
            return new CreateBudgetResult(budget);
        }
    }

}
