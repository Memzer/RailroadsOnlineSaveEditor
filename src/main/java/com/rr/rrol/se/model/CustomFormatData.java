package com.rr.rrol.se.model;

import java.util.ArrayList;
import java.util.List;

import com.rr.rrol.se.reader.BinaryReader;

public class CustomFormatData {

	public int count;
	public List<CustomFormatDataEntry> entries;
	
	public CustomFormatData(BinaryReader reader) {
		count = reader.readInt32();
		entries = new ArrayList<>();
		for(int i=0; i<count; i++) {
			entries.add(new CustomFormatDataEntry(reader));
		}
	}

	public int getCount() {
		return count;
	}

	public List<CustomFormatDataEntry> getEntries() {
		return entries;
	}
}
