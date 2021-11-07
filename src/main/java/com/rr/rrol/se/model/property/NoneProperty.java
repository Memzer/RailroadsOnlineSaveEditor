package com.rr.rrol.se.model.property;

import com.rr.rrol.se.io.BinaryReader;

public class NoneProperty extends Property<String> {
	
	public NoneProperty(BinaryReader reader, PropertyType propertyType, PropertyName propertyName, long length) throws Exception {
		super(reader, propertyType, propertyName, length);
	}

	@Override
	public String getValue() {
		return null;
	}

}
