package com.rr.rrol.se.model.property;

import java.io.ByteArrayOutputStream;

import com.rr.rrol.se.io.BinaryReader;
import com.rr.rrol.se.io.BinaryWriter;

public class StrProperty extends Property<String> {
	
	private String value;

	public StrProperty(BinaryReader reader, PropertyType propertyType, PropertyName propertyName, long length) throws Exception {
		super(reader, propertyType, propertyName, length);
		terminator();
		value = reader.readString();
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void toByteArrayOutputStream(ByteArrayOutputStream os) {
		BinaryWriter.string(os, propertyName.toString());
		BinaryWriter.string(os, propertyType.toString());
		BinaryWriter.int64(os, length);
		os.write(0);
		BinaryWriter.string(os, value);
	}

}
