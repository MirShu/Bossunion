/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.model;

import com.alibaba.fastjson.JSON;

/**
 * Created by ShuLin on 2017/5/3.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class MessageResult {
    private int code;

    public String getRecent() {
        return recent;
    }

    public void setRecent(String recent) {
        this.recent = recent;
    }

    private String recent;
    private String msg;

    private String data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String result;

    public MessageResult() {
        this.code = -1;
        this.msg = "解析异常";
    }

    public MessageResult(int code) {
        this.code = code;
        this.msg = "解析异常";
    }

    public MessageResult(int code, String message, String obj) {
        this.code = code;
        this.msg = message;
        this.data = obj;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /**
     * @param result
     * @return
     */
    public static MessageResult parse(String result) {
        MessageResult message = new MessageResult();
        try {
            message = JSON.parseObject(result, MessageResult.class);
            return message;
        } catch (Exception e) {
            String str = e.getMessage();
        }
        return message;
    }
}
