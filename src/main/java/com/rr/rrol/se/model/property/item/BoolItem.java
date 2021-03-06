package com.rr.rrol.se.model.property.item;

import com.rr.rrol.se.io.BinaryReader;

public class BoolItem extends Item<Boolean> {

	private Boolean value; 
	
	public BoolItem(BinaryReader reader, ItemType itemType, ItemName itemName) {
		super(reader, itemType, itemName);
		
		byte v = reader.readInt8();
		if(v == 1) {
			value = true;
		} else {
			value = false;
		}
	}

	@Override
	public Boolean getValue() {
		return value;
	}
	
}
