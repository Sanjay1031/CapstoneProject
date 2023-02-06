package CapstoneProject.app.lambda;

import CapstoneProject.app.activity.requests.GetExpenseRequest;
import CapstoneProject.app.activity.results.GetExpenseResult;

import javax.naming.Context;

public class GetExpenseLambda
        extends LambdaActivityRunner<GetExpenseRequest, GetExpenseResult>
        implements RequestHandler<LambdaRequest<GetExpenseRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetExpenseRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetExpenseRequest.builder()
                                .withId(path.get("expenseId"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetExpenseActivity().handleRequest(request)
        );
    }
}
