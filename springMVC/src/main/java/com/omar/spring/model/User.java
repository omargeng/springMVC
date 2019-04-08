package com.omar.spring.model;

import com.omar.xstream.util.XStreamAnnoted;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    @NotNull
    private String userName;
    @Pattern(regexp = "\\w{4,30}") //匹配4到30个数字字母以及下划线的字符
    @XStreamAsAttribute
    private String userId;
    private String password;
    @Pattern(regexp = "^1([38]\\d|5[0-35-9]|7[3678])\\d{8}$") //通过正则表达式校验
    private String phoneNo;
    @Past //对属性进行校验，时间必须是一个过去的时间
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) //时间字符串的格式化
    private Date birthDay;

    private String sex;

    private String city;

    private List<String> favoriteCity;

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


    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getFavoriteCity() {
        return favoriteCity;
    }

    public void setFavoriteCity(List<String> favoriteCity) {
        this.favoriteCity = favoriteCity;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", birthDay=" + birthDay +
                '}';
    }
}
