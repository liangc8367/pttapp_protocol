package com.bluesky.protocol;

import java.nio.ByteBuffer;

/**
 * Created by liangc on 29/12/14.
 */
public class Ack extends ProtocolBase {
    public static final int ACKTYPE_POSITIVE    = 1;
    public static final int ACKTYPE_NEGATIVE    = 0;

    public static final int OFFSET_ACKTYPE      = 0;

    /** default ctor */
    public Ack(){
        super(ProtocolBase.PTYPE_ACK);
    }

    /** ctor, initialize from a received payload
     *
     * @param payload
     */
    public Ack(ByteBuffer payload){
        unserialize(payload);
    }

    public Ack(boolean positiveAck, ByteBuffer origPayload){
        if(positiveAck) {
            mAckType = ACKTYPE_POSITIVE;
        } else {
            mAckType = ACKTYPE_NEGATIVE;
        }
        mOrigPacket = ProtocolFactory.getProtocol(origPayload);
    }

    @Override
    public void unserialize(ByteBuffer payload){
        super.unserialize(payload);
        payload = super.getPayload();
        mAckType = payload.getShort();
        payload.position(getMySize());
        mOrigPacket = new ProtocolBase(payload.slice());
    }

    @Override
    public void serialize(ByteBuffer payload){
        super.serialize(payload);
        payload.putShort(mAckType);
        payload.putShort(mOrigPacket.getSequence());
        payload.putShort(mOrigPacket.getType());
    }

    @Override
    public int getSize(){
        return super.getSize() * 2 + Short.SIZE / Byte.SIZE;
    }

    static private int getMySize(){
        return Short.SIZE / Byte.SIZE;
    }

    public short getOrigSeq(){
        return mOrigPacket.getSequence();
    }

    public short getOrigType(){
        return mOrigPacket.getType();
    }


    public void setAckType(short mAckType) {
        this.mAckType = mAckType;
    }

    public short getAckType() {
        return mAckType;
    }

    private short mAckType  = ACKTYPE_NEGATIVE;     /// ack type
    private ProtocolBase    mOrigPacket = null;
}
