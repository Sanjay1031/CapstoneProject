package CapstoneProject.app.activity.results;

public class GetExpenseResult {
    private final ExpenseModel expense;

    private GetExpenseResult(Expense expense) {
        this.expense = new ExpenseModel(expense);
    }

    public ExpenseModel getExpense() {
        return expense;
    }

    @Override
    public String toString() {
        return "GetExpenseResult{" +
                "expense=" + expense +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Expense expense;

        public Builder withExpense(Expense expense) {
            this.expense = expense;
            return this;
        }

        public GetExpenseResult build() {
            return new GetExpenseResult(expense);
        }
    }
}

