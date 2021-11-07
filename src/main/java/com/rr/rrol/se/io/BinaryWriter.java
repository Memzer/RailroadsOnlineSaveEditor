package com.rr.rrol.se.io;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.rr.rrol.se.model.Guid;

public class BinaryWriter {

	public static void int8(ByteArrayOutputStream os, Byte i) {
		os.write(i);
	}

	public static void int16(ByteArrayOutputStream os, Short i) {
		ByteBuffer bb = ByteBuffer.allocate(2);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.putShort(i);
		os.writeBytes(bb.array());
	}

	public static void int32(ByteArrayOutputStream os, Integer i) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.putInt(i);
		os.writeBytes(bb.array());
	}

	public static void int64(ByteArrayOutputStream os, Long i) {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.putLong(i);
		os.writeBytes(bb.array());
	}

	public static void float32(ByteArrayOutputStream os, Float i) {
		Integer intBits = Float.floatToIntBits(i);
		int32(os, intBits);
	}
	
	public static void string(ByteArrayOutputStream os, String s) {
		int length = s==null?0:s.length();
		ByteBuffer bb = ByteBuffer.allocate(length + (s==null?4:5));
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.putInt(length+(s==null?0:1));
		if(s != null) {
			bb.put(s.getBytes());
		}
		os.writeBytes(bb.array());
	}
	
	public static void stringWrapper(ByteArrayOutputStream os, StringWrapper s) {
		int length = s.getString()==null?0:s.getLength();
		ByteBuffer bb = ByteBuffer.allocate(length>0?4+length:4+(-2*length));
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.putInt(length);
		if(s != null) {
			bb.put(s.getString().getBytes());
		}
		os.writeBytes(bb.array());
	}
	
	public static void guid(ByteArrayOutputStream os, Guid guid) {
    	int32(os, guid.getI1());
    	int16(os, guid.getS1());
    	int16(os, guid.getS2());
    	int8(os, guid.getB1());
    	int8(os, guid.getB2());
    	int8(os, guid.getB3());
    	int8(os, guid.getB4());
    	int8(os, guid.getB5());
    	int8(os, guid.getB6());
    	int8(os, guid.getB7());
    	int8(os, guid.getB8());
	}
}
