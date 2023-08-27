package com.example.yibanke.api.db.pojo;

public class TbFaceModel {
    private Integer id;

    private Integer userId;

    private String faceModel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFaceModel() {
        return faceModel;
    }

    public void setFaceModel(String faceModel) {
        this.faceModel = faceModel == null ? null : faceModel.trim();
    }
}