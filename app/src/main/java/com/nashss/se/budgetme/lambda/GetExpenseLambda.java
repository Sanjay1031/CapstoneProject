package com.nashss.se.budgetme.lambda;

import com.nashss.se.budgetme.activity.requests.GetExpenseRequest;
import com.nashss.se.budgetme.activity.results.GetExpenseResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetExpenseLambda
        extends LambdaActivityRunner<GetExpenseRequest, GetExpenseResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetExpenseRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetExpenseRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> {
                    GetExpenseRequest unauthenticatedRequest = input.fromBody(GetExpenseRequest.class);
                    return input.fromUserClaims(claims ->
                            GetExpenseRequest.builder()
                                    .withUserId(claims.get("email"))
                                    .withExpenseId(unauthenticatedRequest.getExpenseId())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetExpenseActivity().handleRequest(request)
        );
    }
}
