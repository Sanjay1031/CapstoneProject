package CapstoneProject.app.dynamodb;


/**
 * Accesses data for an expense using {@link Expense} to represent the model in DynamoDB.
 */

public class ExpenseDao {
    private static final int PAGE_SIZE = 20;
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates an ExpenseDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the expenditures table
     */
    @Inject
    public ExpenseDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Retrieves an Expenditure by expenseId.
     *
     * If not found, throws AlbumTrackNotFoundException.
     *
     * @param expenseId The expenseId to look up
     * @return The corresponding expense if found
     */
    public Expense getExpense(String expenseId) {
        Expense expense = dynamoDbMapper.load(Expense.class, expenseId);
        if (null == expense) {
            throw new ExpenseNotFoundException(
                    String.format("Could not find expenditure with expenseId '%s'", expense));
        }

        return expense;
    }
    /**
     * Saves (creates or updates) the given expense.
     * @param expense The expense to save
     */
    public void saveExpense(Expense expense) {
        this.dynamoDbMapper.save(expense);
    }
}
