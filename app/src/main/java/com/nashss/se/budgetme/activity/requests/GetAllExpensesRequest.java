package com.nashss.se.budgetme.activity.requests;

public class GetAllExpensesRequest {

    private final String expenseId;

    private final String userId;

    public GetAllExpensesRequest(String expenseId, String userId) {
        this.expenseId = expenseId;
        this.userId = userId;
    }

    public String getId() {
        return expenseId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GetAllExpenseRequest{" +
                "userId='" + userId + '\'' +
                "expenseId='" + expenseId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static GetAllExpensesRequest.Builder builder() {
        return new GetAllExpensesRequest.Builder();
    }

    public static class Builder {
        private String userId;
        private String expenseId;
        public GetAllExpensesRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public GetAllExpensesRequest.Builder withId(String expenseId) {
            this.expenseId = expenseId;
            return this;
        }

        public GetAllExpensesRequest build() {
            return new GetAllExpensesRequest(expenseId, userId);
        }
    }
}
