package global;

import java.util.List;

import model.DataManager;
import model.db.entity.LoginUserInfo;
import util.LogUtil;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class DataClass {

    //TODO BaseUrl
    public static String URL = "http://192.168.14.199:8088/portal/r/";

    //TODO 支付宝
    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2018030302306199";

    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "";

    public static final String RSA2_PRIVATE = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDdZdupsNaBg+zsIpWXiiJlOmcGWzQGWvFbqmxo9ztEOSgl++TazyLbD7bs3S3wGfVZ02LaSvTMPwPQhR2oTojOHEnEwZcfK8Yl53rdbuUT6NuyADViizmCybiDBAfDaQa1+oO5WgGdcTw/Oif0Gm6UIrtH8mRvoO5LL0sq5lH2sK8H7YKyGZBP7rYNd2472o+4y8nnjo3dma3w99e7F8ol8WyZDYB1ctkhgSWm1FN7ll8h+g5/tD6iUf/dwNgaB0d1LGc6/BS4rSZT4ZOvEEKWfG2aZ8XyGBDr722AvBD/NiGMxV4kEzFj+0SIjqoXKY9osZa6vgDrnI6MjUUzInVrAgMBAAECggEANyqMQ4omyPrgbC5p6+qli2e0CCdtnS/H+DlM7hK8l9FOKbjMW6FFZCRURk0Oh0dQlWNQvGf0+3ewMaMZsrhu6jyuPPKn/0JfC07UlJxvRDi/lWjr5HQgjnBJI4w7ivt+ipWzgNnmkR6IDpElJ3P2nb7Rv6msUQxelClip3vgbIE/B4lMR1Sm5vQiKnOrPS+a+4h4kkDrW+Y+Kj2vjExOUJNr2xi/ZG0z5H0vaUnU5OjqtT15ZH/ZN1u4tR0DUXjLr8RZVEDJTeI6myqAPbT0+UF6/6OILzuLOtOKaVYveKMxJ+ZpNzfVBZDStnojtuBoGVk2ImwBk6yN8MjEww9BIQKBgQD/8ZeHG/RuSgkIHpTr66VANNt2aDZsP4yvfW3VOO37jHUdhQ9sZrLN3nmTRBw3KcUFa2l0fHvy+LSUjK5pSkqWSlz9j9wUHnUz9/D/q8nwNJjGyMsa8cLENheK5fb06Ok879NCh+mGKpr2w2hZdgfwyPsIM9omasa6Ej2tXbmHMwKBgQDdclJJNR+MYTaVuBz03XEZSaFQadgHFGJuR/XWVPQ+9MJgeJiMb59mcTI2w4TQTrd9bBED1JwA7bskIauOSFoBRCSlMvIjrSHFsKukOB4dvJ5jXDd+V5gDJkuvXAXHa5Iv6BtB3ez3C8JvdF6/epifIR6ATFnmG+gleUvmiH/46QKBgQCKnae5bvR0/vfhQ97X3yoCmh36BPrkqY5iRQZFIq4RPML5F+WAiiZwxPsHPS4i2iZwhvn4MqtWrkfu5DpZn/jBaMjwMwe4hb5oRPJrJfkadQIBNyJ5F1KqvbkGawHTJombjS/XqpBZL535418ShJBMYi1DBgIyKEY05EgvKsGebQKBgQCqCSgp0kKhNdwF5lNFqes38ZON/VfFR9+0skiQBLdux92g2bi4zNTmgjB+YG7QzECUa7A88rm+9MvnmHr0S5XgtwQ0XQQUZNIzy85f0D8/sh8kSCDMQFIWyQcAZXaCg5TCeETFtLRJlZv8nKxL9sTOG04iUX4N9Iuyioq/PzyUUQKBgQDcaZaO2KqhDkylXk8PyEG/3+3I2fDo8gWg9aYMPA7D13+WXC3UpS+mxMGE7xlvQjrx9Hb2SS0Gn8ZHP1+HKSKqpJZhsoKIm/7G8+WcxAZfYRlh4lRolb4ff9uThMaTLUSA7vQCcLu+D4ejfEcorQyTh0le+hpb1Sr6x9T72xRM1Q==";
    public static final String RSA_PRIVATE = "";

    public static final int SDK_PAY_FLAG = 1;
    public static final int SDK_AUTH_FLAG = 2;

    //TODO 融云
    /**集成融云应用的唯一标识, 前端后台都需要用到.*/
    public static final String APP_KEY = "pvxdm17jp3var";
    /**务必妥善保存, 不能放在应用端, 否则可以被反编译获得.*/
    public static final String APP_SECRET = "xwTY3r33RvhZtN";

    public static final String LIVE_URL = "liveUrl";


    public DataManager dataManager;

    public DataClass() {

    }

    public DataClass(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void DbCurrentUser(String userName, String passWord) {  //保存/修改当前帐号信息
        final List<LoginUserInfo> loginUserInfos = dataManager.loadLoginUserInfo();
        if (loginUserInfos != null && loginUserInfos.size() > 0) {
            final LoginUserInfo loginUserInfo = loginUserInfos.get(0);
            if (userName.equals(loginUserInfo.getUsername())) {
                dataManager.UpDataLoginUserInfo(new LoginUserInfo(loginUserInfo.getId(), userName, passWord));
            } else {
                dataManager.insertLoginUserInfo(new LoginUserInfo(userName, passWord));
            }
        } else {
            dataManager.insertLoginUserInfo(new LoginUserInfo(userName, passWord));
        }
    }

    public LoginUserInfo GetCurrentUser() { // 获取上一次登录用户信息
        LoginUserInfo loginUserInfo = null;
        final List<LoginUserInfo> loginUserInfos = dataManager.loadLoginUserInfo();
        if (loginUserInfos != null && loginUserInfos.size() > 0) {
            loginUserInfo = loginUserInfos.get(0);
        }
        return loginUserInfo;
    }


}
