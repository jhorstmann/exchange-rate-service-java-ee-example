package net.jhorstmann.ers.framework.jaxrs;

import net.jhorstmann.ers.framework.flowid.FlowId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@PreMatching
public class FlowIdRequestFilter implements ContainerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(FlowIdRequestFilter.class);
    @Override
    public void filter(final ContainerRequestContext context) throws IOException {
        final String header = context.getHeaders().getFirst(FlowId.HTTP_HEADER);
        LOG.trace("Flow id from header is [{}]", header);
        final String flowId = header != null ? header : FlowId.generate();
        FlowId.set(flowId);
    }

}
