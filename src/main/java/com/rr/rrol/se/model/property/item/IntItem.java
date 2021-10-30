package com.rr.rrol.se.model.property.item;

import com.rr.rrol.se.reader.BinaryReader;

public class IntItem extends Item {

	private int value; 
	
	public IntItem(BinaryReader reader, ItemType itemType, ItemName itemName) {
		super(reader, itemType, itemName);
		
		value = reader.readInt32();
	}

	public int getValue() {
		return value;
	}
	
}
