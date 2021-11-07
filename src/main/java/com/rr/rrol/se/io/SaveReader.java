package com.rr.rrol.se.io;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.rr.rrol.game.objects.Industry;
import com.rr.rrol.game.objects.Player;
import com.rr.rrol.game.objects.Sandhouse;
import com.rr.rrol.game.objects.Spline;
import com.rr.rrol.game.objects.SplineSegment;
import com.rr.rrol.game.objects.Switch;
import com.rr.rrol.game.objects.Turntable;
import com.rr.rrol.game.objects.Vehicle;
import com.rr.rrol.game.objects.Watertower;
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
import com.rr.rrol.se.model.property.item.StrItem;

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
		save.setTurntables(getTurntables(properties));
		save.setWatertowers(getWatertowers(properties));
		save.setSandhouses(getSandhouses(properties));
		save.setIndustries(getIndustries(properties));
		save.setRemovedVegetation(getRemovedVegetation(properties));
		save.setVehicles(getVehicles(properties));
		
		return save;
	}
	
	@SuppressWarnings("rawtypes")
	private static String getSaveGameDate(List<Property> properties) throws Exception {
		return getStrProperty(properties, PropertyName.SaveGameDate).getValue();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<Player> getPlayers(List<Property> properties) {
		List<Player> players = new ArrayList<>();
		try {
			ArrayProperty<String> names = getArrayProperty(properties, PropertyName.PlayerNameArray);
			ArrayProperty<Point3D> locations = getArrayProperty(properties, PropertyName.PlayerLocationArray);
			ArrayProperty<Float> money = getArrayProperty(properties, PropertyName.PlayerMoneyArray);
			ArrayProperty<Float> rotation = getArrayProperty(properties, PropertyName.PlayerRotationArray);
			ArrayProperty<Integer> xps = getArrayProperty(properties, PropertyName.PlayerXPArray);
			
			for(int i=0;i<names.getValue().size(); i++) {
				String name = names.getValue().get(i);
				StrItem item = (StrItem)names.getItems().get(i);
				int len = item.getLength();
				Point3D location = locations.getValue().get(i);
				Float m = money.getValue().get(i);
				Float r = rotation.getValue().get(i);
				Integer xp = xps.getValue().get(i);
				players.add(new Player(name, len, location, r, m, xp));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return players;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Spline> getSplines(List<Property> properties) {
		List<Spline> splines = new ArrayList<>();
		try {
			ArrayProperty<Point3D> controlPoints = getArrayProperty(properties, PropertyName.SplineControlPointsArray);
			ArrayProperty<Integer> controlPointsIndexEnd = getArrayProperty(properties, PropertyName.SplineControlPointsIndexEndArray);
			ArrayProperty<Integer> controlPointsIndexStart = getArrayProperty(properties, PropertyName.SplineControlPointsIndexStartArray);
			ArrayProperty<Point3D> location = getArrayProperty(properties, PropertyName.SplineLocationArray);
			ArrayProperty<Boolean> segmentsVisibility = getArrayProperty(properties, PropertyName.SplineSegmentsVisibilityArray);
			ArrayProperty<Integer> type = getArrayProperty(properties, PropertyName.SplineTypeArray);
	//		ArrayProperty<Integer> visibilityEnd = getArrayProperty(properties, PropertyName.SplineVisibilityEndArray);
	//		ArrayProperty<Integer> visibilityStart = getArrayProperty(properties, PropertyName.SplineVisibilityStartArray);
			
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

		} catch (Exception e) {
			e.printStackTrace();
		}
		return splines;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Switch> getSwitches(List<Property> properties) {		
		List<Switch> switches = new ArrayList<>();
		try {
			ArrayProperty<Point3D> switchLocations = getArrayProperty(properties, PropertyName.SwitchLocationArray);
			ArrayProperty<Point3D> switchRotations = getArrayProperty(properties, PropertyName.SwitchRotationArray);
			ArrayProperty<Integer> switchStates = getArrayProperty(properties, PropertyName.SwitchStateArray);
			ArrayProperty<Integer> switchTypes = getArrayProperty(properties, PropertyName.SwitchTypeArray);
			
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return switches;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Turntable> getTurntables(List<Property> properties) {		
		List<Turntable> turntables = new ArrayList<>();
		try {
			ArrayProperty<Integer> types = getArrayProperty(properties, PropertyName.TurntableTypeArray);
			ArrayProperty<Point3D> locations = getArrayProperty(properties, PropertyName.TurntableLocationArray);
			ArrayProperty<Point3D> rotations = getArrayProperty(properties, PropertyName.TurntableRotatorArray);
			ArrayProperty<Point3D> deckRotations = getArrayProperty(properties, PropertyName.TurntableDeckRotationArray);
			
			for(int i=0; i<types.getValue().size(); i++) {
				Integer type = types.getValue().get(i);
				Point3D location = locations.getValue().get(i);
				Point3D rotation = rotations.getValue().get(i);
				Point3D deckRotation = deckRotations.getValue().get(i);
				Turntable tt = new Turntable(type, location, rotation, deckRotation);
				turntables.add(tt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return turntables;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Watertower> getWatertowers(List<Property> properties) {		
		List<Watertower> watertowers = new ArrayList<>();
		try {
			ArrayProperty<Integer> types = getArrayProperty(properties, PropertyName.WatertowerTypeArray);
			ArrayProperty<Point3D> locations = getArrayProperty(properties, PropertyName.WatertowerLocationArray);
			ArrayProperty<Point3D> rotations = getArrayProperty(properties, PropertyName.WatertowerRotationArray);
			ArrayProperty<Float> waterLevels = getArrayProperty(properties, PropertyName.WatertowerWaterlevelArray);
			
			for(int i=0; i<types.getValue().size(); i++) {
				Integer type = types.getValue().get(i);
				Point3D location = locations.getValue().get(i);
				Point3D rotation = rotations.getValue().get(i);
				Float waterLevel = waterLevels.getValue().get(i);
				Watertower tt = new Watertower(type, location, rotation, waterLevel);
				watertowers.add(tt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return watertowers;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Sandhouse> getSandhouses(List<Property> properties) {		
		List<Sandhouse> sandhouses = new ArrayList<>();
		try {
			ArrayProperty<Integer> types = getArrayProperty(properties, PropertyName.SandhouseTypeArray);
			ArrayProperty<Point3D> locations = getArrayProperty(properties, PropertyName.SandhouseLocationArray);
			ArrayProperty<Point3D> rotations = getArrayProperty(properties, PropertyName.SandhouseRotationArray);
			
			for(int i=0; i<types.getValue().size(); i++) {
				Integer type = types.getValue().get(i);
				Point3D location = locations.getValue().get(i);
				Point3D rotation = rotations.getValue().get(i);
				Sandhouse tt = new Sandhouse(type, location, rotation);
				sandhouses.add(tt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sandhouses;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Industry> getIndustries(List<Property> properties) {		
		List<Industry> industries = new ArrayList<>();
		try {
			ArrayProperty<Integer> types = getArrayProperty(properties, PropertyName.IndustryTypeArray);
			ArrayProperty<Point3D> locations = getArrayProperty(properties, PropertyName.IndustryLocationArray);
			ArrayProperty<Point3D> rotations = getArrayProperty(properties, PropertyName.IndustryRotationArray);
			ArrayProperty<Integer> se1 = getArrayProperty(properties, PropertyName.IndustryStorageEduct1Array);
			ArrayProperty<Integer> se2 = getArrayProperty(properties, PropertyName.IndustryStorageEduct2Array);
			ArrayProperty<Integer> se3 = getArrayProperty(properties, PropertyName.IndustryStorageEduct3Array);
			ArrayProperty<Integer> se4 = getArrayProperty(properties, PropertyName.IndustryStorageEduct4Array);
			ArrayProperty<Integer> sp1 = getArrayProperty(properties, PropertyName.IndustryStorageProduct1Array);
			ArrayProperty<Integer> sp2 = getArrayProperty(properties, PropertyName.IndustryStorageProduct2Array);
			ArrayProperty<Integer> sp3 = getArrayProperty(properties, PropertyName.IndustryStorageProduct3Array);
			ArrayProperty<Integer> sp4 = getArrayProperty(properties, PropertyName.IndustryStorageProduct4Array);
			
			for(int i=0; i<types.getValue().size(); i++) {
				Integer type = types.getValue().get(i);
				Point3D location = locations.getValue().get(i);
				Point3D rotation = rotations.getValue().get(i);
				Integer e1 = se1.getValue().get(i);
				Integer e2 = se2.getValue().get(i);
				Integer e3 = se3.getValue().get(i);
				Integer e4 = se4.getValue().get(i);
				Integer p1 = sp1.getValue().get(i);
				Integer p2 = sp2.getValue().get(i);
				Integer p3 = sp3.getValue().get(i);
				Integer p4 = sp4.getValue().get(i);
				Industry tt = new Industry(type, location, rotation, e1, e2, e3, e4, p1, p2, p3, p4);
				industries.add(tt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return industries;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<Vehicle> getVehicles(List<Property> properties) {		
		List<Vehicle> vehicles = new ArrayList<>();	
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vehicles;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Point3D> getRemovedVegetation(List<Property> properties) {		
		List<Point3D> removedVegetation = new ArrayList<>();
		try {
			ArrayProperty<Point3D> trees = getArrayProperty(properties, PropertyName.RemovedVegetationAssetsArray);
			
			for(int i=0; i<trees.getValue().size(); i++) {
				Point3D location = trees.getValue().get(i);
				removedVegetation.add(location);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return removedVegetation;
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
