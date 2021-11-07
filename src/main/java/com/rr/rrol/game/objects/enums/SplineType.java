package com.rr.rrol.game.objects.enums;

public enum SplineType implements IEnum {

	RailNG,
    Grade,
    GradeConstant,
    Trestle,
    TrestleDeck,
    StonewallVariable,
    StonewallConstant,
    SplineTrestleSteel;

	@Override
    public Integer getIndex() {
		for(int i=0; i<values().length; i++) {
			if(values()[i].equals(this)) {
				return i;
			}
		}
		return null;
	}
    
}
