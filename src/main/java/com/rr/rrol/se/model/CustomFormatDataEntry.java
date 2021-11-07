package com.rr.rrol.se.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.rr.rrol.se.io.BinaryReader;
import com.rr.rrol.se.io.BinaryWriter;

public class CustomFormatDataEntry {

	private Guid guid;
	private int value;
	
	public CustomFormatDataEntry() {}
	
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

	public void setGuid(Guid guid) {
		this.guid = guid;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
