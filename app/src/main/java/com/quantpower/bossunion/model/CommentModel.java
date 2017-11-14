package com.quantpower.bossunion.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ShuLin on 2017/7/29.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class CommentModel implements Serializable {

    private List<CommentsBean> comments;

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class CommentsBean {
        /**
         * content : 钱会让它变的好吃
         * user : {"id":2222403,"avatar":"http://pic3.zhimg.com/0ecf2216c2612b04592126adc16affa2_im.jpg","name":"每一天都在混水摸鱼"}
         * time : 1413987020
         * id : 556780
         * likes : 0
         */

        private String content;
        private UserBean user;
        private int time;
        private int id;
        private int likes;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public static class UserBean {
            /**
             * id : 2222403
             * avatar : http://pic3.zhimg.com/0ecf2216c2612b04592126adc16affa2_im.jpg
             * name : 每一天都在混水摸鱼
             */

            private int id;
            private String avatar;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
