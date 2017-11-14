package com.quantpower.bossunion.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ShuLin on 2017/7/30.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class NewsModel implements Serializable {

    /**
     * stat : 1
     * data : [{"uniquekey":"a60d072bf75b7907ad011b408e4de096","title":"继\u201c中航黑豹\u201d后又一军工妖王破茧而出，必成八月第一妖王","date":"2017-07-30 10:15","category":"财经","author_name":"趣谈历史百家","url":"http://mini.eastday.com/mobile/170730101545115.html","thumbnail_pic_s":"http://03.imgmini.eastday.com/mobile/20170730/20170730_637ba8932c6d935376ff03060a35249d_cover_mwpm_03200403.jpeg","thumbnail_pic_s02":"http://03.imgmini.eastday.com/mobile/20170730/20170730_2363e94e95dfe04e8a94e191a5b4b7f8_cover_mwpm_03200403.jpeg","thumbnail_pic_s03":"http://03.imgmini.eastday.com/mobile/20170730/20170730_583d2ffc1bdf572ee0690b7d321999ed_cover_mwpm_03200403.jpeg"}]
     */

    private String stat;
    private List<DataBean> data;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uniquekey : a60d072bf75b7907ad011b408e4de096
         * title : 继“中航黑豹”后又一军工妖王破茧而出，必成八月第一妖王
         * date : 2017-07-30 10:15
         * category : 财经
         * author_name : 趣谈历史百家
         * url : http://mini.eastday.com/mobile/170730101545115.html
         * thumbnail_pic_s : http://03.imgmini.eastday.com/mobile/20170730/20170730_637ba8932c6d935376ff03060a35249d_cover_mwpm_03200403.jpeg
         * thumbnail_pic_s02 : http://03.imgmini.eastday.com/mobile/20170730/20170730_2363e94e95dfe04e8a94e191a5b4b7f8_cover_mwpm_03200403.jpeg
         * thumbnail_pic_s03 : http://03.imgmini.eastday.com/mobile/20170730/20170730_583d2ffc1bdf572ee0690b7d321999ed_cover_mwpm_03200403.jpeg
         */

        private String uniquekey;
        private String title;
        private String date;
        private String category;
        private String author_name;
        private String url;
        private String thumbnail_pic_s;
        private String thumbnail_pic_s02;
        private String thumbnail_pic_s03;

        public String getUniquekey() {
            return uniquekey;
        }

        public void setUniquekey(String uniquekey) {
            this.uniquekey = uniquekey;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumbnail_pic_s() {
            return thumbnail_pic_s;
        }

        public void setThumbnail_pic_s(String thumbnail_pic_s) {
            this.thumbnail_pic_s = thumbnail_pic_s;
        }

        public String getThumbnail_pic_s02() {
            return thumbnail_pic_s02;
        }

        public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
            this.thumbnail_pic_s02 = thumbnail_pic_s02;
        }

        public String getThumbnail_pic_s03() {
            return thumbnail_pic_s03;
        }

        public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
            this.thumbnail_pic_s03 = thumbnail_pic_s03;
        }
    }
}
