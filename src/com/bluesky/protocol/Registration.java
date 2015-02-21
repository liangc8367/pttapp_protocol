package com.bluesky.protocol;

import java.nio.ByteBuffer;

/**
 * Created by liangc on 29/12/14.
 */
public class Registration extends ProtocolBase {

    public Registration(long target, long source, short sequence){
        super(target, source, ProtocolBase.PTYPE_REGISTRATION, sequence);
    }

    public Registration(ByteBuffer payload){
        unserialize(payload);
    }

}
