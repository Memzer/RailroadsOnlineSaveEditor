package com.rr.rrol.se.model.property.item;

import java.io.ByteArrayOutputStream;

import com.rr.rrol.se.io.BinaryReader;
import com.rr.rrol.se.io.BinaryWriter;

public class FloatItem extends Item<Float> {

	private Float value; 
	
	public FloatItem(BinaryReader reader, ItemType itemType, ItemName itemName) {
		super(reader, itemType, itemName);
		
		value = reader.readFloat();
	}

	@Override
	public Float getValue() {
		return value;
	}

	@Override
	public void toByteArrayOutputStream(ByteArrayOutputStream os) {
		BinaryWriter.float32(os, value);
	}

	public void setValue(Float value) {
		this.value = value;
	}
	
}
