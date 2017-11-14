package com.quantpower.bossunion.model;

import com.alibaba.fastjson.JSON;

/**
 * Created by ShuLin on 2017/7/14.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class ChatMessageResult {
    private String error_code;
    private String reason;
    private String result;
    private String comments;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    /**
     * @param result
     * @return
     */
    public static ChatMessageResult parse(String result) {
        ChatMessageResult message = new ChatMessageResult();
        try {
            message = JSON.parseObject(result, ChatMessageResult.class);
            return message;
        } catch (Exception e) {
            String str = e.getMessage();
        }
        return message;
    }


    /**
     * @param result
     * @return
     */
    public static ChatMessageResult parseComments(String result) {
        ChatMessageResult message = new ChatMessageResult();
        try {
            message = JSON.parseObject(result, ChatMessageResult.class);
            return message;
        } catch (Exception e) {
            String str = e.getMessage();
        }
        return message;
    }

}
