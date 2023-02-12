package com.nashss.se.budgetme.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDate;

@JsonDeserialize(builder = CreateBudgetRequest.Builder.class)
public class CreateBudgetRequest {

    private final String budgetId;
    private final String targetAmount;
    private final Boolean status;
    private final String date;

    private CreateBudgetRequest(String budgetId,
                                 String targetAmount,
                                 Boolean status,
                                 String date) {
        this.budgetId = budgetId;
        this.targetAmount = targetAmount;
        this.status = status;
        this.date = date;
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

    //CHECKSTYLE:OFF:Builder
    public static CreateBudgetRequest.Builder builder() {
        return new CreateBudgetRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String budgetId;
        private String targetAmount;
        private Boolean status;
        private String date;


        public CreateBudgetRequest.Builder withBudgetId(String budgetId) {
            this.budgetId = budgetId;
            return this;
        }

        public CreateBudgetRequest.Builder withTargetAmount(String targetAmount) {
            this.targetAmount = targetAmount;
            return this;
        }
        public CreateBudgetRequest.Builder withStatus(Boolean status) {
            this.status = status;
            return this;
        }

        public CreateBudgetRequest.Builder withDate(String date) {
            this.date = date;
            return this;
        }


        public CreateBudgetRequest build() {
            return new CreateBudgetRequest(budgetId,
                    targetAmount,
                    status,
                    date);
        }
    }
}