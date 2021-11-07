package com.rr.rrol.se.model.property;

import com.rr.rrol.se.io.BinaryReader;
import com.rr.rrol.se.model.property.item.ItemType;
import com.rr.rrol.se.model.property.item.Point3D;

public class PropertyFactory {

	@SuppressWarnings("rawtypes")
	public static Property read(BinaryReader reader) throws Exception {
		if(reader.remaining() <= 0) {
			return null;
		}
		
		PropertyName pName = PropertyName.valueOf(reader.readString());
		String type = reader.readString();
		PropertyType pType = type==null?null:PropertyType.valueOf(type);

		if(pType == null) {
			return new NoneProperty(reader, pType, pName, 0);
		}

		long length = reader.readInt64();		
		
		if(pType.equals(PropertyType.StrProperty)) {
			return new StrProperty(reader, pType, pName, length);
		}
		if(pType.equals(PropertyType.ArrayProperty)) {
			ItemType itemType = ItemType.valueOf(reader.readString());
			if(itemType.equals(ItemType.BoolProperty)) {
				return new ArrayProperty<Boolean>(reader, pType, pName, length, itemType);
			}
			if(itemType.equals(ItemType.IntProperty)) {
				return new ArrayProperty<Integer>(reader, pType, pName, length, itemType);
			}
			if(itemType.equals(ItemType.FloatProperty)) {
				return new ArrayProperty<Float>(reader, pType, pName, length, itemType);
			}
			if(itemType.equals(ItemType.StructProperty)) {
				return new ArrayProperty<Point3D>(reader, pType, pName, length, itemType);
			}
			if(itemType.equals(ItemType.StrProperty)) {
				return new ArrayProperty<String>(reader, pType, pName, length, itemType);
			}
			if(itemType.equals(ItemType.TextProperty)) {
				return new ArrayProperty<String>(reader, pType, pName, length, itemType);
			}
//			return new ArrayProperty(reader, pType, pName, length);
		}
		
		return null;
	}
	
}
