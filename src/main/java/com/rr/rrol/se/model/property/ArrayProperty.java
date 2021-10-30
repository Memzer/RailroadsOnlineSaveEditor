package com.rr.rrol.se.model.property;

import java.util.ArrayList;
import java.util.List;

import com.rr.rrol.se.model.property.item.BoolItem;
import com.rr.rrol.se.model.property.item.FloatItem;
import com.rr.rrol.se.model.property.item.IntItem;
import com.rr.rrol.se.model.property.item.Item;
import com.rr.rrol.se.model.property.item.ItemName;
import com.rr.rrol.se.model.property.item.ItemSubType;
import com.rr.rrol.se.model.property.item.ItemType;
import com.rr.rrol.se.model.property.item.StrItem;
import com.rr.rrol.se.model.property.item.StructItem;
import com.rr.rrol.se.model.property.item.TextItem;
import com.rr.rrol.se.reader.BinaryReader;

public class ArrayProperty extends Property {
	
	private ItemType itemType;
	private List<Item> items;

	public ArrayProperty(BinaryReader reader, PropertyType propertyType, PropertyName propertyName, long length) throws Exception {
		super(reader, propertyType, propertyName, length);
		
		itemType = ItemType.valueOf(reader.readString());
		terminator();
		int count = reader.readInt32();
		items = new ArrayList<>();
		
		if(itemType.equals(ItemType.StrProperty)) {
			for(int i=0; i<count; i++) {
				items.add(new StrItem(reader, itemType, null));
			}
		}
		if(itemType.equals(ItemType.StructProperty)) {
			ItemName itemName = ItemName.valueOf(reader.readString());
			ItemType itemType = ItemType.valueOf(reader.readString());
			long itemLength = reader.readInt64();
			ItemSubType subType = ItemSubType.valueOf(reader.readString());
			terminator();
			String uuid = reader.uuid();
			for(int i=0; i<count; i++) {
				items.add(new StructItem(reader, itemType, itemName));
			}
		}
		if(itemType.equals(ItemType.FloatProperty)) {
			for(int i=0; i<count; i++) {
				items.add(new FloatItem(reader, null, null));
			}
		}
		if(itemType.equals(ItemType.IntProperty)) {
			for(int i=0; i<count; i++) {
				items.add(new IntItem(reader, null, null));
			}
		}
		if(itemType.equals(ItemType.BoolProperty)) {
			for(int i=0; i<count; i++) {
				items.add(new BoolItem(reader, null, null));
			}
		}
		if(itemType.equals(ItemType.TextProperty)) {
			for(int i=0; i<count; i++) {
				items.add(new TextItem(reader, null, null));
			}
		}
	}

	public List<Item> getItems() {
		return items;
	}

}
