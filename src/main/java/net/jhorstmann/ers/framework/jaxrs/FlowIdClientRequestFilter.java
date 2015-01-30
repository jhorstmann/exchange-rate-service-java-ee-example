package net.jhorstmann.ers.framework.jaxrs;

import net.jhorstmann.ers.framework.flowid.FlowId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class FlowIdClientRequestFilter implements ClientRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(FlowIdClientRequestFilter.class);

    @Override
    public void filter(final ClientRequestContext context) throws IOException {
        final String flowId = FlowId.get();
        LOG.trace("Added flow id [{}] to client request", flowId);
        context.getHeaders().putSingle(FlowId.HTTP_HEADER, flowId);
    }
}
