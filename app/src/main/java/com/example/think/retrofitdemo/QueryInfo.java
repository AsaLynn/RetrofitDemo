package com.example.think.retrofitdemo;

import java.util.List;

/**
 * javabean.
 */

public class QueryInfo {

    public QueryInfo() {
    }

    private String com;
    private String condition;
    private String ischeck;
    private String message;
    private String nu;
    private String state;
    private String status;
    private List<PostInfo> data;

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PostInfo> getData() {
        return data;
    }

    public void setData(List<PostInfo> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "QueryInfo{" +
                "com='" + com + '\'' +
                ", condition='" + condition + '\'' +
                ", ischeck='" + ischeck + '\'' +
                ", message='" + message + '\'' +
                ", nu='" + nu + '\'' +
                ", state='" + state + '\'' +
                ", status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
