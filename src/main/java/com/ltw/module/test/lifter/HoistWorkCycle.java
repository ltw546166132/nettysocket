package com.ltw.module.test.lifter;

import java.io.Serializable;
import java.util.Date;

public class HoistWorkCycle implements Serializable {

	private static final long serialVersionUID = -2998273587568437259L;
	
	private Long id;
	protected Byte cage_id;//吊笼编号 1byte
	protected String hoist_box_id;//黑匣子(主机)编号 4byte
	protected Date work_cycle_current_time;//当前时间6byte
	protected Date work_cycle_start_time;//工作循环开始时间6byte
	protected Date work_cycle_end_time;//工作循环终止时间6byte
	protected Short cycle_maximum_weight;
	protected String pilot_name;//驾驶员姓名32byte 字符型（全0代表未认证）
	protected String pilot_identity_card_number;//驾驶员身份证号18byte 字符型（全0代表未认证）
	protected Short the_load;//本次载重2byte 单位KG
	protected Byte the_load_percentage;//本次载重百分比1byte
	protected Short the_rise_start_height;//本次起升起点高度2byte 单位0.1米
	protected Short the_rise_end_height;//本次起升终点高度2byte 单位0.1米
	protected Short the_rise_stroke_height;//本次起升的行程高度2byte 单位0.1米
	protected Byte the_rise_direction;//本次起升方向1byte 1下,2上
	protected Byte the_rise_average_speed;//本次起升平均速度1byte 单位0.1米/秒
	protected Short the_rise_maximum_x_direction_gradient;//本次起升最大X向倾斜度2byte 单位0.01度
	protected Short the_rise_maximum_y_direction_gradient;//本次起升最大Y向倾斜度2byte 单位0.01度
	protected Byte the_load_number;//本次装载人数1byte
	protected Short the_rise_alert_state;//本次起升警报状态2byte
	/**
	 * 报警状态：bit0-1：00重量正常 01重量预警 10重量报警；bit2-3： 00身份认证未正常  01身份认证通过；
	 * bit4-5:00速度正常 01速度预警 10速度报警； bit6-7：00高度正常 01高度预警 10高度告警 
	 * bit8-9：00人数正常 01人数超载  bit10-11:  00 倾斜正常 01倾斜预警 10倾斜报警。
	 * bit12-13：00风速正常 01风速预警 10风速报警*/
	
	/** 表名称	*/
	protected String tableName;
	
	protected Short maximum_wind_speed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Date getWork_cycle_current_time() {
		return work_cycle_current_time;
	}

	public void setWork_cycle_current_time(Date work_cycle_current_time) {
		this.work_cycle_current_time = work_cycle_current_time;
	}

	public Date getWork_cycle_start_time() {
		return work_cycle_start_time;
	}

	public void setWork_cycle_start_time(Date work_cycle_start_time) {
		this.work_cycle_start_time = work_cycle_start_time;
	}

	public Date getWork_cycle_end_time() {
		return work_cycle_end_time;
	}

	public void setWork_cycle_end_time(Date work_cycle_end_time) {
		this.work_cycle_end_time = work_cycle_end_time;
	}

	public Short getCycle_maximum_weight() {
		return cycle_maximum_weight;
	}

	public void setCycle_maximum_weight(Short cycle_maximum_weight) {
		this.cycle_maximum_weight = cycle_maximum_weight;
	}

	public String getPilot_name() {
		return pilot_name;
	}

	public void setPilot_name(String pilot_name) {
		this.pilot_name = pilot_name;
	}

	public String getPilot_identity_card_number() {
		return pilot_identity_card_number;
	}

	public void setPilot_identity_card_number(String pilot_identity_card_number) {
		this.pilot_identity_card_number = pilot_identity_card_number;
	}

	public Short getThe_load() {
		return the_load;
	}

	public void setThe_load(Short the_load) {
		this.the_load = the_load;
	}

	public Byte getThe_load_percentage() {
		return the_load_percentage;
	}

	public void setThe_load_percentage(Byte the_load_percentage) {
		this.the_load_percentage = the_load_percentage;
	}

	public Short getThe_rise_start_height() {
		return the_rise_start_height;
	}

	public void setThe_rise_start_height(Short the_rise_start_height) {
		this.the_rise_start_height = the_rise_start_height;
	}

	public Short getThe_rise_end_height() {
		return the_rise_end_height;
	}

	public void setThe_rise_end_height(Short the_rise_end_height) {
		this.the_rise_end_height = the_rise_end_height;
	}

	public Short getThe_rise_stroke_height() {
		return the_rise_stroke_height;
	}

	public void setThe_rise_stroke_height(Short the_rise_stroke_height) {
		this.the_rise_stroke_height = the_rise_stroke_height;
	}

	public Byte getThe_rise_direction() {
		return the_rise_direction;
	}

	public void setThe_rise_direction(Byte the_rise_direction) {
		this.the_rise_direction = the_rise_direction;
	}

	public Byte getThe_rise_average_speed() {
		return the_rise_average_speed;
	}

	public void setThe_rise_average_speed(Byte the_rise_average_speed) {
		this.the_rise_average_speed = the_rise_average_speed;
	}

	public Short getThe_rise_maximum_x_direction_gradient() {
		return the_rise_maximum_x_direction_gradient;
	}

	public void setThe_rise_maximum_x_direction_gradient(Short the_rise_maximum_x_direction_gradient) {
		this.the_rise_maximum_x_direction_gradient = the_rise_maximum_x_direction_gradient;
	}

	public Short getThe_rise_maximum_y_direction_gradient() {
		return the_rise_maximum_y_direction_gradient;
	}

	public void setThe_rise_maximum_y_direction_gradient(Short the_rise_maximum_y_direction_gradient) {
		this.the_rise_maximum_y_direction_gradient = the_rise_maximum_y_direction_gradient;
	}

	public Byte getThe_load_number() {
		return the_load_number;
	}

	public void setThe_load_number(Byte the_load_number) {
		this.the_load_number = the_load_number;
	}

	public Short getThe_rise_alert_state() {
		return the_rise_alert_state;
	}

	public void setThe_rise_alert_state(Short the_rise_alert_state) {
		this.the_rise_alert_state = the_rise_alert_state;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Short getMaximum_wind_speed() {
		return maximum_wind_speed;
	}

	public void setMaximum_wind_speed(Short maximum_wind_speed) {
		this.maximum_wind_speed = maximum_wind_speed;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
