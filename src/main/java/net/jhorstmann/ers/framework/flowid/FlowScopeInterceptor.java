package net.jhorstmann.ers.framework.flowid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@RunWithFlowId
@Interceptor
public class FlowScopeInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(FlowScopeInterceptor.class);

    @AroundInvoke
    public Object runInFlowScope(final InvocationContext ctx) throws Exception {
        final String flowId = FlowId.generateAndSet();
        LOG.debug("Running in new flow");
        try {
            return ctx.proceed();
        } finally {
            LOG.debug("Finished with flow");
            FlowId.unset();
        }
    }
}
