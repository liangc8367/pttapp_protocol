package com.bluesky.protocol;

import java.nio.ByteBuffer;

/**
 * Call Data
 * Format:
 *  Long(64bit, network endian): target id
 *  Long: suid (source/subscriber id)
 *  Byte: audio data, per preset configuration
 *
 * Created by liangc on 04/01/15.
 */
public class CallData extends ProtocolBase {
    public CallData(long targetId, long suid, short audioSeq, ByteBuffer audioData){
        super(PTYPE_CALL_DATA);
        mTargetId = targetId;
        mSuid = suid;
        mAudioSeq = audioSeq;
        mAudioData = audioData;
    }

    public CallData(ByteBuffer payload){
        unserialize(payload);
    }

    private CallData(){
        this(0L, 0L, (short)0, null);
    }

    @Override
    public void unserialize(ByteBuffer payload) {
        super.unserialize(payload);
        payload = super.getPayload();
        mTargetId = payload.getLong();
        mSuid = payload.getLong();
        mAudioSeq = payload.getShort();
        mAudioData = payload.slice();
    }

    @Override
    public void serialize(ByteBuffer payload) {
        super.serialize(payload);
        payload.putLong(mTargetId);
        payload.putLong(mSuid);
        payload.putShort(mAudioSeq);
        payload.put(mAudioData);
    }

    @Override
    public int getSize() {
        return super.getSize()
                + 2 * Long.SIZE / Byte.SIZE
                + Short.SIZE / Byte.SIZE
                + mAudioData.limit(); // mAudioData now in read-mode, i.e. limit is its # of elements
    }

    public long getTargetId() {
        return mTargetId;
    }

    public long getSuid() {
        return mSuid;
    }

    public short getAudioSeq(){
        return mAudioSeq;
    }

    public ByteBuffer getAudioData() {
        return mAudioData;
    }

    public String toString(){
        return "CallData:" + super.toString()
                + ":" + Long.toHexString(mTargetId)
                + ":" + Long.toHexString(mSuid)
                + ":" + Integer.toHexString(mAudioSeq);
    }

    /** private members */
    long        mTargetId;
    long        mSuid;
    short       mAudioSeq;
    ByteBuffer  mAudioData;

}
