package com.nashss.se.budgetme.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.budgetme.activity.requests.DeleteExpenseRequest;
import com.nashss.se.budgetme.activity.requests.GetExpenseRequest;
import com.nashss.se.budgetme.activity.results.DeleteExpenseResult;

public class DeleteExpenseLambda extends LambdaActivityRunner<DeleteExpenseRequest, DeleteExpenseResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteExpenseRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteExpenseRequest> input, Context context) {
        DeleteExpenseRequest expenseRequest = input.fromPath(path ->
                DeleteExpenseRequest.builder()
                        .withExpenseId(path.get("expenseId"))
                        .build());

        return super.runActivity(
                () -> input.fromUserClaims(claims ->
                        DeleteExpenseRequest.builder()
                                .withUserId(claims.get("email"))
                                .withExpenseId(expenseRequest.getExpenseId())
                                .build()),

                (request, serviceComponent) ->
                        serviceComponent.provideDeleteExpenseActivity().handleRequest(request)
        );
    }
}
