package com.graduation_project.wicky.csa.bean;

/**
 * Created by Wicky on 2019/2/20.
 */

public class ListOption {
    private String title;
    private int image;

    public ListOption(String title){
        this.title=title;
    }
    public String getTitle(){return title;}

    public void  setTitle(String title){this.title=title;}

    public int getImage(){return image;}

    public void setImage(int image){this.image=image;}
}
