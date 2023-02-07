package CapstoneProject.app.dynamodb;

import CapstoneProject.app.dynamodb.models.Budget;
import CapstoneProject.app.exceptions.BudgetNotFoundException;
import CapstoneProject.app.metrics.MetricsPublisher;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for a budget using {@link Budget} to represent the model in DynamoDB.
 */
@Singleton
public class BudgetDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a BudgetDao object.
     *
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the playlists table
     * @param metricsPublisher the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public BudgetDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Returns the {@link Budget} corresponding to the specified budgetId.
     *
     * @param budgetId the Budget ID
     * @return the stored Budget, or null if none was found.
     */
    public Budget getBudget(String budgetId) {
        Budget budget = this.dynamoDbMapper.load(Budget.class, budgetId);

        if (budget == null) {
            throw new BudgetNotFoundException("Could not find budget with id " + budgetId);
        }
        return budget;
    }

    /**
     * Saves (creates or updates) the given budget.
     *
     * @param budget The budget to save
     */
    public void saveBudget(Budget budget) {
        this.dynamoDbMapper.save(budget);

    }

//    /**
//     * Perform a search (via a "scan") of the playlist table for playlists matching the given criteria.
//     *
//     * Both "playlistName" and "tags" attributes are searched.
//     * The criteria are an array of Strings. Each element of the array is search individually.
//     * ALL elements of the criteria array must appear in the playlistName or the tags (or both).
//     * Searches are CASE SENSITIVE.
//     *
//     * @param criteria an array of String containing search criteria.
//     * @return a List of Playlist objects that match the search criteria.
//     */
//    public List<Budget> searchPlaylists(String[] criteria) {
//        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
//
//        if (criteria.length > 0) {
//            Map<String, AttributeValue> valueMap = new HashMap<>();
//            String valueMapNamePrefix = ":c";
//
//            StringBuilder nameFilterExpression = new StringBuilder();
//            StringBuilder tagsFilterExpression = new StringBuilder();
//
//            for (int i = 0; i < criteria.length; i++) {
//                valueMap.put(valueMapNamePrefix + i,
//                        new AttributeValue().withS(criteria[i]));
//                nameFilterExpression.append(
//                        filterExpressionPart("playlistName", valueMapNamePrefix, i));
//                tagsFilterExpression.append(
//                        filterExpressionPart("tags", valueMapNamePrefix, i));
//            }
//
//            dynamoDBScanExpression.setExpressionAttributeValues(valueMap);
//            dynamoDBScanExpression.setFilterExpression(
//                    "(" + nameFilterExpression + ") or (" + tagsFilterExpression + ")");
//        }
//
//        return this.dynamoDbMapper.scan(Budget.class, dynamoDBScanExpression);
//    }
//
//    private StringBuilder filterExpressionPart(String target, String valueMapNamePrefix, int position) {
//        String possiblyAnd = position == 0 ? "" : "and ";
//        return new StringBuilder()
//                .append(possiblyAnd)
//                .append("contains(")
//                .append(target)
//                .append(", ")
//                .append(valueMapNamePrefix).append(position)
//                .append(") ");
//    }
}
