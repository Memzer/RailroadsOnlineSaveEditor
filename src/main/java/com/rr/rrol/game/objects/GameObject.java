package com.rr.rrol.game.objects;

import java.util.Optional;

import com.rr.rrol.se.model.Save;
import com.rr.rrol.se.model.property.ArrayProperty;
import com.rr.rrol.se.model.property.Property;
import com.rr.rrol.se.model.property.PropertyName;
import com.rr.rrol.se.model.property.StrProperty;

public abstract class GameObject {

	@SuppressWarnings("rawtypes")
	public static ArrayProperty getArrayProperty(Save save, PropertyName propertyName) throws Exception {
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
	public static StrProperty getStrProperty(Save save, PropertyName propertyName) throws Exception {
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
