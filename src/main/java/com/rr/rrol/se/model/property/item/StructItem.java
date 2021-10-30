package com.rr.rrol.se.model.property.item;

import com.rr.rrol.se.reader.BinaryReader;

public class StructItem extends Item {

	private float x,y,z; 
	
	public StructItem(BinaryReader reader, ItemType itemType, ItemName itemName) {
		super(reader, itemType, itemName);
		
		x = reader.readFloat();
		y = reader.readFloat();
		z = reader.readFloat();
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}
	
}
