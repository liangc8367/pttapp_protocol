package com.bluesky.utest3.protocol;

import com.bluesky.protocol.CallInit;
import com.bluesky.protocol.ProtocolBase;
import com.bluesky.protocol.ProtocolFactory;
import junit.framework.TestCase;

import java.nio.ByteBuffer;

public class CallInitTest extends TestCase {

    public void testUnserialize() throws Exception {
        long suid = 0xbadbeef001L;
        long target = 0x123456789aL;
        short seq = (short)0xd123;

        CallInit callInit = new CallInit(target, suid);
        callInit.setSequence(seq);

        int sz = callInit.getSize();
        ByteBuffer buf = ByteBuffer.allocate(sz);

        callInit.serialize(buf);

        // unserailize test
        assertEquals(ProtocolBase.PTYPE_CALL_INIT, ProtocolBase.peepType(buf));
        CallInit bproto = (CallInit) ProtocolFactory.getProtocol(buf);
        assertEquals(seq, bproto.getSequence());
        assertEquals(suid, bproto.getSuid());
        assertEquals(target, bproto.getTargetId());

        System.out.print(callInit);
    }

    public void testSerialize() throws Exception {

    }

    public void testGetSize() throws Exception {

    }
}