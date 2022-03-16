package com.example.yibanke.api.db.dao;

import com.example.yibanke.api.db.pojo.TbAmectType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbAmectTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbAmectType record);

    int insertSelective(TbAmectType record);

    TbAmectType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbAmectType record);

    int updateByPrimaryKey(TbAmectType record);
}