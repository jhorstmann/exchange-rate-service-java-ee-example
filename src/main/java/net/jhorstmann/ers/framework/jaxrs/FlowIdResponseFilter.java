package net.jhorstmann.ers.framework.jaxrs;

import net.jhorstmann.ers.framework.flowid.FlowId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class FlowIdResponseFilter implements ContainerResponseFilter {


    private static final Logger LOG = LoggerFactory.getLogger(FlowIdResponseFilter.class);

    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext context)
        throws IOException {
        final String flowId = FlowId.get();

        LOG.trace("Returning flow id [{}]", flowId);

        context.getHeaders().putSingle(FlowId.HTTP_HEADER, flowId);
        FlowId.unset();
    }
}
