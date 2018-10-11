package com.omar.spring.model;

import com.omar.xstream.util.XStreamAnnoted;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

/**
 * @Author omar
 * Company:   csii
 * @version: 1.0
 * @since: JDK 1.60_26
 * Create at:   2017/6/12
 * Description:
 */
@XStreamAnnoted
@XStreamAlias("Message")
public class User implements Serializable {

    private static final long serialVersionUID = -2853945357578768872L;
    private String userName;
    @XStreamAsAttribute
    private String userId;
    private String password;
    private String phoneNo;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                '}';
    }
}
