package com.yl.transaction.weixin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yl.transaction.role.dao.RoleDao;
import com.yl.transaction.role.service.RoleService;
import com.yl.transaction.weixin.dao.WeixinDao;
import com.yl.transaction.weixin.service.WeixinService;

@Service("weixinService")
public class WeixinServiceImpl implements WeixinService{

	@Resource  
	private WeixinDao weixinDao;

	@Override
	public List<Map<String, String>> getCirType(Map<String, String> param) {
		return weixinDao.getCirType(param);
	}

	@Override
	public void setCirType(Map<String, String> param) {
		weixinDao.setCirType(param);
	}

	@Override
	public void updateCirType(Map<String, String> param) {
		weixinDao.updateCirType(param);
	}

}
