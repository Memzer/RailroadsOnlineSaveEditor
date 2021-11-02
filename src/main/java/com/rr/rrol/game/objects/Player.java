package com.rr.rrol.game.objects;

import java.util.List;

import com.rr.rrol.se.model.Save;
import com.rr.rrol.se.model.property.ArrayProperty;
import com.rr.rrol.se.model.property.PropertyName;
import com.rr.rrol.se.model.property.item.FloatItem;
import com.rr.rrol.se.model.property.item.IntItem;
import com.rr.rrol.se.model.property.item.Point3D;
import com.rr.rrol.se.model.property.item.StructItem;

public class Player extends GameObject {

	private Save save;
	private int index;
	private String name;
	private Point3D location;
	private float r,money;
	private int xp;
	
	private Player() {}

	private Player(Save save, int index, String name, Point3D location, float r, float money, int xp) {
		super();
		this.save = save;
		this.index = index;
		this.name = name;
		this.location = location;
		this.r = r;
		this.money = money;
		this.xp = xp;
	}
	
	public static List<String> getPlayerNames(Save save) throws Exception {
		return getArrayProperty(save, PropertyName.PlayerNameArray).getValue();
	}
	
	public static Player getInstance(Save save, String name) throws Exception {
		int i = getArrayProperty(save, PropertyName.PlayerNameArray).getValue().indexOf(name);
		
		ArrayProperty<Point3D> locations = getArrayProperty(save, PropertyName.PlayerLocationArray);
		ArrayProperty<Float> money = getArrayProperty(save, PropertyName.PlayerMoneyArray);
		ArrayProperty<Float> rotation = getArrayProperty(save, PropertyName.PlayerRotationArray);
		ArrayProperty<Integer> xps = getArrayProperty(save, PropertyName.PlayerXPArray);
		
		Point3D location = locations.getValue().get(i);
		Float m = money.getValue().get(i);
		Float r = rotation.getValue().get(i);
		Integer xp = xps.getValue().get(i);
		return new Player(save, i, name, location, r, m, xp);
	}

	public Point3D getLocation() {
		return location;
	}

	public void setLocation(Point3D location) throws Exception {
		StructItem item = (StructItem)getArrayProperty(save, PropertyName.PlayerLocationArray).getItems().get(index);
		item.getValue().setX(location.getX());
		item.getValue().setY(location.getY());
		item.getValue().setZ(location.getZ());
		this.location = location;
	}

	public float getR() {
		return r;
	}

	public void setR(float r) throws Exception {
		FloatItem item = (FloatItem)getArrayProperty(save, PropertyName.PlayerRotationArray).getItems().get(index);
		item.setValue(r);
		this.r = r;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) throws Exception {
		FloatItem item = (FloatItem)getArrayProperty(save, PropertyName.PlayerMoneyArray).getItems().get(index);
		item.setValue(money);
		this.money = money;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) throws Exception {
		IntItem item = (IntItem)getArrayProperty(save, PropertyName.PlayerXPArray).getItems().get(index);
		item.setValue(xp);
		this.xp = xp;
	}
	
}
