package com.rr.rrol.game.objects;

import com.rr.rrol.se.model.property.item.Point3D;

public class Watertower {

	private int type;
	private Point3D location;
	private Point3D rotation;
	private Float waterLevel;
	
	public Watertower(int type, Point3D location, Point3D rotation, Float waterLevel) {
		super();
		this.type = type;
		this.location = location;
		this.rotation = rotation;
		this.waterLevel = waterLevel;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Point3D getLocation() {
		return location;
	}

	public void setLocation(Point3D location) {
		this.location = location;
	}

	public Point3D getRotation() {
		return rotation;
	}

	public void setRotation(Point3D rotation) {
		this.rotation = rotation;
	}

	public Float getWaterLevel() {
		return waterLevel;
	}

	public void setWaterLevel(Float waterLevel) {
		this.waterLevel = waterLevel;
	}
	
}
