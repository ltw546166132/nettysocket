package com.ltw.module.test.lifter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HoistInfo implements Serializable{
	private static final long serialVersionUID = -2642861219120725889L;

	protected Short cage_id;//吊笼编号 1byte
	protected String hoist_box_id;//黑匣子(主机)编号 4byte
	/**注册信息*/
	/**基本属性信息*/
	/** 限位设置信息*/
	protected BigDecimal maximum_elevating_capacity_warning;//最大起重量预警2byte 单位KG
	protected BigDecimal maximum_elevating_capacity_alarm;//最大起重量报警2byte 单位KG
	protected BigDecimal maximum_rise_height;//最大起升高度2byte 单位0.1米
	protected BigDecimal maximum_speed_warning;//最大速度预警2byte 单位0.1米/秒->1米/秒
	protected BigDecimal maximum_speed_alarm;//最大速度报警2byte 单位0.1米/秒->1米/秒
	protected Short maximum_carrying_number;//最大承载人数2byte(升降机一般能承载十几个人)
	protected BigDecimal tilt_warning;//倾斜预警值2byte 单位0.1度->1度
	protected BigDecimal tilt_alarm;//倾斜报警值2byte 单位0.1度->1度

	protected BigDecimal windspeed_warning;//最大风速预警0.1米/秒
	protected BigDecimal windspeed_alarm;//最大风速报警0.1米/秒
	private String info_status;//设置状态
	private String lock;//设置状态

	/**标定信息*/
	protected BigDecimal weight_noload_AD;//重量空载AD值2byte
	protected BigDecimal weight_noload_actual;//重量空载实际值2byte
	protected BigDecimal weight_load_AD;//重量负载AD值2byte
	protected BigDecimal weight_load_actual;//重量负载实际值2byte
	protected BigDecimal height_bottom_AD;//高度底端AD值2byte
	protected BigDecimal height_bottom_actual;//高度底端实际值2byte
	protected BigDecimal height_top_AD;//高度顶端AD值2byte
	protected BigDecimal height_top_actual;//高度顶端实际值2byte

	/**设备扩展信息(在平台为设备设置的设备信息，即非设备直发的信息)*/
	protected String forward;//转发位置
	private String sim_card_number;
	protected String hoist_name;//设备名称
	protected String record_number;//备案编号
	protected String property_unit;//产权单位
	protected Date lease_expiration_time;//租凭到期时间
	protected String record_status;//备案状态
	protected String construction_state;//施工状态
	protected Date remove_date;//拆卸日期
	protected String longitude;//经度
	protected String latitude;//纬度

	/**以下为不可在前端修改的设备扩展信息*/
	protected Date update_time;//数据更新时间
	protected String offline_start_time;//最近一次上线时间
	protected String offline_end_time;//最近一次下线时间
	protected Integer alarm_count;//报警次数
	protected Integer today_alarm_count;//今日报警次数
	protected String total_online_time;
	protected String total_offine_time;

	protected Byte upload_interval;
	/** 表名称	*/
	protected String tableName;
	public Short getCage_id() {
		return cage_id;
	}
	public void setCage_id(Short cage_id) {
		this.cage_id = cage_id;
	}
	public String getHoist_box_id() {
		return hoist_box_id;
	}
	public void setHoist_box_id(String hoist_box_id) {
		this.hoist_box_id = hoist_box_id;
	}
	public BigDecimal getMaximum_elevating_capacity_warning() {
		return maximum_elevating_capacity_warning;
	}
	public void setMaximum_elevating_capacity_warning(BigDecimal maximum_elevating_capacity_warning) {
		this.maximum_elevating_capacity_warning = maximum_elevating_capacity_warning;
	}
	public BigDecimal getMaximum_elevating_capacity_alarm() {
		return maximum_elevating_capacity_alarm;
	}
	public void setMaximum_elevating_capacity_alarm(BigDecimal maximum_elevating_capacity_alarm) {
		this.maximum_elevating_capacity_alarm = maximum_elevating_capacity_alarm;
	}
	public BigDecimal getMaximum_rise_height() {
		return maximum_rise_height;
	}
	public void setMaximum_rise_height(BigDecimal maximum_rise_height) {
		this.maximum_rise_height = maximum_rise_height;
	}
	public BigDecimal getMaximum_speed_warning() {
		return maximum_speed_warning;
	}
	public void setMaximum_speed_warning(BigDecimal maximum_speed_warning) {
		this.maximum_speed_warning = maximum_speed_warning;
	}
	public BigDecimal getMaximum_speed_alarm() {
		return maximum_speed_alarm;
	}
	public void setMaximum_speed_alarm(BigDecimal maximum_speed_alarm) {
		this.maximum_speed_alarm = maximum_speed_alarm;
	}
	public Short getMaximum_carrying_number() {
		return maximum_carrying_number;
	}
	public void setMaximum_carrying_number(Short maximum_carrying_number) {
		this.maximum_carrying_number = maximum_carrying_number;
	}
	public BigDecimal getTilt_warning() {
		return tilt_warning;
	}
	public void setTilt_warning(BigDecimal tilt_warning) {
		this.tilt_warning = tilt_warning;
	}
	public BigDecimal getTilt_alarm() {
		return tilt_alarm;
	}
	public void setTilt_alarm(BigDecimal tilt_alarm) {
		this.tilt_alarm = tilt_alarm;
	}
	public BigDecimal getWindspeed_warning() {
		return windspeed_warning;
	}
	public void setWindspeed_warning(BigDecimal windspeed_warning) {
		this.windspeed_warning = windspeed_warning;
	}
	public BigDecimal getWindspeed_alarm() {
		return windspeed_alarm;
	}
	public void setWindspeed_alarm(BigDecimal windspeed_alarm) {
		this.windspeed_alarm = windspeed_alarm;
	}
	public String getInfo_status() {
		return info_status;
	}
	public void setInfo_status(String info_status) {
		this.info_status = info_status;
	}
	public String getLock() {
		return lock;
	}
	public void setLock(String lock) {
		this.lock = lock;
	}
	public BigDecimal getWeight_noload_AD() {
		return weight_noload_AD;
	}
	public void setWeight_noload_AD(BigDecimal weight_noload_AD) {
		this.weight_noload_AD = weight_noload_AD;
	}
	public BigDecimal getWeight_noload_actual() {
		return weight_noload_actual;
	}
	public void setWeight_noload_actual(BigDecimal weight_noload_actual) {
		this.weight_noload_actual = weight_noload_actual;
	}
	public BigDecimal getWeight_load_AD() {
		return weight_load_AD;
	}
	public void setWeight_load_AD(BigDecimal weight_load_AD) {
		this.weight_load_AD = weight_load_AD;
	}
	public BigDecimal getWeight_load_actual() {
		return weight_load_actual;
	}
	public void setWeight_load_actual(BigDecimal weight_load_actual) {
		this.weight_load_actual = weight_load_actual;
	}
	public BigDecimal getHeight_bottom_AD() {
		return height_bottom_AD;
	}
	public void setHeight_bottom_AD(BigDecimal height_bottom_AD) {
		this.height_bottom_AD = height_bottom_AD;
	}
	public BigDecimal getHeight_bottom_actual() {
		return height_bottom_actual;
	}
	public void setHeight_bottom_actual(BigDecimal height_bottom_actual) {
		this.height_bottom_actual = height_bottom_actual;
	}
	public BigDecimal getHeight_top_AD() {
		return height_top_AD;
	}
	public void setHeight_top_AD(BigDecimal height_top_AD) {
		this.height_top_AD = height_top_AD;
	}
	public BigDecimal getHeight_top_actual() {
		return height_top_actual;
	}
	public void setHeight_top_actual(BigDecimal height_top_actual) {
		this.height_top_actual = height_top_actual;
	}
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	public String getSim_card_number() {
		return sim_card_number;
	}
	public void setSim_card_number(String sim_card_number) {
		this.sim_card_number = sim_card_number;
	}
	public String getHoist_name() {
		return hoist_name;
	}
	public void setHoist_name(String hoist_name) {
		this.hoist_name = hoist_name;
	}
	public String getRecord_number() {
		return record_number;
	}
	public void setRecord_number(String record_number) {
		this.record_number = record_number;
	}
	public String getProperty_unit() {
		return property_unit;
	}
	public void setProperty_unit(String property_unit) {
		this.property_unit = property_unit;
	}
	public Date getLease_expiration_time() {
		return lease_expiration_time;
	}
	public void setLease_expiration_time(Date lease_expiration_time) {
		this.lease_expiration_time = lease_expiration_time;
	}
	public String getRecord_status() {
		return record_status;
	}
	public void setRecord_status(String record_status) {
		this.record_status = record_status;
	}
	public String getConstruction_state() {
		return construction_state;
	}
	public void setConstruction_state(String construction_state) {
		this.construction_state = construction_state;
	}
	public Date getRemove_date() {
		return remove_date;
	}
	public void setRemove_date(Date remove_date) {
		this.remove_date = remove_date;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getOffline_start_time() {
		return offline_start_time;
	}
	public void setOffline_start_time(String offline_start_time) {
		this.offline_start_time = offline_start_time;
	}
	public String getOffline_end_time() {
		return offline_end_time;
	}
	public void setOffline_end_time(String offline_end_time) {
		this.offline_end_time = offline_end_time;
	}
	public Integer getAlarm_count() {
		return alarm_count;
	}
	public void setAlarm_count(Integer alarm_count) {
		this.alarm_count = alarm_count;
	}
	public Integer getToday_alarm_count() {
		return today_alarm_count;
	}
	public void setToday_alarm_count(Integer today_alarm_count) {
		this.today_alarm_count = today_alarm_count;
	}
	public String getTotal_online_time() {
		return total_online_time;
	}
	public void setTotal_online_time(String total_online_time) {
		this.total_online_time = total_online_time;
	}
	public String getTotal_offine_time() {
		return total_offine_time;
	}
	public void setTotal_offine_time(String total_offine_time) {
		this.total_offine_time = total_offine_time;
	}
	public Byte getUpload_interval() {
		return upload_interval;
	}
	public void setUpload_interval(Byte upload_interval) {
		this.upload_interval = upload_interval;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
