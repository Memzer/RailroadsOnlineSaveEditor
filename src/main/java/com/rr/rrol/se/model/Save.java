package com.rr.rrol.se.model;

import java.util.ArrayList;
import java.util.List;

import com.rr.rrol.se.model.property.Property;
import com.rr.rrol.se.model.property.PropertyFactory;
import com.rr.rrol.se.reader.BinaryReader;

public class Save {

	public static byte[] header = "GVAS".getBytes();
	
	private int saveGameVersion;
	private int packageVersion;
	
	private EngineVersion engineVersion;
	
	private int customFormatVersion;
	private CustomFormatData customFormatData;
	
	private String saveGameType;
	
	private List<Property> properties;
	
	public Save(BinaryReader reader) throws Exception {
		byte[] checkHeader = new byte[Save.header.length];
		reader.get(checkHeader);
		
		if(!new String(Save.header).equals(new String(checkHeader))) {
			throw new Exception("Invalid header, expected "+new String(Save.header));
		}
		
		saveGameVersion = reader.readInt32();
		packageVersion = reader.readInt32();
		
		engineVersion = new EngineVersion(reader);
		
		customFormatVersion = reader.readInt32();
		
		customFormatData = new CustomFormatData(reader);
		
		saveGameType = reader.readString();
		
		properties = new ArrayList<>();
		Property property = PropertyFactory.read(reader);
		while(property != null) {
			properties.add(property);
			property = PropertyFactory.read(reader);
		}
	}

	public int getSaveGameVersion() {
		return saveGameVersion;
	}

	public int getPackageVersion() {
		return packageVersion;
	}

	public EngineVersion getEngineVersion() {
		return engineVersion;
	}

	public int getCustomFormatVersion() {
		return customFormatVersion;
	}

	public CustomFormatData getCustomFormatData() {
		return customFormatData;
	}

	public String getSaveGameType() {
		return saveGameType;
	}

	public List<Property> getProperties() {
		return properties;
	}
	
}
