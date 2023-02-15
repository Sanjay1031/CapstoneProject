package com.nashss.se.budgetme.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateExpenseRequest.Builder.class)
public class CreateExpenseRequest {
    private final String userId;
    private final String expenseId;
    private final String expenseName;
    private final String expenseAmount;
    private final String tag;
    private final String date;

    private CreateExpenseRequest(String userId,
                                 String expenseId,
                                 String expenseName,
                                 String expenseAmount,
                                 String tag,
                                 String date) {
        this.userId = userId;
        this.expenseId = expenseId;
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.tag = tag;
        this.date = date;
    }

    public String getUserId() {
        return userId;
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


    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private String expenseId;
        private String expenseName;
        private String expenseAmount;
        private String tag;
        private String date;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public Builder withExpenseId(String expenseId) {
            this.expenseId = expenseId;
            return this;
        }

        public Builder withExpenseName(String expenseName) {
            this.expenseName = expenseName;
            return this;
        }

        public Builder withExpenseAmount(String expenseAmount) {
            this.expenseAmount = expenseAmount;
            return this;
        }

        public Builder withTag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder withDate(String date) {
            this.date = date;
            return this;
        }


        public CreateExpenseRequest build() {
            return new CreateExpenseRequest(userId,
                    expenseId,
                    expenseName,
                    expenseAmount,
                    tag,
                    date);
        }
    }
}
