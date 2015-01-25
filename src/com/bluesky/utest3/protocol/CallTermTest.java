package com.bluesky.utest3.protocol;

import com.bluesky.protocol.CallTerm;
import com.bluesky.protocol.ProtocolBase;
import com.bluesky.protocol.ProtocolFactory;
import junit.framework.TestCase;

import java.nio.ByteBuffer;

public class CallTermTest extends TestCase {

    public void testUnserialize() throws Exception {
        long suid = 0xbadbeef001L;
        long target = 0x123456789aL;
        short seq = (short)0xd123;
        short audioSeq = (short) 0x1199;

        CallTerm callTerm = new CallTerm(target, suid, audioSeq);
        callTerm.setSequence(seq);

        int sz = callTerm.getSize();
        ByteBuffer buf = ByteBuffer.allocate(sz);

        callTerm.serialize(buf);

        // unserailize test
        assertEquals(ProtocolBase.PTYPE_CALL_TERM, ProtocolBase.peepType(buf));
        CallTerm bproto = (CallTerm) ProtocolFactory.getProtocol(buf);
        assertEquals(seq, bproto.getSequence());
        assertEquals(suid, bproto.getSuid());
        assertEquals(target, bproto.getTargetId());
        assertEquals(audioSeq, bproto.getAudioSeq());

        System.out.print(callTerm);
    }
}