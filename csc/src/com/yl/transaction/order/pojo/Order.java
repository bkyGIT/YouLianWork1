package com.yl.transaction.order.pojo;

import java.util.Date;

public class Order {
	
	private String maxaccept;
	
	private String order_type; //工单类型
	
	private String fix_type; //维护类型
	
	private String opr_id; //预约客服ID
	
	private String cust_id; //预约客户ID
	
	private String order_status; //工单状态
	
	private String fixuser_name; //维护人员姓名
	
	private String dept_code; //维护部门ID
	
	private String order_mark; //预约备注信息
	
	private String order_dgree; //回访满意度
	
	private Date creat_time; //工单创建时间
	
	private Date update_time; //工单修改时间

	public String getMaxaccept() {
		return maxaccept;
	}

	public void setMaxaccept(String maxaccept) {
		this.maxaccept = maxaccept;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getFix_type() {
		return fix_type;
	}

	public void setFix_type(String fix_type) {
		this.fix_type = fix_type;
	}

	public String getOpr_id() {
		return opr_id;
	}

	public void setOpr_id(String opr_id) {
		this.opr_id = opr_id;
	}

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getFixuser_name() {
		return fixuser_name;
	}

	public void setFixuser_name(String fixuser_name) {
		this.fixuser_name = fixuser_name;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getOrder_mark() {
		return order_mark;
	}

	public void setOrder_mark(String order_mark) {
		this.order_mark = order_mark;
	}

	public String getOrder_dgree() {
		return order_dgree;
	}

	public void setOrder_dgree(String order_dgree) {
		this.order_dgree = order_dgree;
	}

	public Date getCreat_time() {
		return creat_time;
	}

	public void setCreat_time(Date creat_time) {
		this.creat_time = creat_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

}
