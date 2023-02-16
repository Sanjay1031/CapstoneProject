package com.nashss.se.budgetme.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.budgetme.activity.requests.GetAllExpensesRequest;
import com.nashss.se.budgetme.activity.results.GetAllExpensesResult;

public class GetAllExpensesLambda extends LambdaActivityRunner<GetAllExpensesRequest, GetAllExpensesResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetAllExpensesRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetAllExpensesRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromUserClaims(claims ->
                        GetAllExpensesRequest.builder()
                                .withUserId(claims.get("email"))
                                .withId(claims.get("expenseId"))
                                .build()),
                (request, serviceComponent) -> serviceComponent.provideGetAllExpensesActivity().handleRequest(request)
        );
    }
}
