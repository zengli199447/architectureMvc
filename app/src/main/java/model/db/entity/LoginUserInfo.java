package model.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/3/3 0003.
 */

@Entity
public class LoginUserInfo {
    //主键自增长
    @Id(autoincrement = true)
    private Long id;
    //用户名
    @Unique
    private String username;
    //密码
    private String password;

    public LoginUserInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Generated(hash = 798347144)
    public LoginUserInfo(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    @Generated(hash = 436417725)
    public LoginUserInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}
