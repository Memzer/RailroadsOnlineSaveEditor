package com.rr.rrol.se.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rr.rrol.se.reader.BinaryReader;
import com.rr.rrol.se.reader.BinaryWriter;

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
    
    public void toByteArrayOutputStream(ByteArrayOutputStream os) throws IOException {
    	BinaryWriter.int32(os, count);
    	for(CustomFormatDataEntry entry : entries) {
    		entry.toByteArrayOutputStream(os);
    	}
    }

	public int getCount() {
		return count;
	}

	public List<CustomFormatDataEntry> getEntries() {
		return entries;
	}
}
