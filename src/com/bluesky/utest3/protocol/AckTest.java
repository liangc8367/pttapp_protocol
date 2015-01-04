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
        Registration reg = new Registration();
        short origSeq = 0x1123;
        long origSuid = 0x0abcdef123456L;
        reg.setSequence(origSeq);
        reg.setSUID(origSuid);

        int origSz = reg.getSize();
        ByteBuffer origBuf = ByteBuffer.allocate(origSz);
        reg.serialize(origBuf);

        Ack aProto = new Ack(false, origBuf);
        short seq = 0x1122;
        aProto.setSequence(seq);
        aProto.setAckType(Ack.ACKTYPE_POSITIVE);

        int sz = aProto.getSize();
        ByteBuffer buf = ByteBuffer.allocate(sz);
        aProto.serialize(buf);

        // now, let's unserialize
        int type = ProtocolBase.peepType(buf);
        assertEquals(ProtocolBase.PTYPE_ACK, type);
        Ack bProto = (Ack) ProtocolFactory.getProtocol(buf);
        assertEquals(Ack.ACKTYPE_POSITIVE, bProto.getAckType());
        assertEquals(aProto.getSequence(), seq);
        assertEquals(aProto.getOrigSeq(), origSeq);
        assertEquals(aProto.getOrigType(), ProtocolBase.PTYPE_REGISTRATION);

    }

    public void testSerialize() throws Exception {

    }

    public void testGetSize() throws Exception {

    }
}