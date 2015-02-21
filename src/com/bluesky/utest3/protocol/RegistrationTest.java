package com.bluesky.utest3.protocol;

import com.bluesky.protocol.ProtocolBase;
import com.bluesky.protocol.ProtocolFactory;
import com.bluesky.protocol.Registration;
import junit.framework.TestCase;

import java.nio.ByteBuffer;

public class RegistrationTest extends TestCase {

    public void testUnserialize() throws Exception {
        long target = 0xaabbccddL, source = 0xbababaL;
        short seq = 0x1123;
        Registration reg = new Registration(target, source, seq);

        int sz = reg.getSize();
        ByteBuffer buf = ByteBuffer.allocate(sz);
        reg.serialize(buf);

        buf.flip();

        ProtocolBase proto = ProtocolFactory.getProtocol(buf);
        assertEquals(proto.getTarget(), target);
        assertEquals(proto.getSource(), source);
        assertEquals(ProtocolBase.PTYPE_REGISTRATION, proto.getType());
        assertEquals(proto.getSequence(), seq);

        System.out.print(reg.toString());
    }

    public void testSerialize() throws Exception {

    }

    public void testGetSize() throws Exception {

    }
}