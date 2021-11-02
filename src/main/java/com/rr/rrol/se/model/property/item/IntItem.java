package com.rr.rrol.se.model.property.item;

import java.io.ByteArrayOutputStream;

import com.rr.rrol.se.reader.BinaryReader;
import com.rr.rrol.se.reader.BinaryWriter;

public class IntItem extends Item<Integer> {

	private Integer value; 
	
	public IntItem(BinaryReader reader, ItemType itemType, ItemName itemName) {
		super(reader, itemType, itemName);
		
		value = reader.readInt32();
	}

	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public void toByteArrayOutputStream(ByteArrayOutputStream os) {
		BinaryWriter.int32(os, value);
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
}
