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

        long target = 0xaabbccddL, source = 0xbababaL;
        short seq = 0x1123;

        CallData callData = new CallData(target, source, seq, ByteBuffer.wrap(rawAudio));

        int sz = callData.getSize();
        ByteBuffer buf = ByteBuffer.allocate(sz);

        callData.serialize(buf);

        buf.flip();

        // unserailize test
        assertEquals(ProtocolBase.PTYPE_CALL_DATA, ProtocolBase.peepType(buf));
        CallData bproto = (CallData) ProtocolFactory.getProtocol(buf);
        assertEquals(target, bproto.getTarget());
        assertEquals(source, bproto.getSource());
        assertEquals(seq, bproto.getSequence());
        assertEquals(ProtocolBase.PTYPE_CALL_DATA, bproto.getType());

        ByteBuffer audioBuf = bproto.getAudioData();
        assertEquals(audioBuf.limit(), rawAudioSz);
        byte[] audio = new byte[rawAudioSz];
        audioBuf.get(audio);
        for(int i = 0; i < rawAudioSz; i++){
            assertEquals(rawAudio[i], audio[i]);
        }

        System.out.print(callData);

        // equals()
        byte[] rawAudio2 = new byte[rawAudioSz];

        for(int i = 0; i < rawAudioSz; i++){
            rawAudio2[i] = (byte)(i ^ 0x55);
        }
        CallData callData2 = new CallData(target, source, seq, (ByteBuffer)ByteBuffer.wrap(rawAudio2).flip());
        assertEquals(true, callData2.equals(callData));

    }
}