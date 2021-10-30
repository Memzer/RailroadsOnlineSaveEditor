package com.rr.rrol.se.model;

import com.rr.rrol.se.reader.BinaryReader;

public class EngineVersion {

	public short major;
    public short minor;
    public short patch;
    public int build;
    public String buildId;
    
    public EngineVersion(BinaryReader reader) {
    	major = reader.readInt16();
		minor = reader.readInt16();
		patch = reader.readInt16();
		build = reader.readInt32();
		buildId = reader.readString();
    }

	public short getMajor() {
		return major;
	}

	public short getMinor() {
		return minor;
	}

	public short getPatch() {
		return patch;
	}

	public int getBuild() {
		return build;
	}

	public String getBuildId() {
		return buildId;
	}
    
}
