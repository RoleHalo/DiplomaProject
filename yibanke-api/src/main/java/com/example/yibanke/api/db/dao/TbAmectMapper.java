package com.example.yibanke.api.db.dao;

import com.example.yibanke.api.db.pojo.TbAmect;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbAmectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbAmect record);

    int insertSelective(TbAmect record);

    TbAmect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbAmect record);

    int updateByPrimaryKey(TbAmect record);
}