package com.rr.rrol.se.io;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.rr.rrol.game.objects.Player;
import com.rr.rrol.game.objects.Spline;
import com.rr.rrol.game.objects.SplineSegment;
import com.rr.rrol.game.objects.Switch;
import com.rr.rrol.game.objects.Vehicle;
import com.rr.rrol.game.objects.enums.SplineType;
import com.rr.rrol.game.objects.enums.SwitchState;
import com.rr.rrol.game.objects.enums.SwitchType;
import com.rr.rrol.se.model.CustomFormatDataEntry;
import com.rr.rrol.se.model.Save;
import com.rr.rrol.se.model.property.ArrayProperty;
import com.rr.rrol.se.model.property.Property;
import com.rr.rrol.se.model.property.PropertyFactory;
import com.rr.rrol.se.model.property.PropertyName;
import com.rr.rrol.se.model.property.StrProperty;
import com.rr.rrol.se.model.property.item.Point3D;

public class SaveReader {

	private SaveReader() {}
	
	public static Save read(InputStream in) throws Exception {
		Save save = new Save();
		BinaryReader reader = new BinaryReader(in);
		
		byte[] checkHeader = new byte[Save.header.length];
		reader.get(checkHeader);
		
		if(!new String(Save.header).equals(new String(checkHeader))) {
			throw new Exception("Invalid header, expected "+new String(Save.header));
		}
		
		save.setSaveGameVersion(reader.readInt32());
		save.setPackageVersion(reader.readInt32());		
		save.setMajor(reader.readInt16());
		save.setMinor(reader.readInt16());
		save.setPatch(reader.readInt16());
		save.setBuild(reader.readInt32());
		save.setBuildId(reader.readString());	
		save.setCustomFormatVersion(reader.readInt32());
		save.setCustomFormatCount(reader.readInt32());
		
		for(int i=0; i<save.getCustomFormatCount(); i++) {
			CustomFormatDataEntry entry = new CustomFormatDataEntry();
			entry.setGuid(reader.uuid());
			entry.setValue(reader.readInt32());			
			save.getCustomFormatData().add(entry);
		}
		
		save.setSaveGameType(reader.readString());

		List<Property> properties = new ArrayList<>();
		@SuppressWarnings("rawtypes")
		Property property = PropertyFactory.read(reader);
		while(property != null) {
			properties.add(property);
			property = PropertyFactory.read(reader);
		}
		
		save.setProperties(properties);
		
		save.setSaveGameDate(getSaveGameDate(properties));
		save.setPlayers(getPlayers(properties)); 
		save.setSplines(getSplines(properties));
		save.setSwitches(getSwitches(properties));
		
		save.setVehicles(getVehicles(properties));
		
		return save;
	}
	
	@SuppressWarnings("rawtypes")
	private static String getSaveGameDate(List<Property> properties) throws Exception {
		return getStrProperty(properties, PropertyName.SaveGameDate).getValue();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<Player> getPlayers(List<Property> properties) throws Exception {
		ArrayProperty<String> names = getArrayProperty(properties, PropertyName.PlayerNameArray);
		ArrayProperty<Point3D> locations = getArrayProperty(properties, PropertyName.PlayerLocationArray);
		ArrayProperty<Float> money = getArrayProperty(properties, PropertyName.PlayerMoneyArray);
		ArrayProperty<Float> rotation = getArrayProperty(properties, PropertyName.PlayerRotationArray);
		ArrayProperty<Integer> xps = getArrayProperty(properties, PropertyName.PlayerXPArray);
		
		List<Player> players = new ArrayList<>();		
		for(int i=0;i<names.getValue().size(); i++) {
			String name = names.getValue().get(i);
			Point3D location = locations.getValue().get(i);
			Float m = money.getValue().get(i);
			Float r = rotation.getValue().get(i);
			Integer xp = xps.getValue().get(i);
			players.add(new Player(name, location, r, m, xp));
		}
		return players;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Spline> getSplines(List<Property> properties) throws Exception {
		ArrayProperty<Point3D> controlPoints = getArrayProperty(properties, PropertyName.SplineControlPointsArray);
		ArrayProperty<Integer> controlPointsIndexEnd = getArrayProperty(properties, PropertyName.SplineControlPointsIndexEndArray);
		ArrayProperty<Integer> controlPointsIndexStart = getArrayProperty(properties, PropertyName.SplineControlPointsIndexStartArray);
		ArrayProperty<Point3D> location = getArrayProperty(properties, PropertyName.SplineLocationArray);
		ArrayProperty<Boolean> segmentsVisibility = getArrayProperty(properties, PropertyName.SplineSegmentsVisibilityArray);
		ArrayProperty<Integer> type = getArrayProperty(properties, PropertyName.SplineTypeArray);
//		ArrayProperty<Integer> visibilityEnd = getArrayProperty(properties, PropertyName.SplineVisibilityEndArray);
//		ArrayProperty<Integer> visibilityStart = getArrayProperty(properties, PropertyName.SplineVisibilityStartArray);
		
		List<Spline> splines = new ArrayList<>();
		for(int i=0; i<location.getValue().size(); i++) {
			Point3D loc = location.getValue().get(i);
			Integer t = type.getValue().get(i);
			SplineType sType = SplineType.values()[t];
			Integer is = controlPointsIndexStart.getValue().get(i);
			Integer ie = controlPointsIndexEnd.getValue().get(i);
//			Integer vs = visibilityStart.getValue().get(i);
//			Integer ve = visibilityEnd.getValue().get(i);
			
			Spline spline = new Spline(loc, sType);
			for(int j=is; j<ie; j++) {
				SplineSegment ss = new SplineSegment((Point3D)controlPoints.getItems().get(j).getValue(), 
						(Point3D)controlPoints.getItems().get(j+1).getValue(), 
						(Boolean)segmentsVisibility.getItems().get(j-i).getValue());
				spline.getSegments().add(ss);
			}
			splines.add(spline);
		}
		
		return splines;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Switch> getSwitches(List<Property> properties) throws Exception {
		ArrayProperty<Point3D> switchLocations = getArrayProperty(properties, PropertyName.SwitchLocationArray);
		ArrayProperty<Point3D> switchRotations = getArrayProperty(properties, PropertyName.SwitchRotationArray);
		ArrayProperty<Integer> switchStates = getArrayProperty(properties, PropertyName.SwitchStateArray);
		ArrayProperty<Integer> switchTypes = getArrayProperty(properties, PropertyName.SwitchTypeArray);
		
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<Vehicle> getVehicles(List<Property> properties) throws Exception {
		ArrayProperty<String> types = getArrayProperty(properties, PropertyName.FrameTypeArray);
		ArrayProperty<Point3D> locations = getArrayProperty(properties, PropertyName.FrameLocationArray);
		ArrayProperty<Point3D> rotations = getArrayProperty(properties, PropertyName.FrameRotationArray);
		ArrayProperty<String> numbers = getArrayProperty(properties, PropertyName.FrameNumberArray);
		ArrayProperty<String> names = getArrayProperty(properties, PropertyName.FrameNameArray);
		ArrayProperty<Integer> smokestackTypes = getArrayProperty(properties, PropertyName.SmokestackTypeArray);
		ArrayProperty<Integer> headlightTypes = getArrayProperty(properties, PropertyName.HeadlightTypeArray);
		ArrayProperty<Float> boilerFuelAmounts = getArrayProperty(properties, PropertyName.BoilerFuelAmountArray);
		ArrayProperty<Float> boilerFireTemps = getArrayProperty(properties, PropertyName.BoilerFireTempArray);
		ArrayProperty<Float> boilerWaterTemps = getArrayProperty(properties, PropertyName.BoilerWaterTempArray);
		ArrayProperty<Float> boilerWaterLevels = getArrayProperty(properties, PropertyName.BoilerWaterLevelArray);
		ArrayProperty<Float> boilerPressures = getArrayProperty(properties, PropertyName.BoilerPressureArray);
		ArrayProperty<Boolean> headlightFrontStates = getArrayProperty(properties, PropertyName.HeadlightFrontStateArray);
		ArrayProperty<Boolean> headlightRearStates = getArrayProperty(properties, PropertyName.HeadlightRearStateArray);
		ArrayProperty<Boolean> couplerFrontStates = getArrayProperty(properties, PropertyName.CouplerFrontStateArray);
		ArrayProperty<Boolean> couplerRearStates = getArrayProperty(properties, PropertyName.CouplerRearStateArray);
		ArrayProperty<Float> tenderFuelAmounts = getArrayProperty(properties, PropertyName.TenderFuelAmountArray);
		ArrayProperty<Float> tenderWaterAmounts = getArrayProperty(properties, PropertyName.TenderWaterAmountArray);
		ArrayProperty<Float> compressorAirPressures = getArrayProperty(properties, PropertyName.CompressorAirPressureArray);
		ArrayProperty<Integer> markerLightFrontRightStates = getArrayProperty(properties, PropertyName.MarkerLightsFrontRightStateArray);
		ArrayProperty<Integer> markerLightFrontLeftStates = getArrayProperty(properties, PropertyName.MarkerLightsFrontLeftStateArray);
		ArrayProperty<Integer> markerLightRearRightStates = getArrayProperty(properties, PropertyName.MarkerLightsRearRightStateArray);
		ArrayProperty<Integer> markerLightRearLeftStates = getArrayProperty(properties, PropertyName.MarkerLightsRearLeftStateArray);
		ArrayProperty<String> freightTypes = getArrayProperty(properties, PropertyName.FreightTypeArray);
		ArrayProperty<Integer> freightAmounts = getArrayProperty(properties, PropertyName.FreightAmountArray);
		ArrayProperty<Float> regulatorValues = getArrayProperty(properties, PropertyName.RegulatorValueArray);
		ArrayProperty<Float> brakeValues = getArrayProperty(properties, PropertyName.BrakeValueArray);
		ArrayProperty<Float> generatorValues = getArrayProperty(properties, PropertyName.GeneratorValveValueArray);
		ArrayProperty<Float> compressorValues = getArrayProperty(properties, PropertyName.CompressorValveValueArray);
		ArrayProperty<Float> reverserValues = getArrayProperty(properties, PropertyName.ReverserValueArray);
		
		List<Vehicle> vehicles = new ArrayList<>();		
		for(int i=0;i<types.getValue().size(); i++) {
			Vehicle v = new Vehicle();
			v.setType(types.getValue().get(i));
			v.setLocation(locations.getValue().get(i));
			v.setRotation(rotations.getValue().get(i));
			v.setNumber(numbers.getValue().get(i));
			v.setName(names.getValue().get(i));
			v.setSmokestackType(smokestackTypes.getValue().get(i));
			v.setHeadlightType(headlightTypes.getValue().get(i));
			v.setBoilerFuelAmount(boilerFuelAmounts.getValue().get(i));
			v.setBoilerFireTemp(boilerFireTemps.getValue().get(i));
			v.setBoilerWaterTemp(boilerWaterTemps.getValue().get(i));
			v.setBoilerWaterLevel(boilerWaterLevels.getValue().get(i));
			v.setBoilerPressure(boilerPressures.getValue().get(i));
			v.setHeadlightFrontState(headlightFrontStates.getValue().get(i));
			v.setHeadlightRearState(headlightRearStates.getValue().get(i));
			v.setCouplerFrontState(couplerFrontStates.getValue().get(i));
			v.setCouplerRearState(couplerRearStates.getValue().get(i));
			v.setTenderFuelAmount(tenderFuelAmounts.getValue().get(i));
			v.setTenderWaterAmount(tenderWaterAmounts.getValue().get(i));
			v.setCompressorAirPressure(compressorAirPressures.getValue().get(i));
			v.setMarkerLightsFrontRightState(markerLightFrontRightStates.getValue().get(i));
			v.setMarkerLightsFrontLeftState(markerLightFrontLeftStates.getValue().get(i));
			v.setMarkerLightsRearRightState(markerLightRearRightStates.getValue().get(i));
			v.setMarkerLightsRearLeftState(markerLightRearLeftStates.getValue().get(i));
			v.setFreightType(freightTypes.getValue().get(i));
			v.setFreightAmount(freightAmounts.getValue().get(i));
			v.setRegulatorValue(regulatorValues.getValue().get(i));
			v.setBrakeValue(brakeValues.getValue().get(i));
			v.setGeneratorValue(generatorValues.getValue().get(i));
			v.setCompressorValue(compressorValues.getValue().get(i));
			v.setReverserValue(reverserValues.getValue().get(i));
			vehicles.add(v);
		}
		return vehicles;
	}

	@SuppressWarnings("rawtypes")
	private static ArrayProperty getArrayProperty(List<Property> properties, PropertyName propertyName) throws Exception {
		Optional<Property> opt = properties.stream().filter(p -> p.getPropertyName().equals(propertyName)).findFirst();
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
	private static StrProperty getStrProperty(List<Property> properties, PropertyName propertyName) throws Exception {
		Optional<Property> opt = properties.stream().filter(p -> p.getPropertyName().equals(propertyName)).findFirst();
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
