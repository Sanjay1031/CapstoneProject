package com.nashss.se.budgetme.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.budgetme.activity.requests.UpdateBudgetRequest;

import com.nashss.se.budgetme.activity.results.UpdateBudgetResult;

import java.util.Map;

import static com.nashss.se.budgetme.utils.NullUtils.ifNull;

/**
 * Lambda function to handle UpdateBudgetAPI.
 */
public class UpdateBudgetLambda extends LambdaActivityRunner<UpdateBudgetRequest, UpdateBudgetResult>
        implements RequestHandler<LambdaRequest<UpdateBudgetRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(LambdaRequest<UpdateBudgetRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdateBudgetRequest updateBudgetRequest = input.fromBody(UpdateBudgetRequest.class);
                    Map<String, String> path = ifNull(input.getPathParameters(), Map.of());
                    updateBudgetRequest.setPathBudgetId(path.get("budgetId"));
                    return updateBudgetRequest;
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateBudgetActivity().handleRequest(request)
        );
    }
}
