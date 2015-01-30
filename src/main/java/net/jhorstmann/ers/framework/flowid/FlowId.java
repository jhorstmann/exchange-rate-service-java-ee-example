package net.jhorstmann.ers.framework.flowid;

import com.google.common.io.BaseEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.primitives.Bytes.concat;
import static com.google.common.primitives.Longs.toByteArray;

public class FlowId {

    public static final String HTTP_HEADER = "X-Flow-ID";
    private static final String NAME = "flow-id";

    private static final Logger LOG = LoggerFactory.getLogger(FlowId.class);

    public static void set(final String flowId) throws IllegalStateException {
        checkState(MDC.get(NAME) == null, "Flow-ID already set");
        MDC.put(NAME, flowId);
        LOG.trace("Set flow id [{}]", flowId);
    }

    public static void unset() throws IllegalStateException {
        String flowId = MDC.get(NAME);
        checkState(flowId != null, "Flow-ID not set");
        MDC.remove(NAME);
        LOG.trace("Removed flow id [{}]", flowId);
    }

    public static String get() throws IllegalStateException {
        final String flowId = MDC.get(NAME);
        checkState(flowId != null, "Flow-ID not set");
        return flowId;
    }

    public static String generate() {
        final UUID uuid = UUID.randomUUID();
        final byte[] high = toByteArray(uuid.getMostSignificantBits());
        final byte[] low = toByteArray(uuid.getLeastSignificantBits());
        final byte[] bytes = concat(high, low);

        bytes[0] = 'J';

        return BaseEncoding.base64Url().encode(bytes);
    }

    public static String generateAndSet() throws IllegalStateException {
        final String flowId = generate();
        set(flowId);
        return flowId;
    }

    public static String getOrGenerateAndSet() {
        final String flowId = MDC.get(NAME);
        return flowId != null ? flowId : generateAndSet();
    }

}
