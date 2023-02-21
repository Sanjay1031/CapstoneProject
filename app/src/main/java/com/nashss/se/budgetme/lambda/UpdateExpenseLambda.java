package com.nashss.se.budgetme.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.budgetme.activity.requests.GetExpenseRequest;
import com.nashss.se.budgetme.activity.requests.UpdateExpenseRequest;
import com.nashss.se.budgetme.activity.results.UpdateExpenseResult;

import java.util.Map;

import static com.nashss.se.budgetme.utils.NullUtils.ifNull;

public class UpdateExpenseLambda extends LambdaActivityRunner<UpdateExpenseRequest, UpdateExpenseResult>

        implements RequestHandler<AuthenticatedLambdaRequest<UpdateExpenseRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateExpenseRequest> input, Context context) {
        UpdateExpenseRequest unauthenticatedRequest = input.fromBody(UpdateExpenseRequest.class);
        UpdateExpenseRequest updateRequest = input.fromPath(path ->
                UpdateExpenseRequest.builder()
                        .withExpenseId(path.get("expenseId"))
                        .build());

        return super.runActivity(
                () -> input.fromUserClaims(claims ->
                            UpdateExpenseRequest.builder()
                                    .withUserId(claims.get("email"))
                                    .withExpenseId(updateRequest.getExpenseId())
                                    .withExpenseAmount(unauthenticatedRequest.getExpenseAmount())
                                    .withDate(unauthenticatedRequest.getDate())
                                    .withTag(unauthenticatedRequest.getTag())
                                    .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateExpenseActivity().handleRequest(request)
        );
    }
}


