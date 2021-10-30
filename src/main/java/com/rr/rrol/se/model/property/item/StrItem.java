package com.rr.rrol.se.model.property.item;

import com.rr.rrol.se.reader.BinaryReader;

public class StrItem extends Item {

	private String value; 
	
	public StrItem(BinaryReader reader, ItemType itemType, ItemName itemName) {
		super(reader, itemType, itemName);		
		value = reader.readString();
	}

	public String getValue() {
		return value;
	}
	
}
