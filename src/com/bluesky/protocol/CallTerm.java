package com.bluesky.protocol;

import java.nio.ByteBuffer;

/**
 * Call termination protocol, used by SU to mark the end of call, or by Trunk manager for callhang
 *  * Format:
 *  Long(64bit, network endian): target id
 *  Long: suid (source/subscriber id)
 *
 * Created by liangc on 04/01/15.
 */
public class CallTerm extends ProtocolBase{
    public CallTerm(long target, long source, short sequence){
        super(target, source, PTYPE_CALL_TERM, sequence);
    }

    public CallTerm(ByteBuffer payload){
        unserialize(payload);
    }

}
