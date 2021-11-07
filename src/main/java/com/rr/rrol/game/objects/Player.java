package com.rr.rrol.game.objects;

import com.rr.rrol.se.model.property.item.Point3D;

public class Player extends GameObject {

	private String name;
	private int nameLength;
	private Point3D location;
	private float r,money;
	private int xp;
	
	public Player(String name, int nameLength, Point3D location, float r, float money, int xp) {
		super();
		this.name = name;
		this.nameLength = nameLength;
		this.location = location;
		this.r = r;
		this.money = money;
		this.xp = xp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point3D getLocation() {
		return location;
	}

	public void setLocation(Point3D location) {
		this.location = location;
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getNameLength() {
		return nameLength;
	}
	
}
