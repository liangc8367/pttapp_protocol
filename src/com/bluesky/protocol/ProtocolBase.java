package com.bluesky.protocol;

import java.lang.Short;
import java.nio.ByteBuffer;

/**
 * base of protocol packet
 *  uint64_t: target
 *  uint64_t: source
 *  uint16_t: type
 *  uint16_t: sequence
 *
 * Created by liangc on 29/12/14.
 */
public class ProtocolBase {
    public static final int OFFSET_TARGET = 0;
    public static final int OFFSET_SOURCE = 8;
    public static final int OFFSET_TYPE = 16;
    public static final int OFFSET_SEQUENCE = 24;

    /** packet type */
    public static final short PTYPE_INVALID      = 0;
    public static final short PTYPE_ACK          = 1;
    public static final short PTYPE_REGISTRATION = 3;
    public static final short PTYPE_CALL_INIT    = 4;
    public static final short PTYPE_CALL_DATA    = 5;
    public static final short PTYPE_CALL_TERM    = 6;


    /** ctor, default */
    public ProtocolBase(long target, long source, short type, short sequence){
        mTarget = target;
        mSource = source;
        mType = type;
        mSequence = sequence;
    }

    protected ProtocolBase(){
        this(0L, 0L, PTYPE_INVALID, (short)0);
    }

    /** ctor using received payload
     * @param payload
     */
    public ProtocolBase(ByteBuffer payload){
        unserialize(payload);
    }

    /** unserialize from ByteBuffer
     * @param payload data from offset 0 to payload.position.
     *                If the payload was just serialized, caller shall call flip() first.
     */
    public void unserialize(ByteBuffer payload){
        mPayload = payload;
        mTarget = mPayload.getLong();
        mSource = mPayload.getLong();
        mType = mPayload.getShort();
        mSequence = mPayload.getShort();
    }

    /** serialize to ByteBuffer,
     *
     * @param payload, position will be changed afterwards
     */
    public void serialize(ByteBuffer payload){
        payload.putLong(mTarget);
        payload.putLong(mSource);
        payload.putShort(mType);
        payload.putShort(mSequence);
    }

    /** get size of the proto
     *
     * @return
     * TODO: in future, we can subclass ByteBuffer as Sizer class, then
     * we call serializer() with a Sizer instance, and get the size from
     * the Sizer afterwards.
     *
     */
    public int getSize(){
        return getMySize();
    }

    public static short peepType(ByteBuffer payload){
        return payload.getShort(OFFSET_TYPE);
    }

    /** get size of unserialized version */
    public short getType(){
        return mType;
    }

    public short getSequence(){
        return mSequence;
    }

    public long getTarget(){
        return mTarget;
    }

    public long getSource(){
        return mSource;
    }

    /** return payload, except for the base part
     *
     * @return
     */
    public ByteBuffer getPayload(){
        ByteBuffer payload = mPayload;
        payload.position(getMySize());
        return payload.slice();
    }

    public String toString (){
        return Long.toHexString(mTarget) + ":" +
               Long.toHexString(mSource) + "::" +
               protoTypeName(mType) + ":" + mSequence;
    }

    /** private methods and members */
    private void setType(short mType) {
        this.mType = mType;
    }

    /** private methods and members */
    private int getMySize(){
        return (2 * Long.SIZE + 2 * Short.SIZE) / Byte.SIZE;
    }

    private String protoTypeName(short type){
        String name;
        switch(type){
            case PTYPE_REGISTRATION:
                name = "Registra";
                break;
            case PTYPE_ACK:
                name = "Ack";
                break;
            case PTYPE_CALL_INIT:
                name = "CallInit";
                break;
            case PTYPE_CALL_DATA:
                name = "CallData";
                break;
            case PTYPE_CALL_TERM:
                name = "CallTerm";
            case PTYPE_INVALID:
            default:
                name = "Invalid";
                break;
        }
        return name;
    }

    private long mTarget;
    private long mSource;
    private short mType;
    private short mSequence;

    ByteBuffer    mPayload  = null;
}
