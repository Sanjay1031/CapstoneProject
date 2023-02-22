package com.nashss.se.budgetme.activity.requests;

public class DeleteExpenseRequest {
    private final String userId;

    private  String expenseId;

    private DeleteExpenseRequest(String userId, String expenseId) {
        this.userId = userId;
        this.expenseId = expenseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseId() {
        return expenseId;
    }

    @Override
    public String toString() {
        return "DeleteExpenseRequest{" +
                "userId='" + userId + '\'' +
                "expenseId='" + expenseId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static DeleteExpenseRequest.Builder builder() {
        return new DeleteExpenseRequest.Builder();
    }

    public static class Builder {
        private String userId;
        private String expenseId;
        public DeleteExpenseRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public DeleteExpenseRequest.Builder withExpenseId(String expenseId) {
            this.expenseId = expenseId;
            return this;
        }

        public DeleteExpenseRequest build() {
            return new DeleteExpenseRequest(userId, expenseId);
        }
    }
}
