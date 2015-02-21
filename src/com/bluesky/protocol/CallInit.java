package com.bluesky.protocol;

import java.nio.ByteBuffer;

/**
 * Call initiation protocol, used by subscriber to initiate call, (as preamble)
 *
 * Format:
 *  Long(64bit, network endian): target id
 *  Long: suid (source/subscriber id)
 *
 * Created by liangc on 04/01/15.
 */
public class CallInit extends ProtocolBase {
    public CallInit(long target, long source, short sequence){
        super(target, source, PTYPE_CALL_INIT, sequence);
    }

    public CallInit(ByteBuffer payload){
        unserialize(payload);
    }

}
