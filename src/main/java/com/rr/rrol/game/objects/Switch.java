package com.rr.rrol.game.objects;

import com.rr.rrol.game.objects.enums.SwitchState;
import com.rr.rrol.game.objects.enums.SwitchType;
import com.rr.rrol.se.model.property.item.Point3D;

public class Switch {
	
	private SwitchType type;
	private SwitchState state;
	private Point3D location;
	private Point3D rotation;

    public Switch(SwitchType type, Point3D location, Point3D rotation, SwitchState state) {
        this.type = type;
        this.state = state;
        this.location = location;
        this.rotation = rotation;
    }

	public SwitchType getType() {
		return type;
	}

	public SwitchState getState() {
		return state;
	}

	public Point3D getLocation() {
		return location;
	}

	public Point3D getRotation() {
		return rotation;
	}
}
