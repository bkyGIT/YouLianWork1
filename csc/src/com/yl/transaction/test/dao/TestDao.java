package com.yl.transaction.test.dao;

import com.yl.transaction.test.pojo.Test;

public interface TestDao {

	public Test selectByPrimaryKey(String key);
}
