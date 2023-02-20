package com.graduation_project.wicky.csa.model;

import com.graduation_project.wicky.csa.bean.Good;
import com.graduation_project.wicky.csa.bean.Order;
import com.graduation_project.wicky.csa.bean.User;
import com.graduation_project.wicky.csa.model.entity.ModelGood;
import com.graduation_project.wicky.csa.model.entity.ModelGoodNoId;
import com.graduation_project.wicky.csa.model.entity.ModelOrder;
import com.graduation_project.wicky.csa.model.entity.ModelUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Wicky on 2019/3/22.
 */

public class Converter {

    public static Good toGood(ModelGood modelGood) {
        Good good = new Good();
        good.setId(modelGood.getId()); //id
        good.setName(modelGood.getProductName());//产品名
        good.setPrice(modelGood.getUnitPrice());//产品单价
        switch (modelGood.getUnit()) {//单位与类别
            case "亩":
                good.setCategory(1);
                break;
            case "只":
                good.setCategory(2);
                break;
            case "千克":
                good.setCategory(3);
                break;
            default:
                good.setCategory(1);
                break;
        }
        for (int i = 0; i < modelGood.getPicture().size(); i++) {
            modelGood.getPicture().set(i, "http://203.195.159.23:18088/api" + modelGood.getPicture().get(i));
        }
        good.setGoodsImgList(modelGood.getPicture());//产品图片

        good.setSupplierId(modelGood.getProducerId());//产品供应商id
        good.setInventory(modelGood.getInventory());//库存
        good.setPlaceId("");//产地（后端没有）
        good.setDescription(modelGood.getRemark());
        return good;
    }

    public static ModelGood toModelGood(Good good) {
        ModelGood modelGood = new ModelGood();
        modelGood.setProductName(good.getName());
        modelGood.setUnitPrice((int) good.getPrice());
        switch (good.getCategory()) {//单位与类别
            case 1:
                modelGood.setUnit("亩");
                break;
            case 2:
                modelGood.setUnit("只");
                break;
            case 3:
                modelGood.setUnit("千克");
                break;
            default:
                modelGood.setUnit("亩");
                break;
        }
        modelGood.setPicture(good.getGoodsImgList());
        modelGood.setProducerId(good.getSupplierId());
        modelGood.setInventory(good.getInventory());
        modelGood.setRemark(good.getDescription());
        return modelGood;
    }

    public static ModelGoodNoId toModelGoodNoId(Good good) {
        ModelGoodNoId modelGood = new ModelGoodNoId();
        modelGood.setProductName(good.getName());
        modelGood.setUnitPrice((int) good.getPrice());
        //SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        modelGood.setCreateTime("2019-04-25T08:51:12.402Z");
        modelGood.setLastModified("2019-04-25T08:51:12.402Z");
        switch (good.getCategory()) {//单位与类别
            case 1:
                modelGood.setUnit("亩");
                break;
            case 2:
                modelGood.setUnit("只");
                break;
            case 3:
                modelGood.setUnit("千克");
                break;
            default:
                modelGood.setUnit("亩");
                break;
        }
        String str = "http://203.195.159.23:18088/api";
        if (good.getGoodsImgList() != null && good.getGoodsImgList().size() > 0) {
            for (int i = 0; i < good.getGoodsImgList().size(); i++) {
                String url = good.getGoodsImgList().get(i);
                String newUrl = url.replace(str,"");
                good.getGoodsImgList().set(i,newUrl);
            }
        }else {
            good.setGoodsImgList(new ArrayList<String>());
            good.getGoodsImgList().add("/picture/2f82d701-7061-4486-92a1-2b71214a4499.jpg");
        }
        modelGood.setPicture(good.getGoodsImgList());
        modelGood.setProducerId(good.getSupplierId());
        modelGood.setInventory(good.getInventory());
        modelGood.setRemark(good.getDescription());
        return modelGood;
    }

    public static User toUser(ModelUser modelUser) {
        User user = new User();
        user.setId(modelUser.getId());
        user.setUserName(modelUser.getUsername());
        user.setPassword(modelUser.getPassword());
        user.setPhone(modelUser.getPhoneNumber());
        if (modelUser.getUserType().equals("ROOT")) {
            user.setAdmin(true);
        } else {
            user.setAdmin(false);
        }
        return user;
    }

    public static ModelUser toModelUser(User user, ModelUser modelUser) {
        return modelUser;
    }

    public static ModelOrder toModelOrder(Order order, ModelOrder modelOrder) {
        modelOrder.setId(order.getId());
        modelOrder.setCreateTime(String.valueOf(order.getCreateDate()));
        modelOrder.setBuyerId(order.getAdopterId());
        modelOrder.setProducerId(order.getSupplerId());
        modelOrder.setOrderNumber(order.getOrderNumber());
        modelOrder.setRemark(order.getNote());


        return modelOrder;
    }
}
