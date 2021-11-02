package com.rr.rrol.game.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.rr.rrol.game.objects.enums.SplineType;
import com.rr.rrol.game.objects.enums.SwitchState;
import com.rr.rrol.game.objects.enums.SwitchType;
import com.rr.rrol.se.model.Save;
import com.rr.rrol.se.model.property.ArrayProperty;
import com.rr.rrol.se.model.property.Property;
import com.rr.rrol.se.model.property.PropertyName;
import com.rr.rrol.se.model.property.StrProperty;
import com.rr.rrol.se.model.property.item.Point3D;

public class Parser {

	private Save save;
	
	public Parser(Save save) {
		this.save = save;
	}
	
	@SuppressWarnings("unchecked")
	public List<Switch> getSwitches() throws Exception {
		ArrayProperty<Point3D> switchLocations = getArrayProperty(PropertyName.SwitchLocationArray);
		ArrayProperty<Point3D> switchRotations = getArrayProperty(PropertyName.SwitchRotationArray);
		ArrayProperty<Integer> switchStates = getArrayProperty(PropertyName.SwitchStateArray);
		ArrayProperty<Integer> switchTypes = getArrayProperty(PropertyName.SwitchTypeArray);
		
		List<Switch> switches = new ArrayList<>();
		for(int i=0; i<switchLocations.getValue().size(); i++) {
			Point3D location = switchLocations.getValue().get(i);
			Point3D rotation = switchRotations.getValue().get(i);
			Integer s = switchStates.getValue().get(i);
			SwitchState state = SwitchState.values()[s];
			Integer t = switchTypes.getValue().get(i);
			SwitchType type = SwitchType.values()[t];
			Switch sw = new Switch(type, location, rotation, state);
			switches.add(sw);
		}
		return switches;
	}
	
	@SuppressWarnings("unchecked")
	public List<Spline> getSplines() throws Exception {
		ArrayProperty<Point3D> controlPoints = getArrayProperty(PropertyName.SplineControlPointsArray);
		ArrayProperty<Integer> controlPointsIndexEnd = getArrayProperty(PropertyName.SplineControlPointsIndexEndArray);
		ArrayProperty<Integer> controlPointsIndexStart = getArrayProperty(PropertyName.SplineControlPointsIndexStartArray);
		ArrayProperty<Point3D> location = getArrayProperty(PropertyName.SplineLocationArray);
		ArrayProperty<Boolean> segmentsVisibility = getArrayProperty(PropertyName.SplineSegmentsVisibilityArray);
		ArrayProperty<Integer> type = getArrayProperty(PropertyName.SplineTypeArray);
		ArrayProperty<Integer> visibilityEnd = getArrayProperty(PropertyName.SplineVisibilityEndArray);
		ArrayProperty<Integer> visibilityStart = getArrayProperty(PropertyName.SplineVisibilityStartArray);
		
		List<Spline> splines = new ArrayList<>();
		
		for(int i=0; i<location.getValue().size(); i++) {
			Point3D loc = location.getValue().get(i);
			Integer t = type.getValue().get(i);
			SplineType sType = SplineType.values()[t];
			Integer is = controlPointsIndexStart.getValue().get(i);
			Integer ie = controlPointsIndexEnd.getValue().get(i);
			Integer vs = visibilityStart.getValue().get(i);
			Integer ve = visibilityEnd.getValue().get(i);
			
			splines.add(new Spline(loc, sType, is, ie, vs, ve, controlPoints, segmentsVisibility, i));			
		}
		
		return splines;
	}

	@SuppressWarnings("rawtypes")
	public ArrayProperty getArrayProperty(PropertyName propertyName) throws Exception {
		Optional<Property> opt = save.getProperties().stream().filter(p -> p.getPropertyName().equals(propertyName)).findFirst();
		if(opt.isPresent()) {
			if(opt.get() instanceof ArrayProperty) {
				return (ArrayProperty)opt.get();
			} else {
				throw new Exception("PropertyName "+propertyName+" is not an ArrayProperty");
			}
		}
		throw new Exception("PropertyName "+propertyName+" not found");
	}

	@SuppressWarnings("rawtypes")
	public StrProperty getStrProperty(PropertyName propertyName) throws Exception {
		Optional<Property> opt = save.getProperties().stream().filter(p -> p.getPropertyName().equals(propertyName)).findFirst();
		if(opt.isPresent()) {
			if(opt.get() instanceof StrProperty) {
				return (StrProperty)opt.get();
			} else {
				throw new Exception("PropertyName "+propertyName+" is not an StrProperty");
			}
		}
		throw new Exception("PropertyName "+propertyName+" not found");
	}
	
}
