package CapstoneProject.app.models;

import CapstoneProject.app.converters.DateConverter;
import CapstoneProject.app.dynamodb.models.Budget;

import java.util.Objects;

public class BudgetModel {
    private String budgetId;
    private String targetAmount;
    private Boolean status;
    private String date;

    private DateConverter converter;

    private BudgetModel(Budget budget) {
        converter = new DateConverter();
        this.budgetId = budget.getBudgetId();
        this.targetAmount = budget.getTargetAmount();
        this.status = budget.getStatus();
        this.date = converter.convert(budget.getDate());
    }

    public String getBudgetId() {
        return budgetId;
    }

    public String getTargetAmount() {
        return targetAmount;
    }

    public Boolean getStatus() {
        return status;
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
        BudgetModel budgetModel = (BudgetModel) o;
        return budgetId.equals(budgetModel.budgetId) &&
                targetAmount.equals(budgetModel.targetAmount) &&
                date.equals(budgetModel.date) &&
                status.equals(budgetModel.status);

    }

    @Override
    public int hashCode() {
        return Objects.hash(budgetId, targetAmount, status, date);
    }

    @Override
    public String toString() {
        return "BudgetModel{" +
                "budgetId='" + budgetId + '\'' +
                ", targetAmount='" + targetAmount + '\'' +
                ", status='" + status + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
