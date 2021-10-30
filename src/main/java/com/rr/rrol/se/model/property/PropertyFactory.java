package com.rr.rrol.se.model.property;

import com.rr.rrol.se.reader.BinaryReader;

public class PropertyFactory {

	public static Property read(BinaryReader reader) throws Exception {
		if(reader.remaining() <= 0) {
			return null;
		}
		
		PropertyName pName = PropertyName.valueOf(reader.readString());
		String type = reader.readString();
		PropertyType pType = type==null?null:PropertyType.valueOf(type);

		if(pType == null) {
			return new NoneProperty(reader, pType, pName, 0);
		}

		long length = reader.readInt64();		
		
		if(pType.equals(PropertyType.StrProperty)) {
			return new StrProperty(reader, pType, pName, length);
		}
		if(pType.equals(PropertyType.ArrayProperty)) {
			return new ArrayProperty(reader, pType, pName, length);
		}
		
		return null;
	}
	
}
