/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.constant;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.base.BaseApplication;

import java.text.MessageFormat;

/**
 * Created by ShuLin on 2017/5/3.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class URLS {
    public static final String BASE_SERVER_URL = "http://118.178.121.211:8006/Api/";
    /**
     * 登陆获取验证码
     */
    public static final String USER_SENDCODE = MessageFormat.format("{0}User/SendCode", BASE_SERVER_URL);

    /**
     * 用户登录User/userRegister
     */
    public static final String USER_USER_LOGIN = MessageFormat.format("{0}User/userLogin", BASE_SERVER_URL);

    /**
     * 用户注册
     */
    public static final String USER_USER_REGISTER = MessageFormat.format("{0}User/userRegister", BASE_SERVER_URL);

    /**
     * 检测验证码
     */
    public static final String USER_CHECK_CODE = MessageFormat.format("{0}User/CheckCode", BASE_SERVER_URL);
    /**
     * 无法登录、用户申诉、修改用户手机号
     */
    public static final String USER_ALTERUSER_PHONE = MessageFormat.format("{0}User/alterUserPhone", BASE_SERVER_URL);

    /**
     * 获取行业标签
     */
    public static final String USER_GETTRADE = MessageFormat.format("{0}User/getTrade", BASE_SERVER_URL);


    /**
     * 发现
     */
    public static final String DISCOVERY_LIST = "http://live.9158.com/Room/GetHotLive_v2?lon=121.480732&province=上海&lat=31.220948&page=1&type=1";

    /**
     * 感兴趣的人
     */
    public static final String INTERESTED_LIST = "http://live.9158.com/Room/GetHotLive_v2?lon=126.480732&province=湖南&lat=31.220948&page=1&type=6";

    /**
     * 消息底部列表
     */
    public static final String INFORMATION_LIST = "http://live.9158.com/Room/GetHotLive_v2";

    /**
     * 关注
     */
    public static final String ATTENTION_LIST = "http://live.9158.com/Room/GetHotLive_v2?lon=126.480732&province=湖南&lat=31.220948&page=1&type=1";

    /**
     * 我的订单   http://live.9158.com/Room/GetRandomAnchor
     */
    public static final String MEORDER_LIST = "http://live.9158.com/Room/GetHotLive_v2?lon=126.480732&province=湖南&lat=31.220948&page=1&type=9";

    /**
     * 聊天chat room
     */
    public static final String CHAT_LIST1 = "http://live.9158.com/Room/GetHotLive_v2?lon=126.480732&province=上海&lat=31.220948&page=1&type=8";
    /**
     * 聊天chat room
     */
    public static final String CHAT_LIST = "http://op.juhe.cn/robot/index";

    /**
     * 最近拜访
     */
    public static final String ME_VISIT = "http://live.9158.com/Room/GetHotLive_v2?lon=126.480732&province=上海&lat=31.220948&page=1&type=2";

    /**
     * 赞过我的
     */
    public static final String MATION_PRAISE = "http://live.9158.com/Room/GetHotLive_v2?lon=112.999088&province=长沙&lat=28.227861&page=1&type=2";

    /**
     * 评论过我
     */
    public static final String MATION_REVIEW = "http://live.9158.com/Room/GetHotLive_v2?lon=113.2578&province=广州&lat=23.147268&page=1&type=2";

    /**
     * 互相评论
     */
    public static final String SHORT_COMMENTS = "https://news-at.zhihu.com/api/5/story/4232852/short-comments";

    /**
     * 头条
     */
    public static final String TOUTIAO_INDEX = "http://v.juhe.cn/toutiao/index";

    /**
     * 微信资讯
     */
    public static final String WEIXIN_QUERY = "http://v.juhe.cn/weixin/query?pno&ps&dtype&key=be3f997134a1cafbee2c96bed60b4e9a";

}
