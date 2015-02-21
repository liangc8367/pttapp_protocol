package com.bluesky.utest3.protocol;

import com.bluesky.protocol.CallInit;
import com.bluesky.protocol.ProtocolBase;
import com.bluesky.protocol.ProtocolFactory;
import junit.framework.TestCase;

import java.nio.ByteBuffer;

public class CallInitTest extends TestCase {

    public void testUnserialize() throws Exception {
        long target = 0xaabbccddL, source = 0xbababaL;
        short seq = 0x1123;

        CallInit callInit = new CallInit(target, source, seq);

        int sz = callInit.getSize();
        ByteBuffer buf = ByteBuffer.allocate(sz);

        callInit.serialize(buf);

        buf.flip();

        // unserailize test
        assertEquals(ProtocolBase.PTYPE_CALL_INIT, ProtocolBase.peepType(buf));
        CallInit bproto = (CallInit) ProtocolFactory.getProtocol(buf);
        assertEquals(target, bproto.getTarget());
        assertEquals(source, bproto.getSource());
        assertEquals(seq, bproto.getSequence());

        System.out.print(callInit);
    }

    public void testSerialize() throws Exception {

    }

    public void testGetSize() throws Exception {

    }
}