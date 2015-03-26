package com.bluesky.utest3.protocol;

import com.bluesky.protocol.CallTerm;
import com.bluesky.protocol.ProtocolBase;
import com.bluesky.protocol.ProtocolFactory;
import junit.framework.TestCase;

import java.nio.ByteBuffer;

public class CallTermTest extends TestCase {

    public void testUnserialize() throws Exception {
        long target = 0xaabbccddL, source = 0xbababaL;
        short seq = 0x1123;
        short countdown = 20;

        CallTerm callTerm = new CallTerm(target, source, seq, countdown);

        int sz = callTerm.getSize();
        ByteBuffer buf = ByteBuffer.allocate(sz);

        callTerm.serialize(buf);

        buf.flip();

        // unserailize test
        assertEquals(ProtocolBase.PTYPE_CALL_TERM, ProtocolBase.peepType(buf));
        CallTerm bproto = (CallTerm) ProtocolFactory.getProtocol(buf);
        assertEquals(target, bproto.getTarget());
        assertEquals(source, bproto.getSource());
        assertEquals(seq, bproto.getSequence());
        assertEquals(countdown, bproto.getCountdown());

        System.out.print(callTerm);
    }
}