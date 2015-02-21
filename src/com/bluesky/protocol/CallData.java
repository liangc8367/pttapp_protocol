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
    public CallData(long target, long source, short sequence, ByteBuffer audioData){
        super(target, source, PTYPE_CALL_DATA, sequence);
        mAudioData = audioData;
    }

    public CallData(ByteBuffer payload){
        unserialize(payload);
    }

    @Override
    public void unserialize(ByteBuffer payload) {
        super.unserialize(payload);
        mAudioData = super.getPayload();
    }

    @Override
    public void serialize(ByteBuffer payload) {
        super.serialize(payload);
        payload.put(mAudioData);
    }

    @Override
    public int getSize() {
        return super.getSize()
                + mAudioData.limit(); // mAudioData now in read-mode, i.e. limit is its # of elements
    }

    public ByteBuffer getAudioData() {
        return mAudioData;
    }

    /** private members */
    private ByteBuffer  mAudioData;

}
