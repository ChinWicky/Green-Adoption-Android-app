package com.graduation_project.wicky.csa.bean;


public class Banner {
    /**
     * bannerOrder : 0
     * bannerType : 1
     * createTime : 1506410074000
     * httpurl : http://localhost/admin/admin/common/main.jhtml
     * id : 3
     * imgurl : http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1208/14/c0/12901419_1344914249808.jpg
     * modifiedTime : 1506500255000
     * status : 0
     */

    private int bannerOrder;
    private int bannerType;
    private long createTime;
    private String httpurl;
    private int id;
    private String imgurl;
    //    private long modifiedTime;
    private int status;

    public int getBannerOrder() {
        return bannerOrder;
    }

    public void setBannerOrder(int bannerOrder) {
        this.bannerOrder = bannerOrder;
    }

    public int getBannerType() {
        return bannerType;
    }

    public void setBannerType(int bannerType) {
        this.bannerType = bannerType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getHttpurl() {
        return httpurl;
    }

    public void setHttpurl(String httpurl) {
        this.httpurl = httpurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
