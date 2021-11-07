package com.rr.rrol.se.model.property.item;

public class Point3D {

	private double x,y,z;

	public Point3D(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		return "["+x+", "+y+", "+z+"]";
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
}
