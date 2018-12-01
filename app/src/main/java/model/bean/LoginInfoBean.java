package model.bean;

/**
 * Created by Administrator on 2017/10/31.
 */

public class LoginInfoBean {
    private DataBean data;
    private String msg;
    private String id;
    private String result;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static class DataBean {
        /**
         * status : 99
         * sid : 0e4b8674-f1fc-4da3-aca6-e196fa6390e5
         */

        private int status;
        private String sid;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }
    }
}
