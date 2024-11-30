package com.tomcan.quickuimate.bean;

import java.io.Serializable;
import java.util.List;

public class Skin {

    private List<SkinBean> skin;

    public List<SkinBean> getSkin() {
        return skin;
    }

    public void setSkin(List<SkinBean> skin) {
        this.skin = skin;
    }

    public static class SkinBean implements Serializable {
        /**
         * id : 1
         * name : 静灰
         * path : ic_home_skin1.png
         */

        private int id;
        private String name;
        private String path;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
