package com.rr.rrol.se.model.property;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.rr.rrol.se.io.BinaryReader;
import com.rr.rrol.se.model.Guid;
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

@SuppressWarnings("rawtypes")
public class ArrayProperty<T> extends Property<List> {
	
	private ItemType itemType;
	private List<Item> items;
	private int count;
	
	private ItemType itemType2;
	private ItemName itemName2;
	private long length2;
	private ItemSubType subType;
	private Guid guid;

	public ArrayProperty(BinaryReader reader, PropertyType propertyType, PropertyName propertyName, long length, ItemType type) throws Exception {
		super(reader, propertyType, propertyName, length);
		
//		itemType = ItemType.valueOf(reader.readString());
		itemType = type;
		terminator();
		count = reader.readInt32();

		if(itemType.equals(ItemType.StrProperty)) {
			items = new ArrayList<>();
			for(int i=0; i<count; i++) {
				items.add(new StrItem(reader, itemType, null));
			}
		}
		if(itemType.equals(ItemType.StructProperty)) {
			items = new ArrayList<>();
			itemName2 = ItemName.valueOf(reader.readString());
			itemType2 = ItemType.valueOf(reader.readString());
			length2 = reader.readInt64();
			subType = ItemSubType.valueOf(reader.readString());
			terminator();
			guid = reader.uuid();
			for(int i=0; i<count; i++) {
				items.add(new StructItem(reader, itemType2, itemName2));
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

	public List<Item> getItems() {
		return items;
	}

	@Override
	public String toString() {
		return "ArrayProperty:"+propertyName.name();
	}

}
