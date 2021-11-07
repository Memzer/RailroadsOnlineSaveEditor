package com.rr.rrol.se.model.property.item;

import com.rr.rrol.se.io.BinaryReader;
import com.rr.rrol.se.io.StringWrapper;

public class StrItem extends Item<String> {

	private String value;
	private int length;
	
	public StrItem(BinaryReader reader, ItemType itemType, ItemName itemName) {
		super(reader, itemType, itemName);		
		StringWrapper sw = reader.readStringWrapper();
		value = sw.getString();
		length = sw.getLength();
	}

	@Override
	public String getValue() {
		return value;
	}
	public int getLength() {
		return length;
	}
	
}
