/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.constant;

/**
 * Created by ShuLin on 2017/5/12.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class SysStatusCode {
    /**
     * 接口请求成功
     */
    public static final int SUCCESS = 0;

    /**
     * 系统繁忙，请稍后重试
     */
    public static final int ERRORSYSTEMBUSY = 101;

    /**
     * 短信发送失败（话术—系统繁忙，请稍后重试）
     */
    public static final int ERRORMESSAGEFAILURE = 102;

    /**
     * 验证码错误或已过期
     */
    public static final int ERRORCODEOVERDUE = 201;

    /**
     * 用户已注册
     */
    public static final int USERREGISTERED = 202;

    /**
     * 头像上传失败
     */
    public static final int ERRORUPLOADAVATAR = 301;

    /**
     * 用户中心背景图片墙上传失败
     */
    public static final int ERRORWALL = 302;

    /**
     * 用户现有图片墙数量已最大
     */
    public static final int ERRORWALLBIGGEST= 303;

    /**
     * 用户中心背景图片墙删除失败
     */
    public static final int ERRORDELETEFAILED = 304;

    /**
     * 提交的字符超出规格
     */
    public static final int ERRORCHARACTERBIGGEST = 401;

    /**
     * 超出可添加数量
     */
    public static final int ERROROVERCOUNT = 402;

    /**
     * 已经添加过该数据
     */
    public static final int ERRORADD = 403;

    /**
     * Token失效
     */
    public static final int TOKENINVALID = 001;
}
