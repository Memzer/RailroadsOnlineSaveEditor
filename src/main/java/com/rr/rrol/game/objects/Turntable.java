package com.rr.rrol.game.objects;

import com.rr.rrol.se.model.property.item.Point3D;

public class Turntable {

	private int type;
	private Point3D location;
	private Point3D rotation;
	private Point3D deckRotation;
	
	public Turntable(int type, Point3D location, Point3D rotation, Point3D deckRotation) {
		super();
		this.type = type;
		this.location = location;
		this.rotation = rotation;
		this.deckRotation = deckRotation;
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

	public Point3D getDeckRotation() {
		return deckRotation;
	}

	public void setDeckRotation(Point3D deckRotation) {
		this.deckRotation = deckRotation;
	}
	
}
