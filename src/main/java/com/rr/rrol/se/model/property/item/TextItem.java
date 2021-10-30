package com.rr.rrol.se.model.property.item;

import java.util.ArrayList;
import java.util.List;

import com.rr.rrol.se.reader.BinaryReader;

public class TextItem extends Item {

	private String value; 
	
	public TextItem(BinaryReader reader, ItemType itemType, ItemName itemName) throws Exception {
		super(reader, itemType, itemName);
//		System.out.println(Integer.toHexString(reader.position()));
		byte opt = reader.get();
		long flags = reader.readInt64();
		if(opt == 1) {
			byte[] b1 = reader.readBytes(5);
			String type = reader.readString();
			String separator = reader.readString();
			int numRows = reader.readInt32();
			
			List<Object[]> rowsData = new ArrayList<>();
			
			for(int i=0; i<numRows; i++) {
				String rowId = reader.readString();
				byte rowType = reader.get();
				if(rowType != 4) {
					throw new Exception("Row type must be 4");
				}				
				String v = new TextItem(reader, null, null).getValue();				
				//Storing rowId and rowType, but we're only really interested in the value
				Object[] row = new Object[] {rowId, rowType, v};				
				rowsData.add(row);
			}
			//Build string value, using the array and separator
			StringBuilder sb = new StringBuilder();
			for(Object[] tr : rowsData) {
				if(tr[2] != null) {
					sb.append(tr[2]).append(separator);
				}
			}
			value = sb.toString();			
		} else if(opt == 2) {
			if(((flags >> 32) & 0xFFFFFFFF) != 0) {
				value = reader.readString();
			}
		}
	}

	public String getValue() {
		return value;
	}
	
}
