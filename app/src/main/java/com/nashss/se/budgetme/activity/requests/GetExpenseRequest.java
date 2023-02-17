package com.nashss.se.budgetme.activity.requests;

/**
 * Implementation of the GetExpenseRequest for BudgetMe GetExpense API.
 *
 * This API allows the user to get one of their saved expenditures.
 */
public class GetExpenseRequest {
    private final String userId;
    private final String expenseId;

    private GetExpenseRequest(String userId, String expenseId) {
        this.userId = userId;
        this.expenseId = expenseId;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GetExpenseRequest{" +
                "userId='" + userId + '\'' +
                "expenseId='" + expenseId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private String expenseId;
        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public Builder withExpenseId(String expenseId) {
            this.expenseId = expenseId;
            return this;
        }

        public GetExpenseRequest build() {
            return new GetExpenseRequest(userId, expenseId);
        }
    }
}

