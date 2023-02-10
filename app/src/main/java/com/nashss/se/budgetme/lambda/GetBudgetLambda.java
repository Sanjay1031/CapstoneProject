package com.nashss.se.budgetme.lambda;

import com.nashss.se.budgetme.activity.requests.GetBudgetRequest;
import com.nashss.se.budgetme.activity.results.GetBudgetResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetBudgetLambda
        extends LambdaActivityRunner<GetBudgetRequest, GetBudgetResult>
        implements RequestHandler<LambdaRequest<GetBudgetRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetBudgetRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetBudgetRequest.builder()
                                .withId(path.get("budgetId"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetBudgetActivity().handleRequest(request)
        );
    }
}
