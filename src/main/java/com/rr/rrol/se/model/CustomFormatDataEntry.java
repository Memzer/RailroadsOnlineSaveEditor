package com.rr.rrol.se.model;

import com.rr.rrol.se.reader.BinaryReader;

public class CustomFormatDataEntry {

	public String id;
	public int value;
	
	public CustomFormatDataEntry(BinaryReader reader) {
		id = reader.uuid();
		value = reader.readInt32();
	}

	public String getId() {
		return id;
	}

	public int getValue() {
		return value;
	}
}
