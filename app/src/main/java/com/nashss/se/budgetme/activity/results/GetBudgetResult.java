package com.nashss.se.budgetme.activity.results;

import com.nashss.se.budgetme.dynamodb.models.Budget;
import com.nashss.se.budgetme.models.BudgetModel;


/**
 * Implementation of the GetBudgetActivity for BudgetMe GetBudget API.
 *
 * This API allows the user to get one of their saved expenditures.
 */
public class GetBudgetResult {
    private final BudgetModel budget;

    private GetBudgetResult(Budget budget) {
        this.budget = new BudgetModel(budget);
    }

    public BudgetModel getBudget() {
        return budget;
    }

    @Override
    public String toString() {
        return "GetBudgetResult{" +
                "budget=" + budget +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static GetBudgetResult.Builder builder() {
        return new GetBudgetResult.Builder();
    }

    public static class Builder {
        private Budget budget;

        public GetBudgetResult.Builder withBudget(Budget budget) {
            this.budget = budget;
            return this;
        }

        public GetBudgetResult build() {
            return new GetBudgetResult(budget);
        }
    }
}
