package com.nashss.se.budgetme.activity.requests;
/**
 * Implementation of the GetExpenseRequest for BudgetMe GetExpense API.
 *
 * This API allows the user to get one of their saved expenditures.
 */
public class GetBudgetRequest {
    private final String budgetId;

    private GetBudgetRequest(String budgetId) {
        this.budgetId = budgetId;
    }

    public String getId() {
        return budgetId;
    }

    @Override
    public String toString() {
        return "GetExpenseRequest{" +
                "budgetId='" + budgetId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String budgetId;

        public Builder withId(String budgetId) {
            this.budgetId = budgetId;
            return this;
        }

        public GetBudgetRequest build() {
            return new GetBudgetRequest(budgetId);
        }
    }
}
