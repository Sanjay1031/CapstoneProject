//package com.nashss.se.budgetme.lambda;
//
//import com.amazonaws.services.lambda.runtime.Context;
//import com.amazonaws.services.lambda.runtime.RequestHandler;
//import com.nashss.se.budgetme.activity.requests.UpdateExpenseRequest;
//import com.nashss.se.budgetme.activity.results.UpdateExpenseResult;
//
//import java.util.Map;
//
//import static com.nashss.se.budgetme.utils.NullUtils.ifNull;
//
//public class UpdateExpenseLambda extends LambdaActivityRunner<UpdateExpenseRequest, UpdateExpenseResult>
//        implements RequestHandler<LambdaRequest<UpdateExpenseRequest>, LambdaResponse> {
//    @Override
//    public LambdaResponse handleRequest(LambdaRequest<UpdateExpenseRequest> input, Context context) {
//        return super.runActivity(
//                () -> {
//                    UpdateExpenseRequest updateExpenseRequest = input.fromBody(UpdateExpenseRequest.class);
//                    Map<String, String> path = ifNull(input.getPathParameters(), Map.of());
//                    updateExpenseRequest.setPathExpenseId(path.get("expenseId"));
//                    return updateExpenseRequest;
//                },
//                (request, serviceComponent) ->
//                        serviceComponent.provideUpdateExpenseActivity().handleRequest(request)
//        );
//    }
//}
