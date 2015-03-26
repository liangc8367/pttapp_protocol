package com.bluesky.protocol;

import java.nio.ByteBuffer;

/**
 * Call termination protocol, used by SU to mark the end of call, or by Trunk manager for callhang
 *  * Format:
 *  Long(64bit, network endian): target id
 *  Long: suid (source/subscriber id)
 *  short: type
 *  short: seq
 *  short: count-down (used by listener to sync to end of call-hang)
 *
 * Created by liangc on 04/01/15.
 */
public class CallTerm extends ProtocolBase{
    public CallTerm(long target, long source, short sequence, short countdown){
        super(target, source, PTYPE_CALL_TERM, sequence);
        mCountdown = countdown;
    }

    public CallTerm(ByteBuffer payload){
        unserialize(payload);
    }

    @Override
    public void unserialize(ByteBuffer payload){
        super.unserialize(payload);
        mCountdown = super.getPayload().getShort();
    }

    @Override
    public void serialize(ByteBuffer payload){
        super.serialize(payload);
        payload.putShort(mCountdown);
    }

    @Override
    public int getSize(){
        return super.getSize() + Short.SIZE/Byte.SIZE;
    }

    @Override
    public String toString(){
        return super.toString() + ":" + mCountdown;
    }

    public short getCountdown(){
        return mCountdown;
    }

    private short mCountdown;

}
