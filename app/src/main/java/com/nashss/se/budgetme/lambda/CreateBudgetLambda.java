package com.nashss.se.budgetme.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.budgetme.activity.requests.CreateBudgetRequest;
import com.nashss.se.budgetme.activity.results.CreateBudgetResult;

public class CreateBudgetLambda extends LambdaActivityRunner<CreateBudgetRequest, CreateBudgetResult>
        implements RequestHandler<LambdaRequest<CreateBudgetRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(LambdaRequest<CreateBudgetRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromBody(CreateBudgetRequest.class),
                (request, serviceComponent) ->
                        serviceComponent.provideCreateBudgetActivity().handleRequest(request)
        );
    }
}
