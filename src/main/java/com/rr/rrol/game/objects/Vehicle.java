package com.rr.rrol.game.objects;

import com.rr.rrol.se.model.property.item.Point3D;

public class Vehicle {

	private String type;
	private Point3D location;
	private Point3D rotation;
	private String number;
	private String name;
	private Integer smokestackType;
	private Integer headlightType;
	private Float boilerFuelAmount;
	private Float boilerFireTemp;
	private Float boilerWaterTemp;
	private Float boilerWaterLevel;
	private Float boilerPressure;
	private Boolean headlightFrontState;
	private Boolean headlightRearState;
	private Boolean couplerFrontState;
	private Boolean couplerRearState;
	private Float tenderFuelAmount;
	private Float tenderWaterAmount;
	private Float compressorAirPressure;
	private Integer markerLightsFrontRightState;
	private Integer markerLightsFrontLeftState;
	private Integer markerLightsRearRightState;
	private Integer markerLightsRearLeftState;
	private String freightType;
	private Integer freightAmount;
	private Float regulatorValue;
	private Float brakeValue;
	private Float generatorValue;
	private Float compressorValue;
	private Float reverserValue;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSmokestackType() {
		return smokestackType;
	}
	public void setSmokestackType(Integer smokestackType) {
		this.smokestackType = smokestackType;
	}
	public Integer getHeadlightType() {
		return headlightType;
	}
	public void setHeadlightType(Integer headlightType) {
		this.headlightType = headlightType;
	}
	public Float getBoilerFuelAmount() {
		return boilerFuelAmount;
	}
	public void setBoilerFuelAmount(Float boilerFuelAmount) {
		this.boilerFuelAmount = boilerFuelAmount;
	}
	public Float getBoilerFireTemp() {
		return boilerFireTemp;
	}
	public void setBoilerFireTemp(Float boilerFireTemp) {
		this.boilerFireTemp = boilerFireTemp;
	}
	public Float getBoilerWaterTemp() {
		return boilerWaterTemp;
	}
	public void setBoilerWaterTemp(Float boilerWaterTemp) {
		this.boilerWaterTemp = boilerWaterTemp;
	}
	public Float getBoilerWaterLevel() {
		return boilerWaterLevel;
	}
	public void setBoilerWaterLevel(Float boilerWaterLevel) {
		this.boilerWaterLevel = boilerWaterLevel;
	}
	public Float getBoilerPressure() {
		return boilerPressure;
	}
	public void setBoilerPressure(Float boilerPressure) {
		this.boilerPressure = boilerPressure;
	}
	public Boolean getHeadlightFrontState() {
		return headlightFrontState;
	}
	public void setHeadlightFrontState(Boolean headlightFrontState) {
		this.headlightFrontState = headlightFrontState;
	}
	public Boolean getHeadlightRearState() {
		return headlightRearState;
	}
	public void setHeadlightRearState(Boolean headlightRearState) {
		this.headlightRearState = headlightRearState;
	}
	public Boolean getCouplerFrontState() {
		return couplerFrontState;
	}
	public void setCouplerFrontState(Boolean couplerFrontState) {
		this.couplerFrontState = couplerFrontState;
	}
	public Boolean getCouplerRearState() {
		return couplerRearState;
	}
	public void setCouplerRearState(Boolean couplerRearState) {
		this.couplerRearState = couplerRearState;
	}
	public Float getTenderFuelAmount() {
		return tenderFuelAmount;
	}
	public void setTenderFuelAmount(Float tenderFuelAmount) {
		this.tenderFuelAmount = tenderFuelAmount;
	}
	public Float getTenderWaterAmount() {
		return tenderWaterAmount;
	}
	public void setTenderWaterAmount(Float tenderWaterAmount) {
		this.tenderWaterAmount = tenderWaterAmount;
	}
	public Float getCompressorAirPressure() {
		return compressorAirPressure;
	}
	public void setCompressorAirPressure(Float compressorAirPressure) {
		this.compressorAirPressure = compressorAirPressure;
	}
	public Integer getMarkerLightsFrontRightState() {
		return markerLightsFrontRightState;
	}
	public void setMarkerLightsFrontRightState(Integer markerLightsFrontRightState) {
		this.markerLightsFrontRightState = markerLightsFrontRightState;
	}
	public Integer getMarkerLightsFrontLeftState() {
		return markerLightsFrontLeftState;
	}
	public void setMarkerLightsFrontLeftState(Integer markerLightsFrontLeftState) {
		this.markerLightsFrontLeftState = markerLightsFrontLeftState;
	}
	public Integer getMarkerLightsRearRightState() {
		return markerLightsRearRightState;
	}
	public void setMarkerLightsRearRightState(Integer markerLightsRearRightState) {
		this.markerLightsRearRightState = markerLightsRearRightState;
	}
	public Integer getMarkerLightsRearLeftState() {
		return markerLightsRearLeftState;
	}
	public void setMarkerLightsRearLeftState(Integer markerLightsRearLeftState) {
		this.markerLightsRearLeftState = markerLightsRearLeftState;
	}
	public String getFreightType() {
		return freightType;
	}
	public void setFreightType(String freightType) {
		this.freightType = freightType;
	}
	public Integer getFreightAmount() {
		return freightAmount;
	}
	public void setFreightAmount(Integer freightAmount) {
		this.freightAmount = freightAmount;
	}
	public Float getRegulatorValue() {
		return regulatorValue;
	}
	public void setRegulatorValue(Float regulatorValue) {
		this.regulatorValue = regulatorValue;
	}
	public Float getBrakeValue() {
		return brakeValue;
	}
	public void setBrakeValue(Float brakeValue) {
		this.brakeValue = brakeValue;
	}
	public Float getGeneratorValue() {
		return generatorValue;
	}
	public void setGeneratorValue(Float generatorValue) {
		this.generatorValue = generatorValue;
	}
	public Float getCompressorValue() {
		return compressorValue;
	}
	public void setCompressorValue(Float compressorValue) {
		this.compressorValue = compressorValue;
	}
	public Float getReverserValue() {
		return reverserValue;
	}
	public void setReverserValue(Float reverserValue) {
		this.reverserValue = reverserValue;
	}
	
}
