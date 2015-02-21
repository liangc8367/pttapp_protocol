package com.bluesky.utest3.protocol;

import com.bluesky.protocol.Ack;
import com.bluesky.protocol.ProtocolBase;
import com.bluesky.protocol.ProtocolFactory;
import com.bluesky.protocol.Registration;
import junit.framework.TestCase;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public class AckTest extends TestCase {

    public void testUnserialize() throws Exception {
        long target = 0xaabbccddL, source = 0xbababaL;
        short seq = 0x1123;

        Registration reg = new Registration(target, source, seq);

        int origSz = reg.getSize();
        ByteBuffer origBuf = ByteBuffer.allocate(origSz);
        reg.serialize(origBuf);

        long t2 = 0x112233L, s2 = 0xffeeddcc123L;
        short seq2 = 0x988;

        Ack aProto = new Ack(t2, s2, seq2, false, reg);

        int sz = aProto.getSize();
        ByteBuffer buf = ByteBuffer.allocate(sz);
        aProto.serialize(buf);

        buf.flip();

        // now, let's unserialize
        int type = ProtocolBase.peepType(buf);
        assertEquals(ProtocolBase.PTYPE_ACK, type);
        Ack bproto = (Ack) ProtocolFactory.getProtocol(buf);
        assertEquals(t2, bproto.getTarget());
        assertEquals(s2, bproto.getSource());
        assertEquals(seq2, bproto.getSequence());
        assertEquals(ProtocolBase.PTYPE_ACK, bproto.getType());
        assertEquals(Ack.ACKTYPE_NEGATIVE, bproto.getAckType());

        ProtocolBase origProto = bproto.getOrigProto();
        assertEquals(origProto.getType(), ProtocolBase.PTYPE_REGISTRATION);
        Registration reg2 = (Registration) origProto;

        assertEquals(reg2.getTarget(), target);
        assertEquals(reg2.getSource(), source);
        assertEquals(reg2.getSequence(), seq);

        System.out.print(aProto);
    }

    public void testSerialize() throws Exception {

    }

    public void testGetSize() throws Exception {

    }
}