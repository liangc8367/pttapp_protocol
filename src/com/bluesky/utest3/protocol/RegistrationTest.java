package com.bluesky.utest3.protocol;

import com.bluesky.protocol.ProtocolBase;
import com.bluesky.protocol.ProtocolFactory;
import com.bluesky.protocol.Registration;
import junit.framework.TestCase;

import java.nio.ByteBuffer;

public class RegistrationTest extends TestCase {

    public void testUnserialize() throws Exception {
        Registration reg = new Registration();
        short seq = 0x1123;
        long suid = 0x0abcdef123456L;
        reg.setSequence(seq);
        reg.setSUID(suid);

        int sz = reg.getSize();
        ByteBuffer buf = ByteBuffer.allocate(sz);
        reg.serialize(buf);

        ProtocolBase proto = ProtocolFactory.getProtocol(buf);
        assertEquals(ProtocolBase.PTYPE_REGISTRATION, proto.getType());
        assertEquals(proto.getSequence(), seq);

        Registration bReg = (Registration)proto;
        assertEquals(bReg.getSUID(), suid);
    }

    public void testSerialize() throws Exception {

    }

    public void testGetSize() throws Exception {

    }
}