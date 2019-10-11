package com.mrl.ischool.common.dto;

public class AjaxResult
{
    private boolean success;
    private String msg;
    private Object data;

    public boolean isSuccess() { return this.success; }

    public void setSuccess(boolean success) { this.success = success; }

    public String getMsg() { return this.msg; }

    public void setMsg(String msg) { this.msg = msg; }

    public Object getData() { return this.data; }

    public void setData(Object data) { this.data = data; }
}
