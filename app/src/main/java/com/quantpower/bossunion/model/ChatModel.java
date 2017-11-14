package com.quantpower.bossunion.model;

import java.io.Serializable;

/**
 * Created by ShuLin on 2017/7/15.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class ChatModel implements Serializable {
    private static final long serialVersionUID = 3675076585769085832L;


    /**
     * code : 100000
     * text : 去啦
     */

    private int code;
    private String text;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
