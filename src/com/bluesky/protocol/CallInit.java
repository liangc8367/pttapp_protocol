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
    public CallInit(long targetId, long suid){
        super(PTYPE_CALL_INIT);
        mTargetId = targetId;
        mSuid = suid;
    }

    public CallInit(ByteBuffer payload){
        unserialize(payload);
    }

    private CallInit(){
        this(0L, 0L);
    }

    @Override
    public void unserialize(ByteBuffer payload) {
        super.unserialize(payload);
        payload = super.getPayload();
        mTargetId = payload.getLong();
        mSuid = payload.getLong();
    }

    @Override
    public void serialize(ByteBuffer payload) {
        super.serialize(payload);
        payload.putLong(mTargetId);
        payload.putLong(mSuid);
    }

    @Override
    public int getSize() {
        return super.getSize() + 2 * Long.SIZE / Byte.SIZE;
    }

    public long getTargetId() {
        return mTargetId;
    }

    public long getSuid() {
        return mSuid;
    }

    public String toString(){
        return "CallInit:" + super.toString()
                + ":" + Long.toHexString(mTargetId)
                + ":" + Long.toHexString(mSuid);
    }
    /** private members */
    long mTargetId;
    long mSuid;
}
