package com.quantpower.bossunion.model;

import java.util.List;

/**
 * Created by ShuLin on 2017/7/30.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class WeixinNewsModel {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : wechat_20170730025571
         * title : 胡歌愿意为她“露背”，靳东陪她卖萌！34岁的她凭什么？
         * source : 化妆很容易
         * firstImg : http://zxpic.gtimg.com/infonew/0/wechat_pics_-30087403.jpg/640
         * mark :
         * url : http://v.juhe.cn/weixin/redirect?wid=wechat_20170730025571
         */

        private String id;
        private String title;
        private String source;
        private String firstImg;
        private String mark;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getFirstImg() {
            return firstImg;
        }

        public void setFirstImg(String firstImg) {
            this.firstImg = firstImg;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
