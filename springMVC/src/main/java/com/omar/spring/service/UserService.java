package com.omar.spring.service;

import com.omar.spring.model.User;
import com.omar.spring.sqlMaper.dao.UserMybatisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Author omar
 * Company:   csii
 * @version: 1.0
 * @since: JDK 1.6.0_26
 * Create at:   2017/5/27
 * Description:
 */
@Service("userService")
public class UserService {

    Logger log= LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserMybatisDao userMybatisDao;

    public void addUsre(User user){
        userMybatisDao.addUser(user);
    }

    @Cacheable(value="user",keyGenerator ="baseCacheKeyGenerator")
    public User queryUserById(String userId){
        log.info("query from db");
        return userMybatisDao.selectUserInfoById(userId);
    }

    public User selectUserInfoById(String id){
        return  userMybatisDao.selectUserInfoById(id);
    }

    public UserMybatisDao getUserMybatisDao() {
        return userMybatisDao;
    }

    public void setUserMybatisDao(UserMybatisDao userMybatisDao) {
        this.userMybatisDao = userMybatisDao;
    }
}
