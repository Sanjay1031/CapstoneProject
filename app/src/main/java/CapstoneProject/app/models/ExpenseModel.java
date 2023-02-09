package CapstoneProject.app.models;

import CapstoneProject.app.converters.DateConverter;
import CapstoneProject.app.dynamodb.models.Expense;

import java.util.Objects;

public class ExpenseModel {
    private String expenseId;
    private String expenseName;
    private String expenseAmount;
    private String tag;
    private String date;

    private DateConverter converter;

    public ExpenseModel(Expense expense) {
        converter = new DateConverter();
        this.expenseId = expense.getExpenseId();
        this.expenseName = expense.getExpenseName();
        this.expenseAmount = expense.getExpenseAmount();
        this.tag = expense.getTag();
        this.date = converter.convert(expense.getDate());
    }

    public String getExpenseId() {
        return expenseId;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public String getTag() {
        return tag;
    }

    public String getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExpenseModel that = (ExpenseModel) o;

        return Objects.equals(expenseId, that.expenseId) && Objects.equals(expenseName, that.expenseName) &&
                Objects.equals(expenseAmount, that.expenseAmount) &&
                Objects.equals(tag, that.tag) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseId, expenseName, expenseAmount, tag, date);
    }

    @Override
    public String toString() {
        return "ExpenseModel{" +
                "expenseId='" + expenseId + '\'' +
                ", expenseName='" + expenseName + '\'' +
                ", expenseAmount='" + expenseAmount + '\'' +
                ", tag='" + tag + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

