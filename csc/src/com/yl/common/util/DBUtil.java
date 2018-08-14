package com.yl.common.util;

import org.springframework.stereotype.Service;

import com.yl.common.dao.PublicDao;

@Service("dbUtil")
public class DBUtil {

	public static String getMaxaccept(PublicDao publicDao){
		return publicDao.getMaxaccept();
	}
}
