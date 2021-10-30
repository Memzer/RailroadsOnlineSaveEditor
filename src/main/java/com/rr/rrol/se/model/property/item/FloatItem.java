package com.rr.rrol.se.model.property.item;

import com.rr.rrol.se.reader.BinaryReader;

public class FloatItem extends Item {

	private float value; 
	
	public FloatItem(BinaryReader reader, ItemType itemType, ItemName itemName) {
		super(reader, itemType, itemName);
		
		value = reader.readFloat();
	}

	public float getValue() {
		return value;
	}
	
}
