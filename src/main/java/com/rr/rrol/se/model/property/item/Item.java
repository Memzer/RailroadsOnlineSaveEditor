package com.rr.rrol.se.model.property.item;

import com.rr.rrol.se.reader.BinaryReader;

public class Item {
	
	protected ItemType itemType;
	protected ItemName itemName;

	public Item(BinaryReader reader, ItemType itemType, ItemName itemName) {
		this.itemType = itemType;
		this.itemName = itemName;
	}
	
}
