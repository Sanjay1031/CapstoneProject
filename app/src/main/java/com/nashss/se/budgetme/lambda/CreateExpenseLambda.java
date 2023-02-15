package com.nashss.se.budgetme.lambda;

import com.nashss.se.budgetme.activity.requests.CreateExpenseRequest;
import com.nashss.se.budgetme.activity.results.CreateExpenseResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreateExpenseLambda extends LambdaActivityRunner<CreateExpenseRequest, CreateExpenseResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateExpenseRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateExpenseRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    CreateExpenseRequest unauthenticatedRequest = input.fromBody(CreateExpenseRequest.class);
                    return input.fromUserClaims(claims ->
                            CreateExpenseRequest.builder()
                                    .withUserId(claims.get("email"))
                                    .withExpenseName(unauthenticatedRequest.getExpenseName())
                                    .withExpenseAmount(unauthenticatedRequest.getExpenseAmount())
                                    .withTag(unauthenticatedRequest.getTag())
                                    .withDate(unauthenticatedRequest.getDate())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreateExpenseActivity().handleRequest(request)
        );
    }
}
