package com.ltw.module.test.lifter;

/**
 * 注册信息
 * @author wx
 */
public class HoistInfoRegisterPO extends HoistInfo {
	private static final long serialVersionUID = 2800311114948050978L;
	
	protected Short hoist_manufacturer_and_device_type;//厂家代码(厂家及设备类型?)（1byte）
	
	public void setCrane_manufacturer_and_device_type(Short hoist_manufacturer_and_device_type) {
		this.hoist_manufacturer_and_device_type = hoist_manufacturer_and_device_type;
	}
}