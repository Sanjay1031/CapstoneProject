package CapstoneProject.app.dependency;

/**
 * Dagger Module providing dependencies for metrics classes.
 */
@Module
public class MetricsModule {

    /**
     * Provides CloudWatch client.
     *
     * @return instance for AmazonCloudWatchAsync
     */
    @Provides
    @Singleton
    static AmazonCloudWatch provideCloudWatch() {
        return AmazonCloudWatchAsyncClientBuilder.standard()
                .withRegion(Regions.US_EAST_2)
                .build();
    }
}
