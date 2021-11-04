package com.ltw.module.test.lifter;

import java.io.Serializable;

public class DevicePicture implements Serializable{

	private static final long serialVersionUID = -8156203622598766316L;

	private short ID;
	
	private short P;
	
	private byte[] picture;
	
	private byte amount;

	public short getID() {
		return ID;
	}

	public void setID(short iD) {
		ID = iD;
	}

	public short getP() {
		return P;
	}

	public void setP(short p) {
		P = p;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public byte getAmount() {
		return amount;
	}

	public void setAmount(byte amount) {
		this.amount = amount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
