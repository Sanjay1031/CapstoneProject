package CapstoneProject.app.lambda;

import CapstoneProject.app.activity.requests.CreateExpenseRequest;
import CapstoneProject.app.activity.results.CreateExpenseResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreateExpenseLambda extends LambdaActivityRunner<CreateExpenseRequest, CreateExpenseResult>
        implements RequestHandler<LambdaRequest<CreateExpenseRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(LambdaRequest<CreateExpenseRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromBody(CreateExpenseRequest.class),
                (request, serviceComponent) ->
                        serviceComponent.provideCreateExpenseActivity().handleRequest(request)
        );
    }
}
