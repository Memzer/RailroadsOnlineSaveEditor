package com.rr.rrol.game.objects;

import java.util.ArrayList;
import java.util.List;

import com.rr.rrol.game.objects.enums.SplineType;
import com.rr.rrol.se.model.property.item.Point3D;

public class Spline {

	private Point3D location;
	private SplineType type;
    
    private List<SplineSegment> segments;
    
    public Spline(Point3D location, SplineType type) {
		super();
		this.location = location;
		this.type = type;
    	segments = new ArrayList<>();
    }

	public SplineType getType() {
		return type;
	}

	public List<SplineSegment> getSegments() {
		return segments;
	}

	public Point3D getLocation() {
		return location;
	}

	public void setLocation(Point3D location) {
		this.location = location;
	}

	public void setType(SplineType type) {
		this.type = type;
	}
    
}
