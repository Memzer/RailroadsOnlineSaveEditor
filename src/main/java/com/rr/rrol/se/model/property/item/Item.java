package com.rr.rrol.se.model.property.item;

import java.io.ByteArrayOutputStream;

import com.rr.rrol.se.reader.BinaryReader;

public abstract class Item<T> {
	
	protected ItemType itemType;
	protected ItemName itemName;

	public Item(BinaryReader reader, ItemType itemType, ItemName itemName) {
		this.itemType = itemType;
		this.itemName = itemName;
	}
	
	public abstract T getValue();
	
	public abstract void toByteArrayOutputStream(ByteArrayOutputStream os);
	
}
