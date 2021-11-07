package com.rr.rrol.se.model.property.item;

import com.rr.rrol.se.io.BinaryReader;

public class StructItem extends Item<Point3D> {

	private Point3D value;
	
	public StructItem(BinaryReader reader, ItemType itemType, ItemName itemName) {
		super(reader, itemType, itemName);
		
		value = new Point3D(reader.readFloat(), reader.readFloat(), reader.readFloat());
	}

	@Override
	public Point3D getValue() {
		return value;
	}
	
}
