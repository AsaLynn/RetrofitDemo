package com.example.think.retrofitdemo;

/**
 *
 */

public class PostInfo {

    @Override
    public String toString() {
        return "PostInfo{" +
                "context='" + context + '\'' +
                ", ftime='" + ftime + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public PostInfo() {
    }

    private String context;
    private String ftime;
    private String time;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
