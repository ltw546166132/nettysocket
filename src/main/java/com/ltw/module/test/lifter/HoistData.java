package com.ltw.module.test.lifter;

import java.io.Serializable;
import java.util.Date;

public class HoistData implements Serializable {
	
	private static final long serialVersionUID = -4520090652283310976L;
	
	protected Byte cage_id;//吊笼编号 1byte	
	protected String hoist_box_id;//黑匣子(主机)编号 4byte
	//注：，，驾驶员认证结果0为未认证非零为驾驶员工号。
	//【2018-12-07 15:19:27:799】A5 5A 10 00 35 69 11 12 0C 07 0F 13 18 00 00 00 00 02 A7 21 00 05 DC 00 05 DC 00 00 00 00 00 A9 1F CC 33 C3 3C （实时数据）37
	protected Date hoist_time;//时间6byte
	protected Short real_time_lifting_weight;//实时起重量2byte 单位kg->t
	protected Byte weight_percentage;//重量百分比1byte
	protected Byte real_time_number_of_people;//实时人数1byte
	protected Short real_time_height;//实时高度2byte 单位为0.1米->1米
	protected Byte height_percentage;//高度百分比1byte
	protected Byte real_time_speed;//实时速度1byte（高6bit速度，低2bit方向: 0停止，1上下2下） 0.1米/秒->1米/秒
	protected Short real_time_gradient1;//实时倾斜度1(2byte) 倾斜数值减去1500取绝对值再除以100，倾斜度单位为0.01度
	protected Byte tilt_percentage1;// 倾斜百分比1(1byte) 
	protected Short real_time_gradient2;//实时倾斜度2(2byte) 倾斜数值减去1500取绝对值再除以100，倾斜度单位为0.01度
	protected Byte tilt_percentage2;// 倾斜百分比2(1byte)
	protected Byte driver_identification_state;//驾驶员身份认证结果1byte 
	protected Byte door_lock_state;// 门锁状态1byte（0bit前门1bit后门，数值1开启，0关闭。2bit门锁异常指示，0无异常1有异常）
	protected Short hoist_system_state;// 本字段只存在于实时值 系统状态2byte（0-1bit重量，2-3bit高度限位，4-5bit超速，6-7bit人数，8-9bit倾斜，数值0代表正常，数值1代表预警，数值2代表报警。10bit前门锁状态 11bit后门锁状态：数字0正常,数值1异常）12-13bit风速，0表示正常，1表示预警，2表示报警），
	protected Byte hoist_level;// 本字段只存在于报警值 级别1byte
	protected Byte hoist_alarm_reason;// 本字段只存在于报警值 报警原因1byte 1重量  2高度（冲顶） 3速度  4人数  5倾斜 6风速 
	protected Byte real_time_or_alarm;//是报警值还是实时值 0代表实时值，1代表报警值
	/** 表名称	*/
	protected String tableName;
	
	protected String system_weight;
	
	protected String system_height;
	
	protected String system_speeding;
	
	protected String system_people;
	
	protected String system_tilt;
	
	protected Short really_wind_speed; //0.1米/秒->1米/秒
	
	protected String system_wind;

	public Byte getCage_id() {
		return cage_id;
	}

	public void setCage_id(Byte cage_id) {
		this.cage_id = cage_id;
	}

	public String getHoist_box_id() {
		return hoist_box_id;
	}

	public void setHoist_box_id(String hoist_box_id) {
		this.hoist_box_id = hoist_box_id;
	}

	public Date getHoist_time() {
		return hoist_time;
	}

	public void setHoist_time(Date hoist_time) {
		this.hoist_time = hoist_time;
	}

	public Short getReal_time_lifting_weight() {
		return real_time_lifting_weight;
	}

	public void setReal_time_lifting_weight(Short real_time_lifting_weight) {
		this.real_time_lifting_weight = real_time_lifting_weight;
	}

	public Byte getWeight_percentage() {
		return weight_percentage;
	}

	public void setWeight_percentage(Byte weight_percentage) {
		this.weight_percentage = weight_percentage;
	}

	public Byte getReal_time_number_of_people() {
		return real_time_number_of_people;
	}

	public void setReal_time_number_of_people(Byte real_time_number_of_people) {
		this.real_time_number_of_people = real_time_number_of_people;
	}

	public Short getReal_time_height() {
		return real_time_height;
	}

	public void setReal_time_height(Short real_time_height) {
		this.real_time_height = real_time_height;
	}

	public Byte getHeight_percentage() {
		return height_percentage;
	}

	public void setHeight_percentage(Byte height_percentage) {
		this.height_percentage = height_percentage;
	}

	public Byte getReal_time_speed() {
		return real_time_speed;
	}

	public void setReal_time_speed(Byte real_time_speed) {
		this.real_time_speed = real_time_speed;
	}

	public Short getReal_time_gradient1() {
		return real_time_gradient1;
	}

	public void setReal_time_gradient1(Short real_time_gradient1) {
		this.real_time_gradient1 = real_time_gradient1;
	}

	public Byte getTilt_percentage1() {
		return tilt_percentage1;
	}

	public void setTilt_percentage1(Byte tilt_percentage1) {
		this.tilt_percentage1 = tilt_percentage1;
	}

	public Short getReal_time_gradient2() {
		return real_time_gradient2;
	}

	public void setReal_time_gradient2(Short real_time_gradient2) {
		this.real_time_gradient2 = real_time_gradient2;
	}

	public Byte getTilt_percentage2() {
		return tilt_percentage2;
	}

	public void setTilt_percentage2(Byte tilt_percentage2) {
		this.tilt_percentage2 = tilt_percentage2;
	}

	public Byte getDriver_identification_state() {
		return driver_identification_state;
	}

	public void setDriver_identification_state(Byte driver_identification_state) {
		this.driver_identification_state = driver_identification_state;
	}

	public Byte getDoor_lock_state() {
		return door_lock_state;
	}

	public void setDoor_lock_state(Byte door_lock_state) {
		this.door_lock_state = door_lock_state;
	}

	public Short getHoist_system_state() {
		return hoist_system_state;
	}

	public void setHoist_system_state(Short hoist_system_state) {
		this.hoist_system_state = hoist_system_state;
	}

	public Byte getHoist_level() {
		return hoist_level;
	}

	public void setHoist_level(Byte hoist_level) {
		this.hoist_level = hoist_level;
	}

	public Byte getHoist_alarm_reason() {
		return hoist_alarm_reason;
	}

	public void setHoist_alarm_reason(Byte hoist_alarm_reason) {
		this.hoist_alarm_reason = hoist_alarm_reason;
	}

	public Byte getReal_time_or_alarm() {
		return real_time_or_alarm;
	}

	public void setReal_time_or_alarm(Byte real_time_or_alarm) {
		this.real_time_or_alarm = real_time_or_alarm;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getSystem_weight() {
		return system_weight;
	}

	public void setSystem_weight(String system_weight) {
		this.system_weight = system_weight;
	}

	public String getSystem_height() {
		return system_height;
	}

	public void setSystem_height(String system_height) {
		this.system_height = system_height;
	}

	public String getSystem_speeding() {
		return system_speeding;
	}

	public void setSystem_speeding(String system_speeding) {
		this.system_speeding = system_speeding;
	}

	public String getSystem_people() {
		return system_people;
	}

	public void setSystem_people(String system_people) {
		this.system_people = system_people;
	}

	public String getSystem_tilt() {
		return system_tilt;
	}

	public void setSystem_tilt(String system_tilt) {
		this.system_tilt = system_tilt;
	}

	public Short getReally_wind_speed() {
		return really_wind_speed;
	}

	public void setReally_wind_speed(Short really_wind_speed) {
		this.really_wind_speed = really_wind_speed;
	}

	public String getSystem_wind() {
		return system_wind;
	}

	public void setSystem_wind(String system_wind) {
		this.system_wind = system_wind;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}