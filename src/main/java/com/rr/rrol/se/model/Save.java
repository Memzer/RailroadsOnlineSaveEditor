package com.rr.rrol.se.model;

import java.util.ArrayList;
import java.util.List;

import com.rr.rrol.game.objects.Player;
import com.rr.rrol.game.objects.Spline;
import com.rr.rrol.game.objects.Switch;
import com.rr.rrol.game.objects.Vehicle;
import com.rr.rrol.se.model.property.Property;

public class Save {

	public static byte[] header = "GVAS".getBytes();
	
	private int saveGameVersion;
	private int packageVersion;	
	private short major;
	private short minor;
	private short patch;
	private int build;
	private String buildId;
	private int customFormatVersion;
	private int customFormatCount;
	private List<CustomFormatDataEntry> customFormatData;
	private String saveGameType;	
	@SuppressWarnings("rawtypes")
	
	@Deprecated
	private List<Property> properties;
	
	private String saveGameDate;	
	private List<Player> players;
	private List<Spline> splines;
	private List<Switch> switches;
	
	private List<Vehicle> vehicles;

	public Save() {
		customFormatData = new ArrayList<>();
	}

	public int getSaveGameVersion() {
		return saveGameVersion;
	}

	public int getPackageVersion() {
		return packageVersion;
	}

	public int getCustomFormatVersion() {
		return customFormatVersion;
	}

	public String getSaveGameType() {
		return saveGameType;
	}

	public void setSaveGameVersion(int saveGameVersion) {
		this.saveGameVersion = saveGameVersion;
	}

	public void setPackageVersion(int packageVersion) {
		this.packageVersion = packageVersion;
	}

	public void setCustomFormatVersion(int customFormatVersion) {
		this.customFormatVersion = customFormatVersion;
	}

	public void setSaveGameType(String saveGameType) {
		this.saveGameType = saveGameType;
	}

	public short getMajor() {
		return major;
	}

	public void setMajor(short major) {
		this.major = major;
	}

	public short getMinor() {
		return minor;
	}

	public void setMinor(short minor) {
		this.minor = minor;
	}

	public short getPatch() {
		return patch;
	}

	public void setPatch(short patch) {
		this.patch = patch;
	}

	public int getBuild() {
		return build;
	}

	public void setBuild(int build) {
		this.build = build;
	}

	public String getBuildId() {
		return buildId;
	}

	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}

	public void setCustomFormatData(List<CustomFormatDataEntry> customFormatData) {
		this.customFormatData = customFormatData;
	}

	public int getCustomFormatCount() {
		return customFormatCount;
	}

	public void setCustomFormatCount(int customFormatCount) {
		this.customFormatCount = customFormatCount;
	}

	public List<CustomFormatDataEntry> getCustomFormatData() {
		return customFormatData;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public String getSaveGameDate() {
		return saveGameDate;
	}

	public void setSaveGameDate(String saveGameDate) {
		this.saveGameDate = saveGameDate;
	}

	public static byte[] getHeader() {
		return header;
	}

	@Deprecated
	public List<Property> getProperties() {
		return properties;
	}

	@Deprecated
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public List<Spline> getSplines() {
		return splines;
	}

	public void setSplines(List<Spline> splines) {
		this.splines = splines;
	}

	public List<Switch> getSwitches() {
		return switches;
	}

	public void setSwitches(List<Switch> switches) {
		this.switches = switches;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	
}
