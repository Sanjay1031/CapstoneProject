package com.nashss.se.budgetme.activity.requests;

/**
 * Request class to handle a GetAllExpensesRequest.
 */
public class GetAllExpensesRequest {
/**
 * Instantiates a GetAllExpensesRequest.
 */
    private final String userId;

    public GetAllExpensesRequest(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GetAllExpenseRequest{" +
                "userId='" + userId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static GetAllExpensesRequest.Builder builder() {
        return new GetAllExpensesRequest.Builder();
    }

    public static class Builder {
        private String userId;
        public GetAllExpensesRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public GetAllExpensesRequest build() {
            return new GetAllExpensesRequest(userId);
        }
    }
}
