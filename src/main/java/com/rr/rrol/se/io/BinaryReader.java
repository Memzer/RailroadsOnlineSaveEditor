package com.rr.rrol.se.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.rr.rrol.se.model.Guid;

public class BinaryReader {

	private ByteBuffer bb;
	
	public BinaryReader(InputStream in) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
	        byte[] buffer = new byte[2048];
	        int readCntOnce;

	        while ((readCntOnce = in.read(buffer)) >= 0) {
	            out.write(buffer, 0, readCntOnce);
	        }
	        
	        out.flush();
		    byte[] targetArray = out.toByteArray();	
		    
		    InputStream bais = new ByteArrayInputStream(targetArray);
		    bb = ByteBuffer.allocate(targetArray.length);
		    while (bais.available() > 0) {
		        bb.put((byte) bais.read());
		    }
		    bb.flip();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BinaryReader(String filename) {		
		try {
			RandomAccessFile aFile = new RandomAccessFile(filename,"r");
			FileChannel inChannel = aFile.getChannel();
	        long fileSize = inChannel.size();

	        bb = ByteBuffer.allocate((int) fileSize);
	        inChannel.read(bb);
	        bb.flip();
	        
	        inChannel.close();
	        aFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public byte readInt8() {
		return bb.get();
	}
	public byte get() {
		return bb.get();
	}
	public int position() {
		return bb.position();
	}
	public int remaining() {
		return bb.remaining();
	}
	public byte[] readBytes(int length) {
		byte[] bytes = new byte[length];
		bb.get(bytes);
		return bytes;
	}
	public ByteBuffer get(byte[] bytes) {
		return bb.get(bytes);
	}
	public Short readInt16() {
		return (short)((bb.get() & 0xFF) | (bb.get() & 0xFF) << 8);
	}
	public Integer readInt32() {
		return (bb.get() & 0xFF) | (bb.get() & 0xFF) << 8 | (bb.get() & 0xFF) << 16 | (bb.get() & 0xFF) << 24;
	}	
	public Long readInt64() {
		return (bb.get() & 0xFFL) | (bb.get() & 0xFFL) << 8 | (bb.get() & 0xFFL) << 16 | (bb.get() & 0xFFL) << 24 |
				(bb.get() & 0xFFL) << 32 | (bb.get() & 0xFFL) << 40 | (bb.get() & 0xFFL) << 48 | (bb.get() & 0xFFL) << 56;
	}
	public Float readFloat() {
		return Float.intBitsToFloat((bb.get() & 0xFF) ^ (bb.get() & 0xFF) << 8 ^ (bb.get() & 0xFF) << 16 ^ (bb.get() & 0xFF) << 24);
	}
	public String readString() {
		int length = readInt32();
		if(length == 0) {
			return null;
		}
		if(length == 1 || length == -1) {
			return "";
		}
		byte[] valueBytes = new byte[length>0?length:-2*length];
		
		if(length > 0) {
			bb.get(valueBytes);
			String s = new String(valueBytes);
			return s.substring(0, s.length()-1);
		} else {
			bb.get(valueBytes);
			String s = new String(valueBytes);
			return s.substring(0, s.length()-2);
		}
	}
	public StringWrapper readStringWrapper() {
		int length = readInt32();
		if(length == 0) {
			return new StringWrapper(length, null);
		}
		if(length == 1 || length == -1) {
			return new StringWrapper(length, "");
		}
		byte[] valueBytes = new byte[length>0?length:-2*length];
		
		if(length > 0) {
			bb.get(valueBytes);
			String s = new String(valueBytes);
			return new StringWrapper(length, s.substring(0, s.length()-1));
		} else {
			bb.get(valueBytes);
			String s = new String(valueBytes);
			return new StringWrapper(length, s.substring(0, s.length()-2));
		}
	}
	public Guid uuid() {
		Guid guid = new Guid(readInt32(),readInt16(),readInt16(),readInt8(),readInt8(),readInt8(),readInt8(),readInt8(),readInt8(),readInt8(),readInt8());		
		return guid;
	}
}
