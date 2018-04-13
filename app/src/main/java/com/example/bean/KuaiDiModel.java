package com.example.bean;

import java.util.List;

/**
 * 创建日期：2018-03-28 on 13:09
 * 作者：ls
 */

public class KuaiDiModel {

    /**
     * message : ok
     * nu : 484985211036
     * ischeck : 1
     * condition : F00
     * com : zhongtong
     * status : 200
     * state : 3
     * data : [{"time":"2018-03-28 12:49:31","ftime":"2018-03-28 12:49:31","context":"[武汉市] 快件已在 [武昌关南] 签收,签收人: 本人, 感谢使用中通快递,期待再次为您服务!","location":"武昌关南"},{"time":"2018-03-28 09:06:21","ftime":"2018-03-28 09:06:21","context":"[武汉市] 快件已到达 [武昌关南],业务员 石炼柱(15107185335) 正在第1次派件, 请保持电话畅通,并耐心等待","location":"武昌关南"},{"time":"2018-03-27 14:48:32","ftime":"2018-03-27 14:48:32","context":"[武汉市] 快件离开 [武汉中转部] 发往 [武昌关南]","location":"武汉中转部"},{"time":"2018-03-27 14:21:02","ftime":"2018-03-27 14:21:02","context":"[武汉市] 快件到达 [武汉中转部]","location":"武汉中转部"},{"time":"2018-03-26 23:42:03","ftime":"2018-03-26 23:42:03","context":"[宁波市] 快件离开 [宁波中转部] 发往 [武汉中转部]","location":"宁波中转部"},{"time":"2018-03-26 15:12:35","ftime":"2018-03-26 15:12:35","context":"[宁波市] 快件离开 [宁波] 发往 [宁波中转部]","location":"宁波"},{"time":"2018-03-26 13:55:40","ftime":"2018-03-26 13:55:40","context":"[宁波市] [宁波] 的 宁海电商产业园区 (15990572220) 已收件","location":"宁波"}]
     */

    private String message;
    private String nu;
    private String ischeck;
    private String condition;
    private String com;
    private String status;
    private String state;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2018-03-28 12:49:31
         * ftime : 2018-03-28 12:49:31
         * context : [武汉市] 快件已在 [武昌关南] 签收,签收人: 本人, 感谢使用中通快递,期待再次为您服务!
         * location : 武昌关南
         */

        private String time;
        private String ftime;
        private String context;
        private String location;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "time='" + time + '\'' +
                    ", ftime='" + ftime + '\'' +
                    ", context='" + context + '\'' +
                    ", location='" + location + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "KuaiDiModel{" +
                "message='" + message + '\'' +
                ", nu='" + nu + '\'' +
                ", ischeck='" + ischeck + '\'' +
                ", condition='" + condition + '\'' +
                ", com='" + com + '\'' +
                ", status='" + status + '\'' +
                ", state='" + state + '\'' +
                ", data=" + data +
                '}';
    }
}
