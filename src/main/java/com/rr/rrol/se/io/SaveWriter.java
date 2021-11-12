package com.rr.rrol.se.io;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import com.rr.rrol.se.model.Guid;
import com.rr.rrol.se.model.Save;
import com.rr.rrol.se.model.property.ArrayProperty;
import com.rr.rrol.se.model.property.Property;
import com.rr.rrol.se.model.property.PropertyName;
import com.rr.rrol.se.model.property.PropertyType;
import com.rr.rrol.se.model.property.item.Item;
import com.rr.rrol.se.model.property.item.ItemSubType;
import com.rr.rrol.se.model.property.item.ItemType;
import com.rr.rrol.se.model.property.item.Point3D;
import com.rr.rrol.se.model.property.item.TextItem;

public class SaveWriter {

	private SaveWriter() {}
	
	public static ByteArrayOutputStream write(Save save) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		os.writeBytes(Save.getHeader());
		BinaryWriter.int32(os, save.getSaveGameVersion());
		BinaryWriter.int32(os, save.getPackageVersion());
		BinaryWriter.int16(os, save.getMajor());
		BinaryWriter.int16(os, save.getMinor());
		BinaryWriter.int16(os, save.getPatch());
		BinaryWriter.int32(os, save.getBuild());
		BinaryWriter.string(os, save.getBuildId());
		BinaryWriter.int32(os, save.getCustomFormatVersion());
		BinaryWriter.int32(os, save.getCustomFormatCount());
		
		for(CustomFormatDataEntry entry : save.getCustomFormatData()) {
			BinaryWriter.guid(os, entry.getGuid());
			BinaryWriter.int32(os, entry.getValue());
		}
		
		BinaryWriter.string(os, save.getSaveGameType());
		
		writeSaveGameDate(os, save.getSaveGameDate());
		
		writePlayers(os, save);
		writeSplines(os, save);	
		writeSwitches(os, save);	
		writeTurntables(os, save);
		writeWatertowers(os, save);
		writeSandhouses(os, save);
		writeIndustries(os, save);
		writeVehicles(os, save);
		writeRemovedVegetation(os, save);
		
		BinaryWriter.string(os, "None");
		BinaryWriter.int32(os, 0);
		
		return os;
	}
	
	private static void writeSaveGameDate(ByteArrayOutputStream os, String date) {
		BinaryWriter.string(os, PropertyName.SaveGameDate.name());
		BinaryWriter.string(os, PropertyType.StrProperty.name());
		BinaryWriter.int64(os, 4+date.length()+1L);
		os.write(0);
		BinaryWriter.string(os, date);
	}
	
	private static void writePlayers(ByteArrayOutputStream os, Save save) throws Exception {
		List<Player> players = getList(save, Save::getPlayers);
		if(players.size() == 0) {
			return;
		}
		List<String> playerNames = convert(players, Player::getName);
		List<Integer> playerNameLengths = convert(players, Player::getNameLength);
		long length = 0;
		List<Object> data = new ArrayList<>();
		for(int i=0; i<playerNames.size(); i++) {
			data.add(new StringWrapper(playerNameLengths.get(i), playerNames.get(i)));
			int pnl = playerNameLengths.get(i);
			length += 4+(pnl>0?pnl:-2*pnl);
		}		
		writeArray(os, PropertyName.PlayerNameArray, ItemType.StrProperty, data, length);
		writeArrayStruct(os, PropertyName.PlayerLocationArray, ItemSubType.Vector, convert(players, Player::getLocation));
		writeArray(os, PropertyName.PlayerRotationArray, ItemType.FloatProperty, convert(players, Player::getR));
		writeArray(os, PropertyName.PlayerXPArray, ItemType.IntProperty, convert(players, Player::getXp));
		writeArray(os, PropertyName.PlayerMoneyArray, ItemType.FloatProperty, convert(players, Player::getMoney));
	}
	
	private static void writeSplines(ByteArrayOutputStream os, Save save) throws Exception {
		List<Spline> splines = getList(save, Save::getSplines);
		if(splines.size() == 0) {
			return;
		}
		List<Object> controlPoints = new ArrayList<>();
		List<Object> controlPointsIndexStartArray = new ArrayList<>();
		List<Object> controlPointsIndexEndArray = new ArrayList<>();
		List<Object> segementVisibilityArray = new ArrayList<>();
		List<Object> splineVisibilityStartArray = new ArrayList<>();
		List<Object> splineVisibilityEndArray = new ArrayList<>();
		int controlPointIndexStart = 0;
		int splineVisStart = 0;
		for(Spline spline : splines) {
			splineVisibilityStartArray.add(splineVisStart);
			splineVisStart += spline.getSegments().size();
			splineVisibilityEndArray.add(splineVisStart-1);
			controlPointsIndexStartArray.add(controlPointIndexStart);
			controlPointIndexStart += spline.getSegments().size()+1;
			controlPointsIndexEndArray.add(controlPointIndexStart-1);
			controlPoints.add(spline.getLocation());
			for(SplineSegment seg : spline.getSegments()) {
				controlPoints.add(seg.getEnd());
				segementVisibilityArray.add(seg.isVisible());
			}
		}
		writeArrayStruct(os, PropertyName.SplineLocationArray, ItemSubType.Vector, convert(splines, Spline::getLocation));
		writeArray(os, PropertyName.SplineTypeArray, ItemType.IntProperty, convert(convert(splines, Spline::getType), SplineType::getIndex));
		writeArrayStruct(os, PropertyName.SplineControlPointsArray, ItemSubType.Vector, controlPoints);
		writeArray(os, PropertyName.SplineControlPointsIndexStartArray, ItemType.IntProperty, controlPointsIndexStartArray);
		writeArray(os, PropertyName.SplineControlPointsIndexEndArray, ItemType.IntProperty, controlPointsIndexEndArray);
		writeArray(os, PropertyName.SplineSegmentsVisibilityArray, ItemType.BoolProperty, segementVisibilityArray, segementVisibilityArray.size());
		writeArray(os, PropertyName.SplineVisibilityStartArray, ItemType.IntProperty, splineVisibilityStartArray);
		writeArray(os, PropertyName.SplineVisibilityEndArray, ItemType.IntProperty, splineVisibilityEndArray);
	}
	
	private static void writeSwitches(ByteArrayOutputStream os, Save save) throws Exception {
		List<Switch> switches = getList(save, Save::getSwitches);	
		if(switches.size() == 0) {
			return;
		}	
		writeArray(os, PropertyName.SwitchTypeArray, ItemType.IntProperty, convert(convert(switches, Switch::getType), SwitchType::getIndex));
		writeArrayStruct(os, PropertyName.SwitchLocationArray, ItemSubType.Vector, convert(switches, Switch::getLocation));
		writeArrayStruct(os, PropertyName.SwitchRotationArray, ItemSubType.Rotator, convert(switches, Switch::getRotation));
		writeArray(os, PropertyName.SwitchStateArray, ItemType.IntProperty, convert(convert(switches, Switch::getState), SwitchState::getIndex));
	}
	
	private static void writeTurntables(ByteArrayOutputStream os, Save save) throws Exception {
		List<Turntable> turntables = getList(save, Save::getTurntables);
		if(turntables.size() == 0) {
			return;
		}
		writeArray(os, PropertyName.TurntableTypeArray, ItemType.IntProperty, convert(turntables, Turntable::getType));
		writeArrayStruct(os, PropertyName.TurntableLocationArray, ItemSubType.Vector, convert(turntables, Turntable::getLocation));
		writeArrayStruct(os, PropertyName.TurntableRotatorArray, ItemSubType.Rotator, convert(turntables, Turntable::getRotation));
		writeArrayStruct(os, PropertyName.TurntableDeckRotationArray, ItemSubType.Rotator, convert(turntables, Turntable::getDeckRotation));		
	}
	
	private static void writeWatertowers(ByteArrayOutputStream os, Save save) throws Exception {
		List<Watertower> watertowers = getList(save, Save::getWatertowers);
		if(watertowers.size() == 0) {
			return;
		}
		writeArray(os, PropertyName.WatertowerTypeArray, ItemType.IntProperty, convert(watertowers, Watertower::getType));
		writeArrayStruct(os, PropertyName.WatertowerLocationArray, ItemSubType.Vector, convert(watertowers, Watertower::getLocation));
		writeArrayStruct(os, PropertyName.WatertowerRotationArray, ItemSubType.Rotator, convert(watertowers, Watertower::getRotation));
		writeArray(os, PropertyName.WatertowerWaterlevelArray, ItemType.FloatProperty, convert(watertowers, Watertower::getWaterLevel));		
	}
	
	private static void writeSandhouses(ByteArrayOutputStream os, Save save) throws Exception {
		List<Sandhouse> sandhouses = getList(save, Save::getSandhouses);
		if(sandhouses.size() == 0) {
			return;
		}
		writeArray(os, PropertyName.SandhouseTypeArray, ItemType.IntProperty, convert(sandhouses, Sandhouse::getType));
		writeArrayStruct(os, PropertyName.SandhouseLocationArray, ItemSubType.Vector, convert(sandhouses, Sandhouse::getLocation));
		writeArrayStruct(os, PropertyName.SandhouseRotationArray, ItemSubType.Rotator, convert(sandhouses, Sandhouse::getRotation));		
	}
	
	private static void writeIndustries(ByteArrayOutputStream os, Save save) throws Exception {
		List<Industry> industries = getList(save, Save::getIndustries);
		if(industries.size() == 0) {
			return;
		}
		writeArray(os, PropertyName.IndustryTypeArray, ItemType.IntProperty, convert(industries, Industry::getType));
		writeArrayStruct(os, PropertyName.IndustryLocationArray, ItemSubType.Vector, convert(industries, Industry::getLocation));
		writeArrayStruct(os, PropertyName.IndustryRotationArray, ItemSubType.Rotator, convert(industries, Industry::getRotation));
		writeArray(os, PropertyName.IndustryStorageEduct1Array, ItemType.IntProperty, convert(industries, Industry::getStorageEduct1));
		writeArray(os, PropertyName.IndustryStorageEduct2Array, ItemType.IntProperty, convert(industries, Industry::getStorageEduct2));
		writeArray(os, PropertyName.IndustryStorageEduct3Array, ItemType.IntProperty, convert(industries, Industry::getStorageEduct3));
		writeArray(os, PropertyName.IndustryStorageEduct4Array, ItemType.IntProperty, convert(industries, Industry::getStorageEduct4));
		writeArray(os, PropertyName.IndustryStorageProduct1Array, ItemType.IntProperty, convert(industries, Industry::getStorageProduct1));
		writeArray(os, PropertyName.IndustryStorageProduct2Array, ItemType.IntProperty, convert(industries, Industry::getStorageProduct2));
		writeArray(os, PropertyName.IndustryStorageProduct3Array, ItemType.IntProperty, convert(industries, Industry::getStorageProduct3));
		writeArray(os, PropertyName.IndustryStorageProduct4Array, ItemType.IntProperty, convert(industries, Industry::getStorageProduct4));
	}
	
	private static void writeVehicles(ByteArrayOutputStream os, Save save) throws Exception {
		List<Vehicle> vehicles = getList(save, Save::getVehicles);
		if(vehicles.size() == 0) {
			return;
		}
		long length = 0;
		for(Vehicle v : vehicles) {
			length += 4+v.getType().length()+1;
		}
		ArrayProperty<String> frameNumberArray = getArrayProperty(save.getProperties(), PropertyName.FrameNumberArray);
		ArrayProperty<String> frameNameArray = getArrayProperty(save.getProperties(), PropertyName.FrameNameArray);
		writeArray(os, PropertyName.FrameTypeArray, ItemType.StrProperty, convert(vehicles, Vehicle::getType), length);
		writeArrayStruct(os, PropertyName.FrameLocationArray, ItemSubType.Vector, convert(vehicles, Vehicle::getLocation));
		writeArrayStruct(os, PropertyName.FrameRotationArray, ItemSubType.Rotator, convert(vehicles, Vehicle::getRotation));
		writeArrayText(os, PropertyName.FrameNumberArray, ItemType.TextProperty, frameNumberArray.getItems(), frameNumberArray.getLength());
		writeArrayText(os, PropertyName.FrameNameArray, ItemType.TextProperty, frameNameArray.getItems(), frameNameArray.getLength());
		writeArray(os, PropertyName.SmokestackTypeArray, ItemType.IntProperty, convert(vehicles, Vehicle::getSmokestackType));
		writeArray(os, PropertyName.HeadlightTypeArray, ItemType.IntProperty, convert(vehicles, Vehicle::getHeadlightType));
		writeArray(os, PropertyName.BoilerFuelAmountArray, ItemType.FloatProperty, convert(vehicles, Vehicle::getBoilerFuelAmount));
		writeArray(os, PropertyName.BoilerFireTempArray, ItemType.FloatProperty, convert(vehicles, Vehicle::getBoilerFireTemp));
		writeArray(os, PropertyName.BoilerWaterTempArray, ItemType.FloatProperty, convert(vehicles, Vehicle::getBoilerWaterTemp));
		writeArray(os, PropertyName.BoilerWaterLevelArray, ItemType.FloatProperty, convert(vehicles, Vehicle::getBoilerWaterLevel));
		writeArray(os, PropertyName.BoilerPressureArray, ItemType.FloatProperty, convert(vehicles, Vehicle::getBoilerPressure));
		writeArray(os, PropertyName.HeadlightFrontStateArray, ItemType.BoolProperty, convert(vehicles, Vehicle::getHeadlightFrontState), vehicles.size());
		writeArray(os, PropertyName.HeadlightRearStateArray, ItemType.BoolProperty, convert(vehicles, Vehicle::getHeadlightRearState), vehicles.size());
		writeArray(os, PropertyName.CouplerFrontStateArray, ItemType.BoolProperty, convert(vehicles, Vehicle::getCouplerFrontState), vehicles.size());
		writeArray(os, PropertyName.CouplerRearStateArray, ItemType.BoolProperty, convert(vehicles, Vehicle::getCouplerRearState), vehicles.size());
		writeArray(os, PropertyName.TenderFuelAmountArray, ItemType.FloatProperty, convert(vehicles, Vehicle::getTenderFuelAmount));
		writeArray(os, PropertyName.TenderWaterAmountArray, ItemType.FloatProperty, convert(vehicles, Vehicle::getTenderWaterAmount));
		writeArray(os, PropertyName.CompressorAirPressureArray, ItemType.FloatProperty, convert(vehicles, Vehicle::getCompressorAirPressure));
		writeArray(os, PropertyName.MarkerLightsFrontRightStateArray, ItemType.IntProperty, convert(vehicles, Vehicle::getMarkerLightsFrontRightState));
		writeArray(os, PropertyName.MarkerLightsFrontLeftStateArray, ItemType.IntProperty, convert(vehicles, Vehicle::getMarkerLightsFrontLeftState));
		writeArray(os, PropertyName.MarkerLightsRearRightStateArray, ItemType.IntProperty, convert(vehicles, Vehicle::getMarkerLightsRearRightState));
		writeArray(os, PropertyName.MarkerLightsRearLeftStateArray, ItemType.IntProperty, convert(vehicles, Vehicle::getMarkerLightsRearLeftState));
		List<String> ftStr = convert(vehicles, Vehicle::getFreightType);
		length = 0;
		for(String s : ftStr) {
			length += s==null?4:4+s.length()+1;
		}
		writeArray(os, PropertyName.FreightTypeArray, ItemType.StrProperty, convert(vehicles, Vehicle::getFreightType), length);
		writeArray(os, PropertyName.FreightAmountArray, ItemType.IntProperty, convert(vehicles, Vehicle::getFreightAmount));
		writeArray(os, PropertyName.RegulatorValueArray, ItemType.FloatProperty, convert(vehicles, Vehicle::getRegulatorValue));
		writeArray(os, PropertyName.BrakeValueArray, ItemType.FloatProperty, convert(vehicles, Vehicle::getBrakeValue));
		writeArray(os, PropertyName.GeneratorValveValueArray, ItemType.FloatProperty, convert(vehicles, Vehicle::getGeneratorValue));
		writeArray(os, PropertyName.CompressorValveValueArray, ItemType.FloatProperty, convert(vehicles, Vehicle::getCompressorValue));
		writeArray(os, PropertyName.ReverserValueArray, ItemType.FloatProperty, convert(vehicles, Vehicle::getReverserValue));
	}
	
	private static void writeRemovedVegetation(ByteArrayOutputStream os, Save save) throws Exception {
		List<Point3D> removedVegetation = getList(save, Save::getRemovedVegetation);
		List<Object> removed = new ArrayList<>();
		removed.addAll(removedVegetation);
		if(removedVegetation.size() == 0) {
			return;
		}
		writeArrayStruct(os, PropertyName.RemovedVegetationAssetsArray, ItemSubType.Vector, removed);
	}

	private static void writeArray(ByteArrayOutputStream os, PropertyName pName, ItemType iType, List<Object> data) throws Exception {
		writeArray(os, pName, iType, data, data.size()*4);
	}
	private static void writeArray(ByteArrayOutputStream os, PropertyName pName, ItemType iType, List<Object> data, long len) throws Exception {
		long length = 0L;
		length+=4;//int32(count)
		length += len;
		
		BinaryWriter.string(os, pName.name());
		BinaryWriter.string(os, PropertyType.ArrayProperty.name());
		BinaryWriter.int64(os, length);
		
		BinaryWriter.string(os, iType.name());
		BinaryWriter.int8(os, (byte)0);
		BinaryWriter.int32(os, data.size());
		
		for(Object o : data) {
			if(o instanceof StringWrapper) {
				BinaryWriter.stringWrapper(os, (StringWrapper)o);
			} else if(o instanceof String) {
				BinaryWriter.string(os, (String)o);
			} else if(o instanceof Float) {
				BinaryWriter.float32(os, (Float)o);
			} else if(o instanceof Integer) {
				BinaryWriter.int32(os, (Integer)o);
			} else if(o instanceof Boolean) {
				BinaryWriter.int8(os, (byte)(((boolean)o)?1:0));
			} else if(o == null) {
				BinaryWriter.int32(os, 0);
			} else {
				throw new Exception("Unsupported write type for "+pName.name()+":"+iType.name()+" at "+Integer.toHexString(os.size()).toUpperCase());
			}
		}		
	}
	
	private static void writeArrayText(ByteArrayOutputStream os, PropertyName pName, ItemType iType, List<Item> data, long length) throws Exception {
		BinaryWriter.string(os, pName.name());
		BinaryWriter.string(os, PropertyType.ArrayProperty.name());
		BinaryWriter.int64(os, length);
		//===================
		BinaryWriter.string(os,  iType.name());
		BinaryWriter.int8(os, (byte)0);
		BinaryWriter.int32(os, data.size());
		//==
		for(Item p : data) {
			if(p instanceof TextItem) {
				TextItem item = (TextItem)p;
				
				BinaryWriter.int32(os, item.getOpt());
				BinaryWriter.int8(os, item.getFf());
				BinaryWriter.int32(os, item.getI1());
				
				if(item.getOpt() == 1) {
					BinaryWriter.int32(os, item.getI2());
					BinaryWriter.int8(os, item.getTerm());
					BinaryWriter.string(os, item.getHash());
					BinaryWriter.string(os, item.getFormat());					
					BinaryWriter.int32(os, item.getRows().size());
					
					for(TextItem.TextRow row : item.getRows()) {
//						System.out.println(Integer.toHexString(os.size()));
						BinaryWriter.string(os, row.getRowId());
						BinaryWriter.int8(os, row.getB());
						BinaryWriter.int32(os, row.getOpt());
						BinaryWriter.int8(os, row.getFf());
						if(row.getValue() != null) {
							BinaryWriter.int32(os, row.getI());
						}
						BinaryWriter.string(os, row.getValue());
					}
				} else if(item.getOpt() == 2) {
					BinaryWriter.string(os, item.getValue());
				}
			}
		}
	}
	
	private static void writeArrayStruct(ByteArrayOutputStream os, PropertyName pName, ItemSubType iType, List<Object> data) throws Exception {
		long length = 0L;
		length+=ItemType.StructProperty.name().length();
		length+=1+4;//0x00 + int32(count)
		length+=pName.name().length();
		length+=ItemType.StructProperty.name().length();
		length+=8;//int64(length)
		length+=iType.name().length();
		length+=1+16;//0x00 + guid
		length+=data.size()*12L;
		
		BinaryWriter.string(os, pName.name());
		BinaryWriter.string(os, PropertyType.ArrayProperty.name());
		BinaryWriter.int64(os, length);
		
		BinaryWriter.string(os, ItemType.StructProperty.name());
		BinaryWriter.int8(os, (byte)0);
		BinaryWriter.int32(os, data.size());
		
		BinaryWriter.string(os, pName.name());
		BinaryWriter.string(os, ItemType.StructProperty.name());
		BinaryWriter.int64(os, data.size()*12L);
		BinaryWriter.string(os, iType.name());
		BinaryWriter.int8(os, (byte)0);
		BinaryWriter.guid(os, Guid.empty());
		
		for(Object p : data) {
			if(p instanceof Point3D) {
				BinaryWriter.float32(os, (float)((Point3D)p).getX());
				BinaryWriter.float32(os, (float)((Point3D)p).getY());
				BinaryWriter.float32(os, (float)((Point3D)p).getZ());
			} else {
				throw new Exception("Unsupported write type for "+pName.name()+":"+iType.name());
			}
		}	
	}
	
	private static <T, U> U getList(T t, Function<T, U> p) {
		return p.apply(t);
	}	
	private static <T, U> List<U> convert(List<T> source, Function<T, U> func) {
	    return source.stream().map(func).collect(Collectors.toList());
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
}
