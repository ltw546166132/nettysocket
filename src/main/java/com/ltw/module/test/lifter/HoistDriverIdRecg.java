package com.ltw.module.test.lifter;

import java.io.Serializable;

public class HoistDriverIdRecg implements Serializable {

	private static final long serialVersionUID = -5859682296865857720L;
	
	private Long id;
    private Byte hoist_id;
    private String hoist_box_id;
    private String system_time;
    private Byte recognition_results;
    private String driver_name;
    private String driver_id;
    private Byte recg_rercentage;
    private String id_card_no;
    /** 表名称	*/
	protected String tableName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Byte getHoist_id() {
		return hoist_id;
	}
	public void setHoist_id(Byte hoist_id) {
		this.hoist_id = hoist_id;
	}
	public String getHoist_box_id() {
		return hoist_box_id;
	}
	public void setHoist_box_id(String hoist_box_id) {
		this.hoist_box_id = hoist_box_id;
	}
	public String getSystem_time() {
		return system_time;
	}
	public void setSystem_time(String system_time) {
		this.system_time = system_time;
	}
	public Byte getRecognition_results() {
		return recognition_results;
	}
	public void setRecognition_results(Byte recognition_results) {
		this.recognition_results = recognition_results;
	}
	public String getDriver_name() {
		return driver_name;
	}
	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}
	public String getDriver_id() {
		return driver_id;
	}
	public void setDriver_id(String driver_id) {
		this.driver_id = driver_id;
	}
	public Byte getRecg_rercentage() {
		return recg_rercentage;
	}
	public void setRecg_rercentage(Byte recg_rercentage) {
		this.recg_rercentage = recg_rercentage;
	}
	public String getId_card_no() {
		return id_card_no;
	}
	public void setId_card_no(String id_card_no) {
		this.id_card_no = id_card_no;
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