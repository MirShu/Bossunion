package com.quantpower.bossunion.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ShuLin on 2017/7/12.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class DiscoveryList implements Serializable {

    private static final long serialVersionUID = 1162859811555825148L;


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * pos : 1
         * useridx : 5323
         * userId : WeiXin20236542
         * gender : 0
         * myname : Hitler 👑主持璐璐💐
         * smallpic : http://liveimg.9158.com/pic/avator/2017-07/05/13/20170705131629_5323_250.png
         * bigpic : http://liveimg.9158.com/pic/avator/2017-07/05/13/20170705131629_5323_640.png
         * allnum : 9992
         * roomid : 60675707
         * serverid : 5
         * gps : 北京市
         * flv : http://hdl.9158.com/live/a3ad0e9f4c2ac37166cac9ea290139a8.flv
         * anchorlevel : 1
         * starlevel : 5
         * familyName : Hitler
         * isSign : 1
         * nation :
         * nationFlag :
         * distance : 0
         */

        private int pos;
        private int useridx;
        private String userId;
        private int gender;
        private String myname;
        private String smallpic;
        private String bigpic;
        private int allnum;
        private int roomid;
        private int serverid;
        private String gps;
        private String flv;
        private int anchorlevel;
        private int starlevel;
        private String familyName;
        private int isSign;
        private String nation;
        private String nationFlag;
        private int distance;

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public int getUseridx() {
            return useridx;
        }

        public void setUseridx(int useridx) {
            this.useridx = useridx;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getMyname() {
            return myname;
        }

        public void setMyname(String myname) {
            this.myname = myname;
        }

        public String getSmallpic() {
            return smallpic;
        }

        public void setSmallpic(String smallpic) {
            this.smallpic = smallpic;
        }

        public String getBigpic() {
            return bigpic;
        }

        public void setBigpic(String bigpic) {
            this.bigpic = bigpic;
        }

        public int getAllnum() {
            return allnum;
        }

        public void setAllnum(int allnum) {
            this.allnum = allnum;
        }

        public int getRoomid() {
            return roomid;
        }

        public void setRoomid(int roomid) {
            this.roomid = roomid;
        }

        public int getServerid() {
            return serverid;
        }

        public void setServerid(int serverid) {
            this.serverid = serverid;
        }

        public String getGps() {
            return gps;
        }

        public void setGps(String gps) {
            this.gps = gps;
        }

        public String getFlv() {
            return flv;
        }

        public void setFlv(String flv) {
            this.flv = flv;
        }

        public int getAnchorlevel() {
            return anchorlevel;
        }

        public void setAnchorlevel(int anchorlevel) {
            this.anchorlevel = anchorlevel;
        }

        public int getStarlevel() {
            return starlevel;
        }

        public void setStarlevel(int starlevel) {
            this.starlevel = starlevel;
        }

        public String getFamilyName() {
            return familyName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName;
        }

        public int getIsSign() {
            return isSign;
        }

        public void setIsSign(int isSign) {
            this.isSign = isSign;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getNationFlag() {
            return nationFlag;
        }

        public void setNationFlag(String nationFlag) {
            this.nationFlag = nationFlag;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }
    }
}
