package CapstoneProject.app.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a record in the budgets table.
 * Previously PlaylistDAO
 */
@DynamoDBTable(tableName = "budgets")
public class Budget {
    private String budgetId;
    private String targetAmount;

    private Boolean status;
    private LocalDate date;

    @DynamoDBHashKey(attributeName = "budgetId")
    public String getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(String budgetId) {
        this.budgetId = budgetId;
    }


    @DynamoDBAttribute(attributeName = "targetAmount")
    public String getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(String targetAmount) {
        this.targetAmount = targetAmount;
    }
    @DynamoDBAttribute(attributeName = "status")
    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }

    @DynamoDBAttribute(attributeName = "date")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Budget budget = (Budget) o;
        return budgetId.equals(budget.budgetId) &&
                targetAmount.equals(budget.targetAmount) &&
                date.equals(budget.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budgetId, targetAmount, date);
    }

}
