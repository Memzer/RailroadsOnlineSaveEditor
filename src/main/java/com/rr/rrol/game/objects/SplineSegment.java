package com.rr.rrol.game.objects;

import com.rr.rrol.se.model.property.item.Point3D;

public class SplineSegment {

	private Point3D start;
	private Point3D end;
	private boolean visible;
	
	public SplineSegment(Point3D start, Point3D end, boolean visible) {
		super();
		this.start = start;
		this.end = end;
		this.visible = visible;
	}
	
	public Point3D getStart() {
		return start;
	}
	public Point3D getEnd() {
		return end;
	}
	public boolean isVisible() {
		return visible;
	}
	
}
