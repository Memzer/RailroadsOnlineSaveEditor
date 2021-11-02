package com.rr.rrol.se.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rr.rrol.se.model.property.Property;
import com.rr.rrol.se.model.property.PropertyFactory;
import com.rr.rrol.se.reader.BinaryReader;
import com.rr.rrol.se.reader.BinaryWriter;

public class Save {

	public static byte[] header = "GVAS".getBytes();
	
	private int saveGameVersion;
	private int packageVersion;
	
	private EngineVersion engineVersion;
	
	private int customFormatVersion;
	private CustomFormatData customFormatData;
	
	private String saveGameType;
	
	@SuppressWarnings("rawtypes")
	private List<Property> properties;

	@SuppressWarnings("rawtypes")
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
	
	public ByteArrayOutputStream toByteArrayOutputStream() throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		os.writeBytes(header);
		BinaryWriter.int32(os, saveGameVersion);
		BinaryWriter.int32(os, packageVersion);
		engineVersion.toByteArrayOutputStream(os);
		BinaryWriter.int32(os, customFormatVersion);
		customFormatData.toByteArrayOutputStream(os);
		BinaryWriter.string(os, saveGameType);
		for(Property property : properties) {
			property.toByteArrayOutputStream(os);
		}
		
		return os;
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

	@SuppressWarnings("rawtypes")
	public List<Property> getProperties() {
		return properties;
	}
	
}
