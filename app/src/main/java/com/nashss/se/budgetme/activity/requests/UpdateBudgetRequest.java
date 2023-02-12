package com.nashss.se.budgetme.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = UpdateBudgetRequest.Builder.class)
public class UpdateBudgetRequest {
    private String budgetId;
    /**
     * Uses the pathBudget as our path and makes sure it's the same
     * budgetID that is trying to be changed.
     */
    private String pathBudgetId;

    private final String targetAmount;

    private final String date;

    /**
     * Takes in all the fields to be updated.
     *
     * @param budgetId takes in the employee's ID.
     * @param targetAmount takes in the lastName.
     * @param date takes in the email.
     */
    public UpdateBudgetRequest(String budgetId, String targetAmount, String date) {
        this.budgetId = budgetId;
        this.targetAmount = targetAmount;
        this.date = date;
    }

    public String getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(String budgetId) {
        this.budgetId = budgetId;
    }

    public String getPathBudgetId() {
        return pathBudgetId;
    }

    public void setPathBudgetId(String pathBudgetId) {
        this.pathBudgetId = pathBudgetId;
    }

    public String getTargetAmount() {
        return targetAmount;
    }

    public String getDate() {
        return date;
    }


    @Override
    public String toString() {
        return "UpdateBudgetRequest{" +
                "budgetId='" + budgetId + '\'' +
                ", targetAmount='" + targetAmount + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static UpdateBudgetRequest.Builder builder() {
        return new UpdateBudgetRequest.Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {

        private String budgetId;

        private String targetAmount;


        private String date;


        public UpdateBudgetRequest.Builder withBudgetId(String budgetId) {
            this.budgetId = budgetId;
            return this;
        }

        public UpdateBudgetRequest.Builder withTargetAmount(String targetAmount) {
            this.targetAmount = targetAmount;
            return this;
        }

        public UpdateBudgetRequest.Builder withDate(String date) {
            this.date = date;
            return this;
        }



        public UpdateBudgetRequest build() {
            return new UpdateBudgetRequest(budgetId, targetAmount,date);
        }
    }
}
