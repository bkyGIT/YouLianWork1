package com.yl.common.enums;
/**
 * 人员角色等级
 * @author swz
 *
 */
public enum UserLevel {
	admin("1","部门管理员"),
	worker("2","部门工作人员");
	
	private String code;  
    private String type;
    
    private UserLevel(String code,String type){  
        this.code=code;  
        this.type=type;  
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}  
    
    
}
