package com.mlxphone.news.entity;

/**
 * Created by MLXPHONE on 2016/12/9 0009.
 */

public class RegisterInfo {

    /**
     * message : OK
     * status : 0
     * data : {"result":-3,"token":null,"explain":"邮箱已注册！"}
     */

    private String message;
    private int status;
    private DataBean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * result : -3
         * token : null
         * explain : 邮箱已注册！
         */

        private int result;
        private Object token;
        private String explain;

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public Object getToken() {
            return token;
        }

        public void setToken(Object token) {
            this.token = token;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }
    }
}
