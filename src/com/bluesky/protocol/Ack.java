package com.bluesky.protocol;

import java.nio.ByteBuffer;

/**
 * Created by liangc on 29/12/14.
 */
public class Ack extends ProtocolBase {
    public static final short ACKTYPE_POSITIVE    = 1;
    public static final short ACKTYPE_NEGATIVE    = 0;

    /** default ctor */
    private Ack(){
        super(0L, 0L, ProtocolBase.PTYPE_ACK, (short)0);
    }

    /** ctor, initialize from a received payload
     *
     * @param payload
     */
    public Ack(ByteBuffer payload){
        unserialize(payload);
    }

    /** ctor, shall only be used to instance Ack for reply
     *
     * @param positiveAck
     * @param origProto
     */
    public Ack(long target, long source, short sequence, boolean positiveAck, ProtocolBase origProto){
        super(target, source, ProtocolBase.PTYPE_ACK, sequence);
        if(positiveAck) {
            mAckType = ACKTYPE_POSITIVE;
        } else {
            mAckType = ACKTYPE_NEGATIVE;
        }
        mOrigProto = origProto;
    }

    @Override
    public void unserialize(ByteBuffer payload){
        super.unserialize(payload);
        payload = super.getPayload();
        mAckType = payload.getShort();
        mOrigProto = ProtocolFactory.getProtocol(payload.slice());
    }

    /** packet format:
     *      base
     *      ack-type: short
     *      orig-packet
     * @param payload, position will be changed afterwards
     */
    @Override
    public void serialize(ByteBuffer payload){
        super.serialize(payload);
        payload.putShort(mAckType);
        mOrigProto.serialize(payload);
    }

    @Override
    public int getSize(){
        return super.getSize() + mOrigProto.getSize() + Short.SIZE/Byte.SIZE;
    }


    public ProtocolBase getOrigProto(){
        return mOrigProto;
    }

    public short getAckType() {
        return mAckType;
    }

    public String toString(){
        String c;
        if( mAckType == ACKTYPE_POSITIVE ){
            c = "Ack";
        } else {
            c = "Nack";
        }
        return super.toString() + ":" + c + ":"
                + mOrigProto;
    }

    private short mAckType  = ACKTYPE_NEGATIVE;     /// ack type
    private ProtocolBase    mOrigProto = null;
}
