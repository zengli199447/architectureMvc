package model.db;

import java.util.List;

import model.db.entity.LoginUserInfo;

/**
 * Created by Administrator on 2018/1/5.
 */

public interface DBHelper {

    //---------------------------条件查询---------------------------------------

    /**
     * 查询 LoginUserInfo数据
     *
     * @return
     */
    LoginUserInfo queryLoginUserInfo(String mUserName);

    //---------------------------查询所有（无筛选条件）---------------------------

    /**
     * 查询所有 LoginUserInfo数据
     *
     * @return
     */
    List<LoginUserInfo> loadLoginUserInfo();


    //---------------------------插入数据（更新数据）-----------------------------

    /**
     * 插入一条 LoginUserInfo数据
     *
     * @return
     */
    void insertLoginUserInfo(LoginUserInfo mLoginUserInfo);


    //---------------------------删除数据(条件删除)-------------------------------

    /**
     * 删除一个 LoginUserInfo数据
     *
     * @return
     */
    void deleteLoginUserInfo(String mUserName);


    //---------------------------修改数据()-------------------------------

    /**
     * 修改一条 IpAndPortInfo数据
     *
     * @return
     */
    void UpDataLoginUserInfo(LoginUserInfo mLoginUserInfo);

}
