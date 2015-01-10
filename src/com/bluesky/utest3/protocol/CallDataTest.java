package com.bluesky.utest3.protocol;

import com.bluesky.protocol.ProtocolBase;
import com.bluesky.protocol.ProtocolFactory;
import com.bluesky.protocol.CallData;
import junit.framework.TestCase;

import java.nio.ByteBuffer;

public class CallDataTest extends TestCase {

    public void testUnserialize() throws Exception {
        int rawAudioSz = 23;
        final int SEED = 0x55;
        byte[] rawAudio = new byte[rawAudioSz];

        for(int i = 0; i < rawAudioSz; i++){
            rawAudio[i] = (byte)(i ^ 0x55);
        }

        long suid = 0xbadbeef001L;
        long target = 0x123456789aL;
        short audioSeq  = (short) 0x321;
        short seq = (short)0xd123;

        CallData callData = new CallData(target, suid, audioSeq, ByteBuffer.wrap(rawAudio));
        callData.setSequence(seq);

        int sz = callData.getSize();
        ByteBuffer buf = ByteBuffer.allocate(sz);

        callData.serialize(buf);

        // unserailize test
        assertEquals(ProtocolBase.PTYPE_CALL_DATA, ProtocolBase.peepType(buf));
        CallData bproto = (CallData) ProtocolFactory.getProtocol(buf);
        assertEquals(seq, bproto.getSequence());
        assertEquals(suid, bproto.getSuid());
        assertEquals(target, bproto.getTargetId());
        assertEquals(audioSeq, bproto.getAudioSeq());

        ByteBuffer audioBuf = bproto.getAudioData();
        assertEquals(audioBuf.limit(), rawAudioSz);
        byte[] audio = new byte[rawAudioSz];
        audioBuf.get(audio);
        for(int i = 0; i < rawAudioSz; i++){
            assertEquals(rawAudio[i], audio[i]);
        }

    }
}