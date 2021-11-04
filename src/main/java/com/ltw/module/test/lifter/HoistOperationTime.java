package com.ltw.module.test.lifter;

import java.io.Serializable;

public class HoistOperationTime implements Serializable{

	private static final long serialVersionUID = 382011132961446116L;

	protected String hoist_box_id;//升降机编号(出厂编号) 3byte
	
	protected String start_time;//最近一次上线时间
	
	protected String end_time;//最近一次下线时间
	
	/**
	 * 表名称
	 */
	protected String tableName;

	public String getHoist_box_id() {
		return hoist_box_id;
	}

	public void setHoist_box_id(String hoist_box_id) {
		this.hoist_box_id = hoist_box_id;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
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
