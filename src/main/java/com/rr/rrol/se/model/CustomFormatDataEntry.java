package com.rr.rrol.se.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.rr.rrol.se.reader.BinaryReader;
import com.rr.rrol.se.reader.BinaryWriter;

public class CustomFormatDataEntry {

	public Guid guid;
	public int value;
	
	public CustomFormatDataEntry(BinaryReader reader) {
		guid = reader.uuid();
		value = reader.readInt32();
	}
    
    public void toByteArrayOutputStream(ByteArrayOutputStream os) throws IOException {
    	BinaryWriter.guid(os, guid);
    	BinaryWriter.int32(os, value);
    }

	public Guid getGuid() {
		return guid;
	}

	public int getValue() {
		return value;
	}
}
