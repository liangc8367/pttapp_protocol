package com.bluesky.protocol;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

/**
 *
 * Created by liangc on 29/12/14.
 */
public class ProtocolFactory {

    /** unserialize protocol */
    static public ProtocolBase getProtocol(ByteBuffer payload){

        short protoType = ProtocolBase.peepType(payload);
        ProtocolBase proto;
        switch(protoType){
            case ProtocolBase.PTYPE_ACK: {
                Ack p = new Ack(payload);
                proto = p;
                break;
            }
            case ProtocolBase.PTYPE_REGISTRATION: {
                Registration p = new Registration(payload);
                proto = p;
                break;
            }
            case ProtocolBase.PTYPE_CALL_INIT: {
                CallInit p = new CallInit(payload);
                proto = p;
                break;
            }
            case ProtocolBase.PTYPE_CALL_TERM: {
                CallTerm p = new CallTerm(payload);
                proto = p;
                break;
            }
            case ProtocolBase.PTYPE_CALL_DATA: {
                CallData p = new CallData(payload);
                proto = p;
                break;
            }
            default:
                throw new IllegalArgumentException();
        }
        return proto;
    }

    /** unserialize protocol from received DatagramPacket */
    static public ProtocolBase getProtocol(DatagramPacket packet){
        return getProtocol(ByteBuffer.wrap(packet.getData(), packet.getOffset(), packet.getLength()));
    }
}
