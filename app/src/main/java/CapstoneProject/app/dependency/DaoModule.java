package CapstoneProject.app.dependency;

/**
 * Dagger Module providing dependencies for DAO classes.
 */
@Module
public class DaoModule {
    /**
     * Provides a DynamoDBMapper singleton instance.
     *
     * @return DynamoDBMapper object
     */
    @Singleton
    @Provides
    public DynamoDBMapper provideDynamoDBMapper() {
        return new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient(Regions.US_EAST_2));
    }
}
