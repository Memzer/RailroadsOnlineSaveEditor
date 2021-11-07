package com.rr.rrol.game.objects.enums;

public enum SwitchType implements IEnum {

	SwitchLeft,
    SwitchRight,
    Unknown1,
    Unknown2,
    SwitchLeftMirror,
    SwitchRightMirror,
    SwitchCross90;

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
