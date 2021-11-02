package com.rr.rrol.se.model.property;

import java.io.ByteArrayOutputStream;

import com.rr.rrol.se.reader.BinaryReader;
import com.rr.rrol.se.reader.BinaryWriter;

public class NoneProperty extends Property<String> {
	
	public NoneProperty(BinaryReader reader, PropertyType propertyType, PropertyName propertyName, long length) throws Exception {
		super(reader, propertyType, propertyName, length);
	}

	@Override
	public String getValue() {
		return null;
	}

	@Override
	public void toByteArrayOutputStream(ByteArrayOutputStream os) {
		BinaryWriter.string(os, "None");
		BinaryWriter.int32(os, 0);
	}

}
