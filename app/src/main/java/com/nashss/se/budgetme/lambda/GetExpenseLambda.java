package com.nashss.se.budgetme.lambda;

import com.nashss.se.budgetme.activity.requests.GetExpenseRequest;
import com.nashss.se.budgetme.activity.results.GetExpenseResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
