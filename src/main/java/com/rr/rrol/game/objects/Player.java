package com.rr.rrol.game.objects;

public class Player {

	private String name;
	private float x,y,z,r,money;
	private int xp;
	
	public Player(String name, float money, int xp, float x, float y, float z, float r) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
		this.r = r;
		this.money = money;
		this.xp = xp;
	}
	
}
