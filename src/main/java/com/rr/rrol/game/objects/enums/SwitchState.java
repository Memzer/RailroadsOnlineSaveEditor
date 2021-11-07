package com.rr.rrol.game.objects.enums;

public enum SwitchState implements IEnum {

	Main,
    Side,
    Switching; //TODO: check if exists actually

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
