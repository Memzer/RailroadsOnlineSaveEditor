package com.rr.rrol.se.model.property;

import com.rr.rrol.se.io.BinaryReader;
import com.rr.rrol.se.io.StringWrapper;

public class StrProperty extends Property<String> {
	
	private String value;
	private long length;

	public StrProperty(BinaryReader reader, PropertyType propertyType, PropertyName propertyName, long length) throws Exception {
		super(reader, propertyType, propertyName, length);
		terminator();	
		StringWrapper sw = reader.readStringWrapper();
		value = sw.getString();
		length = sw.getLength();
	}

	@Override
	public String getValue() {
		return value;
	}
	public long getLength() {
		return length;
	}

}
