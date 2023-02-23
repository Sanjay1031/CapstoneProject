package com.nashss.se.budgetme.activity.results;

import com.nashss.se.budgetme.dynamodb.models.Expense;
import com.nashss.se.budgetme.models.ExpenseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Result class to handle a GetAllExpensesResult.
 */
public class GetAllExpensesResult {
    private final List<ExpenseModel> expenseList;

    /**
     * Instantiates a new GetAllExpenses object.
     *
     * @param expenseList to access the expenditures table.
     */
    private GetAllExpensesResult(List<Expense> expenseList) {
        this.expenseList = expenseList.stream().map(ExpenseModel::new).collect(Collectors.toList());
    }

    public List<ExpenseModel> getExpenseList() {
        return new ArrayList<>(expenseList);
    }

    @Override
    public String toString() {
        return "GetAllExpensesResult{" +
                "expenseList=" + expenseList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private List<Expense> expenseList;

        public Builder withExpenseList(List<Expense> expenseList){
            this.expenseList = new ArrayList<>(expenseList);
            return this;
        }

        public GetAllExpensesResult build() { return new GetAllExpensesResult(expenseList); }
    }
}
