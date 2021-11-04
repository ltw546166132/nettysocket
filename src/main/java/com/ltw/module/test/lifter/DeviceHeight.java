package com.ltw.module.test.lifter;

import java.io.Serializable;

public class DeviceHeight implements Serializable{

	private static final long serialVersionUID = 5801070755948633062L;

	private String deviceId;
	
	private Short increase_height;
	
	private Short height_section;
	
	private boolean flag;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Short getIncrease_height() {
		return increase_height;
	}

	public void setIncrease_height(Short increase_height) {
		this.increase_height = increase_height;
	}

	public Short getHeight_section() {
		return height_section;
	}

	public void setHeight_section(Short height_section) {
		this.height_section = height_section;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
