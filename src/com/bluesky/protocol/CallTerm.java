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
    public CallTerm(long targetId, long suid, short audioSeq){
        super(PTYPE_CALL_TERM);
        mTargetId = targetId;
        mSuid = suid;
        mAudioSeq = audioSeq;
    }

    public CallTerm(ByteBuffer payload){
        unserialize(payload);
    }

    private CallTerm(){
        this(0L, 0L, (short)0);
    }

    @Override
    public void unserialize(ByteBuffer payload) {
        super.unserialize(payload);
        payload = super.getPayload();
        mTargetId = payload.getLong();
        mSuid = payload.getLong();
        mAudioSeq = payload.getShort();
    }

    @Override
    public void serialize(ByteBuffer payload) {
        super.serialize(payload);
        payload.putLong(mTargetId);
        payload.putLong(mSuid);
        payload.putShort(mAudioSeq);
    }

    @Override
    public int getSize() {
        return super.getSize() + 2 * Long.SIZE / Byte.SIZE + Short.SIZE/Byte.SIZE;
    }

    public long getTargetId() {
        return mTargetId;
    }

    public long getSuid() {
        return mSuid;
    }

    public short getAudioSeq() {
        return mAudioSeq;
    }

    public String toString(){
        return "CallTerm:" + super.toString()
                + ":" + Long.toHexString(mTargetId)
                + ":" + Long.toHexString(mSuid);
    }
    /** private members */
    long mTargetId;
    long mSuid;
    short mAudioSeq;

}
