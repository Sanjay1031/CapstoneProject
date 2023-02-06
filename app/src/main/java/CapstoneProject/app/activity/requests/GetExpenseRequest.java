package CapstoneProject.app.activity.requests;

public class GetExpenseRequest {
    private final String expenseId;

    private GetExpenseRequest(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getId() {
        return expenseId;
    }

    @Override
    public String toString() {
        return "GetExpenseRequest{" +
                "expenseId='" + expenseId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String expenseId;

        public Builder withId(String expenseId) {
            this.expenseId = expenseId;
            return this;
        }

        public GetExpenseRequest build() {
            return new GetExpenseRequest(expenseId);
        }
    }
}

