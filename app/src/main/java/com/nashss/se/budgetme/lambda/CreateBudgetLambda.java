package com.nashss.se.budgetme.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.budgetme.activity.requests.CreateBudgetRequest;

import com.nashss.se.budgetme.activity.results.CreateBudgetResult;

/**
 * Lambda function to handle CreateBudgetAPI.
 */
public class CreateBudgetLambda extends LambdaActivityRunner<CreateBudgetRequest, CreateBudgetResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateBudgetRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateBudgetRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    CreateBudgetRequest unauthenticatedRequest = input.fromBody(CreateBudgetRequest.class);
                    return input.fromUserClaims(claims ->
                            CreateBudgetRequest.builder()
                                    .withUserId(claims.get("email"))
                                    .withTargetAmount(unauthenticatedRequest.getTargetAmount())
                                    .withStatus(unauthenticatedRequest.getStatus())
                                    .withDate(unauthenticatedRequest.getDate())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreateBudgetActivity().handleRequest(request)
        );
    }
}
