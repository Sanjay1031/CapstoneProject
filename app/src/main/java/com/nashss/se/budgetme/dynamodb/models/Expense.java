package com.nashss.se.budgetme.dynamodb.models;

import com.nashss.se.budgetme.converters.DateConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a record in the expenditures table.
 * Previously AlbumDAO
 */
@DynamoDBTable(tableName = "Expenditures")
public class Expense {
    public static final String TAG_GSI = "TagIdIndex";
    public static final String DATE_GSI = "DateIndex";

    private String userId;
    private String expenseId;
    private String expenseAmount;
    private String expenseName;
    private String tag;
    private LocalDate date;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBHashKey(attributeName = "expenseId")
    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    @DynamoDBAttribute(attributeName = "expenseAmount")
    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    // "name" is a reserved word in DDB, so the attribute in the table is called "expenseName".
    @DynamoDBAttribute(attributeName = "expenseName")
    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }
    @DynamoDBIndexHashKey(globalSecondaryIndexName = TAG_GSI)
    @DynamoDBAttribute(attributeName = "tag")
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    @DynamoDBIndexHashKey(globalSecondaryIndexName = DATE_GSI)
    @DynamoDBAttribute(attributeName = "date")
    @DynamoDBTypeConverted(converter = DateConverter.class)
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
        Expense that = (Expense) o;
        return expenseId.equals(that.expenseId) &&
                expenseAmount.equals(that.expenseAmount) &&
                Objects.equals(expenseName, that.expenseName) &&
                Objects.equals(tag, that.tag) &&
                Objects.equals(date, that.date);

    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseId, expenseAmount, expenseName, tag, date);
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId='" + expenseId + '\'' +
                ", expenseAmount=" + expenseAmount +
                ", expenseName='" + expenseName + '\'' +
                ", tag='" + tag + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
