package com.rr.rrol.se.model.property;

import com.rr.rrol.se.reader.BinaryReader;

public class NoneProperty extends Property {
	
	public NoneProperty(BinaryReader reader, PropertyType propertyType, PropertyName propertyName, long length) throws Exception {
		super(reader, propertyType, propertyName, length);
	}

}
