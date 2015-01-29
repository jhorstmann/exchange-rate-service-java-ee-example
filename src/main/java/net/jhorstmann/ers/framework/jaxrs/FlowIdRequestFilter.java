package net.jhorstmann.ers.framework.jaxrs;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import net.jhorstmann.ers.framework.flowid.FlowId;

@Provider
public class FlowIdRequestFilter implements ContainerRequestFilter {

    @Override
    public void filter(final ContainerRequestContext context) throws IOException {
        final String header = context.getHeaders().getFirst(FlowId.HTTP_HEADER);
        final String flowId = header != null ? header : FlowId.generate();
        FlowId.set(flowId);
    }

}
