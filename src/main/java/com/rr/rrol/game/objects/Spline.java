package com.rr.rrol.game.objects;

import java.util.ArrayList;
import java.util.List;

import com.rr.rrol.game.objects.enums.SplineType;
import com.rr.rrol.se.model.property.ArrayProperty;
import com.rr.rrol.se.model.property.item.Point3D;

public class Spline {

	private SplineType type;
    
    private List<SplineSegment> segments;
    
    public Spline(Point3D location, SplineType type) {
		super();
		this.type = type;
    	segments = new ArrayList<>();
    }
    
	public Spline(Point3D location, SplineType type, Integer controlPointIndexStart, Integer controlPointIndexEnd, 
			Integer visibilityIndexStart, Integer visibilityIndexEnd, ArrayProperty<Point3D> controlPoints, 
			ArrayProperty<Boolean> segmentsVisibility, int index) throws Exception {
		super();
		this.type = type;
		
		if (controlPointIndexStart == null || controlPointIndexEnd == null) {
            throw new Exception("Unable to parse control points, start and end indexes not set!");
        }

        if (visibilityIndexStart == null || visibilityIndexEnd == null) {
            throw new Exception("Unable to parse visibility points, start and end indexes not set!");
        }

        if (controlPointIndexStart != visibilityIndexStart + index || controlPointIndexEnd != visibilityIndexEnd + index + 1) {
            throw new Exception("Visibility segments count and control points count not same!");
        }
        
        Point3D firstControlPoint = (Point3D)controlPoints.getValue().get(controlPointIndexStart);
        if (firstControlPoint.getX() != location.getX() || 
        		firstControlPoint.getY() != location.getY() || 
        		firstControlPoint.getZ() != location.getZ()) {
            throw new Exception("First control point and spline location are different!");
        }
        
        segments = new ArrayList<>();
        for(int i=controlPointIndexStart; i<controlPointIndexEnd; i++) {
        	segments.add(new SplineSegment((Point3D)controlPoints.getValue().get(i),
        			(Point3D)controlPoints.getValue().get(i+1),
        			((Boolean)segmentsVisibility.getValue().get(i-index))));
        }
	}

	public SplineType getType() {
		return type;
	}

	public List<SplineSegment> getSegments() {
		return segments;
	}
    
}
