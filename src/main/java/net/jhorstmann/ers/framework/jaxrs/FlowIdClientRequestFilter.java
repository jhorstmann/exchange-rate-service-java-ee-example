package net.jhorstmann.ers.framework.jaxrs;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.Provider;

import net.jhorstmann.ers.framework.flowid.FlowId;

@Provider
public class FlowIdClientRequestFilter implements ClientRequestFilter {

    @Override
    public void filter(final ClientRequestContext context) throws IOException {
        final String flowId = FlowId.get();
        context.getHeaders().putSingle(FlowId.HTTP_HEADER, flowId);
    }
}
