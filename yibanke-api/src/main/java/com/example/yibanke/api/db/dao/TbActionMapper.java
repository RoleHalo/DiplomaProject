package com.example.yibanke.api.db.dao;

import com.example.yibanke.api.db.pojo.TbAction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbActionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbAction record);

    int insertSelective(TbAction record);

    TbAction selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbAction record);

    int updateByPrimaryKey(TbAction record);
}