package com.rr.rrol.se.model.property;

import com.rr.rrol.se.reader.BinaryReader;

public abstract class Property {

	protected PropertyName propertyName;
	protected PropertyType propertyType;
	protected long length;
	protected BinaryReader reader;
	
	public Property(BinaryReader reader, PropertyType propertyType, PropertyName propertyName, long length) {
		this.reader = reader;
		this.propertyName = propertyName;
		this.propertyType = propertyType;
		this.length = length;
	}
	
	protected void terminator() throws Exception {
		if(reader.get() != 0) {throw new Exception("Expected terminator");}
	}

	public PropertyName getPropertyName() {
		return propertyName;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}
}
