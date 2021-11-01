package com.rr.rrol.se.model.property;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

@SuppressWarnings("rawtypes")
public class ArrayProperty<T> extends Property<List> {
	
	private ItemType itemType;
	private List<Item> items;

	public ArrayProperty(BinaryReader reader, PropertyType propertyType, PropertyName propertyName, long length, ItemType type) throws Exception {
		super(reader, propertyType, propertyName, length);
		
//		itemType = ItemType.valueOf(reader.readString());
		itemType = type;
		terminator();
		int count = reader.readInt32();

		if(itemType.equals(ItemType.StrProperty)) {
			items = new ArrayList<>();
			for(int i=0; i<count; i++) {
				items.add(new StrItem(reader, itemType, null));
			}
		}
		if(itemType.equals(ItemType.StructProperty)) {
			items = new ArrayList<>();
			ItemName itemName = ItemName.valueOf(reader.readString());
			ItemType itemType = ItemType.valueOf(reader.readString());
			@SuppressWarnings("unused")
			long itemLength = reader.readInt64();
			@SuppressWarnings("unused")
			ItemSubType subType = ItemSubType.valueOf(reader.readString());
			terminator();
			@SuppressWarnings("unused")
			String uuid = reader.uuid();
			for(int i=0; i<count; i++) {
				items.add(new StructItem(reader, itemType, itemName));
			}
		}
		if(itemType.equals(ItemType.FloatProperty)) {
			items = new ArrayList<>();
			for(int i=0; i<count; i++) {
				items.add(new FloatItem(reader, null, null));
			}
		}
		if(itemType.equals(ItemType.IntProperty)) {
			items = new ArrayList<>();
			for(int i=0; i<count; i++) {
				items.add(new IntItem(reader, null, null));
			}
		}
		if(itemType.equals(ItemType.BoolProperty)) {
			items = new ArrayList<>();
			for(int i=0; i<count; i++) {
				items.add(new BoolItem(reader, null, null));
			}
		}
		if(itemType.equals(ItemType.TextProperty)) {
			items = new ArrayList<>();
			for(int i=0; i<count; i++) {
				items.add(new TextItem(reader, null, null));
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getValue() {
		return (List<T>)items.stream().map(x -> ((Item)x).getValue()).collect(Collectors.toList());
	}

}
