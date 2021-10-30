package com.rr.rrol.se.model.property;

import com.rr.rrol.se.reader.BinaryReader;

public class StrProperty extends Property {
	
	private String value;

	public StrProperty(BinaryReader reader, PropertyType propertyType, PropertyName propertyName, long length) throws Exception {
		super(reader, propertyType, propertyName, length);
		terminator();
		value = reader.readString();
	}

}
