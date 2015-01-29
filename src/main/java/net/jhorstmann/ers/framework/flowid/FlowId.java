package net.jhorstmann.ers.framework.flowid;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.primitives.Bytes.concat;
import static com.google.common.primitives.Longs.toByteArray;

import java.util.UUID;

import com.google.common.io.BaseEncoding;

public class FlowId {

    public static final String HTTP_HEADER = "X-Flow-ID";
    private static final String NAME = "flow-id";

    private static final ThreadLocal<String> threadLocalFlowId = new ThreadLocal<String>() {
        @Override
        public String toString() {
            return NAME;
        }
    };

    public static void set(final String flowId) throws IllegalStateException {

        checkState(threadLocalFlowId.get() == null, "Flow-ID already set");
        threadLocalFlowId.set(flowId);
    }

    public static void unset() throws IllegalStateException {
        checkState(threadLocalFlowId.get() != null, "Flow-ID not set");
        threadLocalFlowId.remove();
    }

    public static String get() throws IllegalStateException {
        final String flowId = threadLocalFlowId.get();
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
        final String flowId = threadLocalFlowId.get();
        return flowId != null ? flowId : generateAndSet();
    }

}
