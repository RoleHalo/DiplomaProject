package com.example.yibanke.api.db.dao;

import com.example.yibanke.api.db.pojo.TbFaceModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbFaceModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbFaceModel record);

    int insertSelective(TbFaceModel record);

    TbFaceModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbFaceModel record);

    int updateByPrimaryKeyWithBLOBs(TbFaceModel record);

    int updateByPrimaryKey(TbFaceModel record);
}