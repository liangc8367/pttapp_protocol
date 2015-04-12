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
    /** ctor
     *
     * @param target
     * @param source
     * @param sequence
     * @param audioData valid data from position to limit, i.e. size as remaining
     */
    public CallData(long target, long source, short sequence, ByteBuffer audioData){
        super(target, source, PTYPE_CALL_DATA, sequence);
        mAudioData = audioData;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if( obj instanceof CallData){
            if(super.equals(obj)){
                mAudioData.rewind(); // if callData had serialized, then we have to rewind its audio-data.
                if(mAudioData.equals(((CallData)obj).mAudioData)){
                    equal = true;
                }
            }
        }
        return equal;
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
                + mAudioData.remaining(); // mAudioData now in read-mode, i.e. limit is its # of elements
    }

    public ByteBuffer getAudioData() {
        return mAudioData;
    }

    /** private members */
    private ByteBuffer  mAudioData;

}
