package com.bluesky.utest.protocol;

import static org.junit.Assert.*;
import com.bluesky.protocol.*;

import java.nio.ByteBuffer;

public class AckTest {

    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void testUnserialize() throws Exception {
        Ack aProto = new Ack();
        short seq = 0x1122;
        aProto.setSequence(seq);
        aProto.setAckType(Ack.ACKTYPE_POSITIVE);

        int sz = aProto.getSize();
        ByteBuffer buf = ByteBuffer.allocate(sz);
        aProto.serialize(buf);

        // now, let's unserialize
        int type = ProtocolBase.peepType(buf);
        assertEquals(ProtocolBase.PTYPE_ACK, type);
        Ack bProto = (Ack)ProtocolFactory.getProtocol(buf);
        assertEquals(Ack.ACKTYPE_POSITIVE, bProto.getAckType());

    }

    @org.junit.Test
    public void testSerialize() throws Exception {

    }

    @org.junit.Test
    public void testGetSize() throws Exception {

    }
}