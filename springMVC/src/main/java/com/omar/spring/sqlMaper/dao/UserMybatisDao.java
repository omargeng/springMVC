package com.omar.spring.sqlMaper.dao;

import com.omar.spring.model.User;

/**
 * @Author omar
 * Company:   csii
 * @version: 1.0
 * @since: JDK 1.6.0_26
 * Create at:   2017/5/27
 * Description:
 */
public interface UserMybatisDao {

    public User selectUserInfoById(String uid);
    public void addUser(User user);
}
