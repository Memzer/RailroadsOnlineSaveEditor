package com.rr.rrol.se.model;

public class Guid {

	private Integer i1;
	private Short s1;
	private Short s2;
	private Byte b1;
	private Byte b2;
	private Byte b3;
	private Byte b4;
	private Byte b5;
	private Byte b6;
	private Byte b7;
	private Byte b8;
	
	public Guid(Integer i1, Short s1, Short s2, Byte b1, Byte b2, Byte b3, Byte b4, Byte b5, Byte b6, Byte b7, Byte b8) {
		super();
		this.i1 = i1;
		this.s1 = s1;
		this.s2 = s2;
		this.b1 = b1;
		this.b2 = b2;
		this.b3 = b3;
		this.b4 = b4;
		this.b5 = b5;
		this.b6 = b6;
		this.b7 = b7;
		this.b8 = b8;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(padToLength(8, Integer.toHexString(i1)));
		sb.append("-");
		sb.append(padToLength(4, Integer.toHexString(s1)));
		sb.append("-");
		sb.append(padToLength(4, Integer.toHexString(s2)));
		sb.append("-");
		sb.append(padToLength(2, Integer.toHexString(b1)));
		sb.append(padToLength(2, Integer.toHexString(b2)));
		sb.append("-");
		sb.append(padToLength(2, Integer.toHexString(b3)));
		sb.append(padToLength(2, Integer.toHexString(b4)));
		sb.append(padToLength(2, Integer.toHexString(b5)));
		sb.append(padToLength(2, Integer.toHexString(b6)));
		sb.append(padToLength(2, Integer.toHexString(b7)));
		sb.append(padToLength(2, Integer.toHexString(b8)));
		return sb.toString();
	}
	
	private String padToLength(int length, String hex) {
		while(hex.length() < length) {
			hex = "0"+hex;
		}
		while(hex.length() > length) {
			hex = hex.substring(1);
		}
		return hex;
	}

	public Integer getI1() {
		return i1;
	}

	public void setI1(Integer i1) {
		this.i1 = i1;
	}

	public Short getS1() {
		return s1;
	}

	public void setS1(Short s1) {
		this.s1 = s1;
	}

	public Short getS2() {
		return s2;
	}

	public void setS2(Short s2) {
		this.s2 = s2;
	}

	public Byte getB1() {
		return b1;
	}

	public void setB1(Byte b1) {
		this.b1 = b1;
	}

	public Byte getB2() {
		return b2;
	}

	public void setB2(Byte b2) {
		this.b2 = b2;
	}

	public Byte getB3() {
		return b3;
	}

	public void setB3(Byte b3) {
		this.b3 = b3;
	}

	public Byte getB4() {
		return b4;
	}

	public void setB4(Byte b4) {
		this.b4 = b4;
	}

	public Byte getB5() {
		return b5;
	}

	public void setB5(Byte b5) {
		this.b5 = b5;
	}

	public Byte getB6() {
		return b6;
	}

	public void setB6(Byte b6) {
		this.b6 = b6;
	}

	public Byte getB7() {
		return b7;
	}

	public void setB7(Byte b7) {
		this.b7 = b7;
	}

	public Byte getB8() {
		return b8;
	}

	public void setB8(Byte b8) {
		this.b8 = b8;
	}
	
}
