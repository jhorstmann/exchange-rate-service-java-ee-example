package net.jhorstmann.ers.framework.flowid;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@RunWithFlowId
@Interceptor
public class FlowScopeInterceptor {
    @AroundInvoke
    public Object runInFlowScope(final InvocationContext ctx) throws Exception {
        final String flowId = FlowId.generateAndSet();
        System.out.println("Created new flow id " + flowId);
        try {
            return ctx.proceed();
        } finally {
            FlowId.unset();
            System.out.println("Removed flow id " + flowId);
        }
    }
}
