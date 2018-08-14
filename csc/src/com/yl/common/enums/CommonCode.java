package com.yl.common.enums;
/**
 * 公共知识库枚举类
 * @author swz
 *
 */
public enum CommonCode {  
	  
	CONVER_DEGREE("CONVER_DEGREE","通话满意度"),  
	CONVER_STATUS("CONVER_STATUS","通话状态类型"),  
	FIX_TYPE("FIX_TYPE","维护类型"),  
	ORDER_DGREE("ORDER_DGREE","维护满意度"),  
    ORDER_STATUS("ORDER_STATUS","工单状态"),  
    ORDER_TYPE("ORDER_TYPE","工单类型"),
    DEPT_TYPE("DEPT_TYPE","部门类型"),
	USER_LEVEL("USER_LEVEL","人员角色等级"),
	ORDER_CHANNEL("ORDER_CHANNEL","受理渠道"),
	TALK_FLAG("TALK_FLAG","接通状态"),
	IN_FLAG("IN_FLAG","呼叫方向");
      
    private String code;  
    private String type;  
      
    private CommonCode(String code,String type){  
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