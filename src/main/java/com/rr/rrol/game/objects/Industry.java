package com.rr.rrol.game.objects;

import com.rr.rrol.se.model.property.item.Point3D;

public class Industry {
	private Integer type;
	private Point3D location;
	private Point3D rotation;
	private Integer storageEduct1;
	private Integer storageEduct2;
	private Integer storageEduct3;
	private Integer storageEduct4;
	private Integer storageProduct1;
	private Integer storageProduct2;
	private Integer storageProduct3;
	private Integer storageProduct4;
	
	public Industry(Integer type, Point3D Location, Point3D Rotation, Integer storageEduct1, Integer storageEduct2, Integer storageEduct3, Integer storageEduct4, Integer storageProduct1, Integer storageProduct2, Integer storageProduct3, Integer storageProduct4) {
		super();
		this.type = type;
		this.location = Location;
		this.rotation = Rotation;
		this.storageEduct1 = storageEduct1;
		this.storageEduct2 = storageEduct2;
		this.storageEduct3 = storageEduct3;
		this.storageEduct4 = storageEduct4;
		this.storageProduct1 = storageProduct1;
		this.storageProduct2 = storageProduct2;
		this.storageProduct3 = storageProduct3;
		this.storageProduct4 = storageProduct4;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
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

	public Integer getStorageEduct1() {
		return storageEduct1;
	}

	public void setStorageEduct1(Integer storageEduct1) {
		this.storageEduct1 = storageEduct1;
	}

	public Integer getStorageEduct2() {
		return storageEduct2;
	}

	public void setStorageEduct2(Integer storageEduct2) {
		this.storageEduct2 = storageEduct2;
	}

	public Integer getStorageEduct3() {
		return storageEduct3;
	}

	public void setStorageEduct3(Integer storageEduct3) {
		this.storageEduct3 = storageEduct3;
	}

	public Integer getStorageEduct4() {
		return storageEduct4;
	}

	public void setStorageEduct4(Integer storageEduct4) {
		this.storageEduct4 = storageEduct4;
	}

	public Integer getStorageProduct1() {
		return storageProduct1;
	}

	public void setStorageProduct1(Integer storageProduct1) {
		this.storageProduct1 = storageProduct1;
	}

	public Integer getStorageProduct2() {
		return storageProduct2;
	}

	public void setStorageProduct2(Integer storageProduct2) {
		this.storageProduct2 = storageProduct2;
	}

	public Integer getStorageProduct3() {
		return storageProduct3;
	}

	public void setStorageProduct3(Integer storageProduct3) {
		this.storageProduct3 = storageProduct3;
	}

	public Integer getStorageProduct4() {
		return storageProduct4;
	}

	public void setStorageProduct4(Integer storageProduct4) {
		this.storageProduct4 = storageProduct4;
	}
}
